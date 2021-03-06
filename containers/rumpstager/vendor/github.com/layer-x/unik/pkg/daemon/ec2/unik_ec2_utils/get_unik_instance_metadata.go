package unik_ec2_utils

import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/pkg/types"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/layerx-commons/lxerrors"
	"encoding/base64"
	"encoding/json"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
)

const UNIK_INSTANCE_ID = "UNIK_INSTANCE_ID"
const UNIKERNEL_ID = "UNIKERNEL_ID"

func GetUnikInstanceMetadata(logger lxlog.Logger, instance *ec2.Instance) (*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	var unikInstanceId, instanceName, unikernelName, unikernelId string
	for _, tag := range instance.Tags {
		if *tag.Key == UNIK_INSTANCE_ID {
			unikInstanceId = *tag.Value
		}
		if *tag.Key == "Name" {
			instanceName = *tag.Value
		}
		if *tag.Key == UNIKERNEL_NAME {
			unikernelName = *tag.Value
		}
		if *tag.Key == UNIKERNEL_ID {
			unikernelId = *tag.Value
		}
	}
	if unikInstanceId == "" {
		return nil, nil
	}
	describeUserDataInput := &ec2.DescribeInstanceAttributeInput{
		Attribute: aws.String("userData"),
		InstanceId: instance.InstanceId,
	}
	describeUserDataOutput, err := ec2Client.DescribeInstanceAttribute(describeUserDataInput)
	if err != nil {
		return nil, lxerrors.New("could not get userdata for instance " + *instance.InstanceId, err)
	}
	if describeUserDataOutput.UserData == nil {
		return nil, lxerrors.New("userdata was nil for instance " + unikInstanceId, nil)
	}
	data, err := base64.StdEncoding.DecodeString(*describeUserDataOutput.UserData.Value)
	if err != nil {
		return nil, lxerrors.New("could not decode base64 output", err)
	}
	var unikInstanceData types.UnikInstanceData
	err = json.Unmarshal(data, &unikInstanceData)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal userdata string " + string(data) + "to unikinstance data", err)
	}
	if instanceName == "" {
		instanceName = unikInstanceId
	}
	unikInstance := &types.UnikInstance{
		UnikInstanceData: unikInstanceData,
		UnikInstanceID: unikInstanceId,
		VMID: *instance.InstanceId,
		UnikInstanceName: instanceName,
		UnikernelId: unikernelId,
		UnikernelName: unikernelName,
	}
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
	logger.WithFields(lxlog.Fields{
		"unik-instance": unikInstance,
	}).Debugf("read unik instance")
	return unikInstance, nil
}
