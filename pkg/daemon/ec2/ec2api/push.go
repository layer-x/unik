package ec2api
import (
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/http"
	"github.com/layer-x/unik/pkg/types"
	"encoding/json"
)

//const hubUrl = "http://www.unikhub.tk/"
const hubUrl = "http://ec2-54-215-251-128.us-west-1.compute.amazonaws.com:9999/"

func Push(unikernelName string) error {
	var unikernel *types.Unikernel
	unikernels, err := ListUnikernels()
	if err != nil {
		return lxerrors.New("getting unikernel list", err)
	}
	for _, uk := range unikernels {
		if uk.UnikernelName == unikernelName {
			unikernel = uk
			break
		}
	}
	if unikernel == nil {
		return lxerrors.New("unikernel "+unikernelName+" not found", nil)
	}
	data, err := json.Marshal(unikernel)
	if err != nil {
		return lxerrors.New("converting unikernel to json", err)
	}
	resp, body, err := lxhttpclient.Post(hubUrl, "/unikernels", nil, data)
	if err != nil {
		return lxerrors.New("failed posting unikernel data to hub", err)
	}
	if resp.StatusCode != http.StatusAccepted {
		return lxerrors.New("failed posting unikernel data to hub: "+string(body), nil)
	}
	return nil
}