package unik_ec2_utils
import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
)

const UNIKERNEL_APP_NAME = "UNIKERNEL_APP_NAME"

func GetUnikernelMetadata(image *ec2.Image) *types.Unikernel {
	unikernel := &types.Unikernel{}
	for _, tag := range image.Tags {
		if *tag.Key == UNIKERNEL_APP_NAME {
			unikernel.UnikernelName = *tag.Value
		}
	}
	if unikernel.UnikernelName == "" {
		return nil
	}
	unikernel.AMI = *image.ImageId
	unikernel.CreationDate = *image.CreationDate
	return unikernel
}