package ec2api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/layer-x/unik/pkg/types"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/layerx-commons/lxlog"
)

const UNIK_BLOCK_DEVICE = "UNIK_BLOCK_DEVICE"

func CreateVolume(logger *lxlog.LxLogger, volumeName string, size int) (*types.Volume, error) {
	args := append([]string{
		"run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-v", unikernelCompilationDir + ":/unikernel",
		"rumpstager", "-mode", "aws", "-a", unikernelName,
	}, volumeArgs...)



	_, err := GetVolumeByIdOrName(logger, volumeName)
	if err == nil {
		return nil, lxerrors.New("cannot create, volume "+volumeName+" already exists", nil)
	}

	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	createVolumeInput := &ec2.CreateVolumeInput{
		Size: aws.Int64(int64(size)),
		VolumeType: aws.String("standard"),
		AvailabilityZone: aws.String(ec2Client.AvailabilityZone),
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
				Key:   aws.String(UNIK_BLOCK_DEVICE),
				Value: aws.String(volumeName),
			},
		},
	}
	createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
	if err != nil {
		defer DeleteVolume(logger, volumeName, true)
		return nil, lxerrors.New("failed to tag volume " + volumeName, err)
	}
	logger.WithFields(lxlog.Fields{
		"output": createTagsOutput,
	}).Debugf("tagged volume " + volumeName)
	volume, err := GetVolumeByIdOrName(logger, volumeName)
	if err != nil {
		defer DeleteVolume(logger, volumeName, true)
		return nil, lxerrors.New("failed to retrieve volume " + volumeName + " after it was just created...", err)
	}
	logger.WithFields(lxlog.Fields{
		"volume": volume,
	}).Debugf("created volume")
	return volume, nil
}
