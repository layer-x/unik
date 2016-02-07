package ec2api

import (
	"github.com/Sirupsen/logrus"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/cmd/daemon/ec2_metada_client"
	"github.com/layer-x/unik/cmd/daemon/unik_ec2_utils"
	"github.com/layer-x/unik/types"
)

func ListUnikInstances() ([]*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeInstancesInput := &ec2.DescribeInstancesInput{
		Filters: []*ec2.Filter{
			&ec2.Filter{
				Name:   aws.String("tag-key"),
				Values: []*string{aws.String("UNIK_INSTANCE_ID")},
			},
		},
	}
	describeInstancesOutput, err := ec2Client.DescribeInstances(describeInstancesInput)
	if err != nil {
		return nil, lxerrors.New("running 'describe instances'", err)
	}

	allUnikInstances := []*types.UnikInstance{}

	for _, reservation := range describeInstancesOutput.Reservations {
		for _, instance := range reservation.Instances {
			unikInstance := unik_ec2_utils.GetUnikInstanceMetadata(instance)
			if unikInstance != nil {
				lxlog.Debugf(logrus.Fields{"UnikInstance": unikInstance.UnikInstanceID}, "Unik Instance read")
				allUnikInstances = append(allUnikInstances, unikInstance)
			}
		}
	}

	return allUnikInstances, nil
}
