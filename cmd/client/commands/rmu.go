package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/http"
)

func Rmu(config types.UnikConfig, unikernelName string, force bool) error {
	fmt.Printf("Deleting unikernel "+unikernelName+" force=%v\n",force)
	url := config.Url
	resp, body, err := lxhttpclient.Delete(url, "/unikernels/"+unikernelName+fmt.Sprintf("?force=%v", force), nil)
	if err != nil {
		return lxerrors.New("failed deleting unikernel", err)
	}
	if resp.StatusCode != http.StatusNoContent {
		return lxerrors.New("failed deleting unikernel, got message: "+string(body), err)
	}
	return nil
}