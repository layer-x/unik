package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/layer-x/unik/pkg/daemon/ec2/unik_ec2_utils"
	"github.com/layer-x/unik/pkg/types"
)

func ListUnikInstances(logger lxlog.Logger) ([]*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
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
			unikInstance, err := unik_ec2_utils.GetUnikInstanceMetadata(logger, instance)
			if unikInstance != nil && err == nil {
				logger.WithFields(lxlog.Fields{
					"UnikInstance": unikInstance.UnikInstanceID,
				}).Debugf("Unik Instance read")
				allUnikInstances = append(allUnikInstances, unikInstance)
			}
		}
	}

	return allUnikInstances, nil
}
