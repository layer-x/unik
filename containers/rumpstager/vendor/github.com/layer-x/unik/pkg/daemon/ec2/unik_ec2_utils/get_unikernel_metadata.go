package unik_ec2_utils

import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"strings"
	"encoding/json"
)

const UNIKERNEL_NAME = "UNIKERNEL_APP_NAME"
const UNIK_BLOCK_DEVICE = "UNIK_BLOCK_DEVICE"

func GetUnikernelMetadata(image *ec2.Image) *types.Unikernel {
	unikernel := &types.Unikernel{}
	for _, tag := range image.Tags {
		if *tag.Key == UNIKERNEL_NAME {
			unikernel.UnikernelName = *tag.Value
		}
		if strings.Contains(*tag.Key, UNIK_BLOCK_DEVICE) {
			var deviceMapping types.DeviceMapping
			err := json.Unmarshal([]byte(*tag.Value), &deviceMapping)
			if err == nil {
				unikernel.Devices = append(unikernel.Devices, &deviceMapping)
			}
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
