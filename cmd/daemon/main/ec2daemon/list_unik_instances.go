package ec2daemon
import (
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/daemon/main/unik_ec2_utils"
)

func listUnikInstances() ([]*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeInstancesOutput, err := ec2Client.DescribeInstances(&ec2.DescribeInstancesInput{})
	if err != nil {
		return nil, lxerrors.New("running 'describe instances'", err)
	}

	allUnikInstances := []*types.UnikInstance{}

	for _, reservation := range describeInstancesOutput.Reservations {
		for _, instance := range reservation.Instances {
			unikInstance := unik_ec2_utils.GetUnikInstanceMetadata(instance)
			if unikInstance != nil {
				allUnikInstances = append(allUnikInstances, unikInstance)
			}
		}
	}

	return allUnikInstances, nil
}