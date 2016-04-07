package ec2api
import (
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/http"
	"github.com/layer-x/unik/pkg/types"
"github.com/layer-x/layerx-commons/lxlog"
)

//const hubUrl = "http://www.unikhub.tk/"
const hubUrl = "www.unikhub.tk"

func Push(logger lxlog.Logger, unikernelName string) error {
	var unikernel *types.Unikernel
	unikernels, err := ListUnikernels(logger)
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
	resp, body, err := lxhttpclient.Post(hubUrl, "/unikernels", nil, unikernel)
	if err != nil {
		return lxerrors.New("failed posting unikernel data to hub", err)
	}
	if resp.StatusCode != http.StatusAccepted {
		return lxerrors.New("failed posting unikernel data to hub: "+string(body), nil)
	}
	return nil
}
