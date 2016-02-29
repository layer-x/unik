package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/ec2/ec2_metada_client"
)


func DeleteVolume(volumeNameOrId string, force bool) error {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	volume, err := GetVolumeByIdOrName(volumeNameOrId)
	if err != nil {
		return lxerrors.New("could not get list of volumes", err)
	}

	if volume.IsAttached() {
		if !force {
			return lxerrors.New("volume is still attached to instance " + volume.Attachments[0].InstanceId + ", try again with force=true to override", err)
		} else {
			err = DetachVolume(volumeNameOrId, true)
			if err != nil {
				return lxerrors.New("could not detach volume "+volume.Name+" from instance "+volume.Attachments[0].InstanceId, err)
			}
		}
	}

	deleteVolumeInput := &ec2.DeleteVolumeInput{
		VolumeId: aws.String(volume.VolumeId),
	}
	_, err = ec2Client.DeleteVolume(deleteVolumeInput)
	if err != nil {
		return lxerrors.New("failed to delete volume "+volume.VolumeId, err)
	}

	return nil
}
