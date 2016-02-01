package commands
import (
	"strings"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
)

func Login(url string) error {
	url = "http://" + strings.TrimPrefix(url, "http://") + ":3000"
	_, _, err := lxhttpclient.Get(url, "/apps", nil)
	if err != nil {
		return lxerrors.New("could not reach endpoint "+url, err)
	}
	return nil
}