package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
"github.com/layer-x/layerx-commons/lxlog"
)


func DetachVolume(logger lxlog.Logger, volumeNameOrId string, force bool) error {
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	volume, err := GetVolumeByIdOrName(logger, volumeNameOrId)
	if err != nil {
		return lxerrors.New("could not find volume "+volumeNameOrId, err)
	}
	if !volume.IsAttached() {
		return lxerrors.New("volume " + volume.Name + " is not currently attached to an instance", err)
	}
	detachVolumeInput := &ec2.DetachVolumeInput{
		VolumeId: aws.String(volume.VolumeId),
		Force: aws.Bool(force),
	}
	_, err = ec2Client.DetachVolume(detachVolumeInput)
	if err != nil {
		return lxerrors.New("could not detach volume "+volume.Name+" from instance "+volume.Attachments[0].InstanceId, err)
	}

	return nil
}
