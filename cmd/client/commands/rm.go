package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/http"
	"fmt"
)

func Rm(config types.UnikConfig, instanceId string) error {
	fmt.Printf("Deleting instance "+instanceId+"\n")
	url := config.Url
	resp, body, err := lxhttpclient.Delete(url, "/instances/"+instanceId, nil)
	if err != nil {
		return lxerrors.New("failed deleting instance", err)
	}
	if resp.StatusCode != http.StatusAccepted {
		return lxerrors.New("failed deleting instance, got message: "+string(body), err)
	}
	return nil
}