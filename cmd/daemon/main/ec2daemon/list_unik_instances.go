package ec2daemon
import (
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/daemon/main/unik_ec2_utils"
"github.com/aws/aws-sdk-go/aws"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
)

func listUnikInstances() ([]*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeInstancesInput := &ec2.DescribeInstancesInput{
		Filters: []*ec2.Filter{
			&ec2.Filter{
				Name: aws.String("tag-key"),
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
				lxlog.Debugf(logrus.Fields{"instance": instance},"read instance from EC2")
				allUnikInstances = append(allUnikInstances, unikInstance)
			}
		}
	}

	return allUnikInstances, nil
}