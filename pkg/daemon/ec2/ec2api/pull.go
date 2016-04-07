package ec2api

import (
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"encoding/json"
	"strings"
"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/aws/aws-sdk-go/service/ec2"
"github.com/layer-x/layerx-commons/lxlog"
)

func Pull(logger lxlog.Logger, unikernelName string) error {
	_, data, err := lxhttpclient.Get(hubUrl, "/unikernels", nil)
	if err != nil {
		return lxerrors.New("retreiving public unikernel list", err)
	}
	var unikernels map[string]*types.Unikernel
	err = json.Unmarshal(data, &unikernels)
	if err != nil {
		return lxerrors.New("converting json to unikernel map", err)
	}
	var unikernel *types.Unikernel
	for _, uk := range unikernels {
		if strings.Contains(uk.UnikernelName, unikernelName) {
			unikernel = uk
			break
		}
	}
	if unikernel == nil {
		return lxerrors.New("unikernel "+unikernelName+" not found in unik hub", nil)
	}

	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	region, err := ec2_metada_client.GetRegion()
	if err != nil {
		return lxerrors.New("getting region", err)
	}
	newName := strings.Replace(unikernel.UnikernelName, "-public", "", -1)
	copyImageInput := &ec2.CopyImageInput{
		Name: aws.String(newName),
		SourceImageId: aws.String(unikernel.Id),
		SourceRegion: aws.String(region),
	}
	_, err = ec2Client.CopyImage(copyImageInput)
	if err != nil {
		return lxerrors.New("performing ec2 copy image", err)
	}
	return nil
}
