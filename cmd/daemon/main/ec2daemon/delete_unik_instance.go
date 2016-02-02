package ec2daemon
import (
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
)

func DeleteUnikInstance(unikInstanceId string) error {
	unikInstances, err := ListUnikInstances()
	if err != nil {
		return lxerrors.New("failed to retrieve known instances", err)
	}
	var amazonId string
	for _, unikInstance := range unikInstances {
		if unikInstance.UnikInstanceID == unikInstanceId {
			amazonId = unikInstance.AmazonID
			break
		}
	}
	if amazonId == "" {
		return lxerrors.New("unik instance "+unikInstanceId+" not found", nil)
	}
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	terminateInstancesInput := &ec2.TerminateInstancesInput{
		InstanceIds: []*string{aws.String(amazonId)},
	}
	_, err = ec2Client.TerminateInstances(terminateInstancesInput)
	if err != nil {
		return lxerrors.New("could not terminate unik instance "+unikInstanceId, err)
	}
	return nil
}