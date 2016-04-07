package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
"github.com/layer-x/layerx-commons/lxlog"
)


func AttachVolume(logger lxlog.Logger, volumeNameOrId, unikInstanceId, deviceName string) error {
	if deviceName == "" {
		return lxerrors.New("device name must be specified", nil)
	}
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	volume, err := GetVolumeByIdOrName(logger, volumeNameOrId)
	if err != nil {
		return lxerrors.New("could not find volume "+volumeNameOrId, err)
	}
	if volume.IsAttached() {
		return lxerrors.New("volume " + volume.Name + " is already attached to instance "+volume.Attachments[0].InstanceId, err)
	}
	unikInstance, err := GetUnikInstanceByPrefixOrName(logger, unikInstanceId)
	if err != nil {
		return lxerrors.New("failed to retrieve unik instance", err)
	}

	attachVolumeInput := &ec2.AttachVolumeInput{
		VolumeId: aws.String(volume.VolumeId),
		InstanceId: aws.String(unikInstance.VMID),
		Device: aws.String(deviceName),
	}
	attachVolumeOutput, err := ec2Client.AttachVolume(attachVolumeInput)
	if err != nil {
		return lxerrors.New("could not attach volume "+volume.Name+" to instance "+unikInstance.UnikInstanceID, err)
	}

	createTagsInput := &ec2.CreateTagsInput{
		Resources: aws.StringSlice([]string{volume.VolumeId}),
		Tags: []*ec2.Tag{
			&ec2.Tag{
				Key:   aws.String("ATTACHED_UNIK_INSTANCE"),
				Value: aws.String(unikInstance.UnikInstanceID),
			},
		},
	}
	createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
	if err != nil {
		return lxerrors.New("failed to tag volume " + volume.Name, err)
	}
	logger.WithFields(lxlog.Fields{"output": createTagsOutput}).Debugf("tagged volume " + volume.Name)
	logger.WithFields(lxlog.Fields{"output": attachVolumeOutput}).Infof("attached volume " + volume.Name+ " to instance "+ unikInstanceId)

	return nil
}
