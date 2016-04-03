package unik_ec2_utils

import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/pkg/types"
	"time"
)

const UNIKERNEL_NAME = "UNIKERNEL_APP_NAME"

func GetUnikernelMetadata(image *ec2.Image) *types.Unikernel {
	unikernel := &types.Unikernel{}
	for _, tag := range image.Tags {
		if *tag.Key == UNIKERNEL_NAME {
			unikernel.UnikernelName = *tag.Value
		}
	}
	if unikernel.UnikernelName == "" {
		return nil
	}
	unikernel.Id = *image.ImageId
	unikernel.CreationDate = *image.CreationDate
	layout := "2006-01-02T15:04:05.000Zs"
	createdTime, err := time.Parse(layout, *image.CreationDate)
	if err != nil {
		unikernel.Created = createdTime.Unix()
	}
	return unikernel
}
