package unik_ec2_utils

import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/types"
)

const UNIK_INSTANCE_ID = "UNIK_INSTANCE_ID"
const UNIKERNEL_ID = "UNIKERNEL_ID"

func GetUnikInstanceMetadata(instance *ec2.Instance) *types.UnikInstance {
	unikInstance := &types.UnikInstance{
		Tags: make(map[string]string),
	}
	for _, tag := range instance.Tags {
		switch *tag.Key {
		case "Name" :
				unikInstance.UnikInstanceName = *tag.Value
		case UNIK_INSTANCE_ID:
				unikInstance.UnikInstanceID = *tag.Value
		case UNIKERNEL_ID:
				unikInstance.UnikernelId = *tag.Value
		case UNIKERNEL_APP_NAME:
				unikInstance.UnikernelName = *tag.Value
		default:
				unikInstance.Tags[*tag.Key] = *tag.Value
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
