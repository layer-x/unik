package unik_ec2_utils
import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
)

const UNIK_INSTANCE_ID = "UNIK_INSTANCE_ID"
const UNIKERNEL_ID = "UNIKERNEL_ID"

func GetUnikInstanceMetadata(instance *ec2.Instance) *types.UnikInstance {
	unikInstance := &types.UnikInstance{}
	for _, tag := range instance.Tags {
		if *tag.Key == "Name" {
			unikInstance.UnikInstanceName = *tag.Value
		}
		if *tag.Key == UNIK_INSTANCE_ID {
			unikInstance.UnikInstanceID = *tag.Value
		}
		if *tag.Key == UNIKERNEL_ID {
			unikInstance.UnikernelId = *tag.Value
		}
		if *tag.Key == UNIKERNEL_APP_NAME {
			unikInstance.UnikernelName = *tag.Value
		}
	}
	if unikInstance.UnikInstanceID == "" {
		return nil
	}
	unikInstance.AmazonID = *instance.InstanceId
	if instance.PrivateIpAddress != nil {
		unikInstance.PrivateIp = *instance.PrivateIpAddress
	}
	if instance.PublicIpAddress != nil {
		unikInstance.PublicIp = *instance.PublicIpAddress
	}
	if instance.State != nil {
		unikInstance.State = *instance.State.Name
	}
	if instance.LaunchTime != nil {
		unikInstance.Created = *instance.LaunchTime
	}
	return unikInstance
}