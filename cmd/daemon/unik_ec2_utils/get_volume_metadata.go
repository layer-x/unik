package unik_ec2_utils
import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/types"
)

func GetVolumeMetadata(awsVol *ec2.Volume) *types.Volume {
	volume := &types.Volume{}
	attachedUnikInstance := ""
	for _, tag := range awsVol.Tags {
		if *tag.Key == "UNIK_VOLUME_NAME" {
			volume.Name = *tag.Value
		}
		if *tag.Key == "ATTACHED_UNIK_INSTANCE" {
			attachedUnikInstance = *tag.Value
		}
	}
	if volume.Name == "" {
		return nil
	}
	if awsVol.CreateTime != nil {
		volume.CreateTime = *awsVol.CreateTime
	}
	if awsVol.Size != nil {
		volume.Size = int(*awsVol.Size)
	}
	if awsVol.State != nil {
		volume.State = *awsVol.State
	}
	if awsVol.VolumeId != nil {
		volume.VolumeId = *awsVol.VolumeId
	}
	for _, awsAttachment := range awsVol.Attachments {
		attachment := &types.Attachment{}
		if awsAttachment.AttachTime != nil {
			attachment.AttachTime = *awsAttachment.AttachTime
		}
		if awsAttachment.Device != nil {
			attachment.Device = *awsAttachment.Device
		}
		if awsAttachment.InstanceId != nil {
			attachment.InstanceId = *awsAttachment.InstanceId
		}
		if awsAttachment.State != nil {
			attachment.State = *awsAttachment.State
		}
		if awsAttachment.VolumeId != nil {
			attachment.VolumeId = *awsAttachment.VolumeId
		}
		attachment.UnikInstanceId = attachedUnikInstance
		volume.Attachments = append(volume.Attachments, attachment)
	}
	return volume
}