package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/ec2_metada_client"
)


func AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	if deviceName == "" {
		return lxerrors.New("device name must be specified", nil)
	}
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	volume, err := GetVolumeByIdOrName(volumeNameOrId)
	if err != nil {
		return lxerrors.New("could not find volume "+volumeNameOrId, err)
	}
	if volume.IsAttached() {
		return lxerrors.New("volume " + volume.Name + " is already attached to instance "+volume.Attachments[0].InstanceId, err)
	}
	unikInstance, err := GetUnikInstanceByPrefixOrName(unikInstanceId)
	if err != nil {
		return lxerrors.New("failed to retrieve unik instance", err)
	}

	attachVolumeInput := &ec2.AttachVolumeInput{
		VolumeId: aws.String(volume.VolumeId),
		InstanceId: aws.String(unikInstance.AmazonID),
		Device: aws.String(deviceName),
	}
	_, err = ec2Client.AttachVolume(attachVolumeInput)
	if err != nil {
		return lxerrors.New("could not attach volume "+volume.Name+" to instance "+unikInstance.UnikInstanceID, err)
	}

	return nil
}
