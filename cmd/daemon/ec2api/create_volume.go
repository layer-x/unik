package ec2api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/ec2_metada_client"
	"github.com/layer-x/unik/types"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
)

const UNIK_VOLUME_NAME = "UNIK_VOLUME_NAME"

func CreateVolume(volumeName string, size int) (*types.Volume, error) {
	_, err := GetVolumeByIdOrName(volumeName)
	if err == nil {
		return nil, lxerrors.New("cannot create, volume "+volumeName+" already exists", nil)
	}

	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	createVolumeInput := &ec2.CreateVolumeInput{
		Size: aws.Int64(int64(size)),
		VolumeType: aws.String("standard"),
	}
	awsVol, err := ec2Client.CreateVolume(createVolumeInput)
	if err != nil {
		return nil, lxerrors.New("error performing get volume request to ec2", err)
	}

	createTagsInput := &ec2.CreateTagsInput{
		Resources: aws.StringSlice([]string{*awsVol.VolumeId}),
		Tags: []*ec2.Tag{
			&ec2.Tag{
				Key:   aws.String("Name"),
				Value: aws.String(volumeName),
			},
			&ec2.Tag{
				Key:   aws.String(UNIK_VOLUME_NAME),
				Value: aws.String(volumeName),
			},
		},
	}
	createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
	if err != nil {
		defer DeleteVolume(volumeName, true)
		return nil, lxerrors.New("failed to tag volume " + volumeName, err)
	}
	lxlog.Debugf(logrus.Fields{"output": createTagsOutput}, "tagged volume " + volumeName)
	volume, err := GetVolumeByIdOrName(volumeName)
	if err != nil {
		defer DeleteVolume(volumeName, true)
		return nil, lxerrors.New("failed to retrieve volume " + volumeName + " after it was just created...", err)
	}
	lxlog.Debugf(logrus.Fields{"volume": volume}, "created volume")
	return volume, nil
}
