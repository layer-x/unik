package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/http"
)

func Run(config types.UnikConfig, unikernelName, instanceName string, instances int) error {
	fmt.Printf("Running %v instances of unikernel "+unikernelName+"\n", instances)
	url := config.Url
	resp, body, err := lxhttpclient.Post(url, "/unikernels/"+unikernelName+"/run"+fmt.Sprintf("?instances=%v&name=%s", instances, instanceName), nil, nil)
	if err != nil {
		return lxerrors.New("failed running app", err)
	}
	if resp.StatusCode != http.StatusAccepted {
		return lxerrors.New("failed running app, got message: "+string(body), err)
	}
	return nil
}