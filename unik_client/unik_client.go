package unik_client
import (
	"github.com/layer-x/unik/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
"encoding/json"
)

type UnikClient struct {
	url string
}

func NewUnikClient(url string) *UnikClient {
	return &UnikClient{
		url: url,
	}
}

func (c *UnikClient) GetUnikInstances() ([]*types.UnikInstance, error) {
	_, body, err := lxhttpclient.Get(c.url, "/instances", nil)
	if err != nil {
		return nil, lxerrors.New("error requesting unik instance list", err)
	}
	var unikInstances []*types.UnikInstance
	err = json.Unmarshal(body, unikInstances)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal unik instance json", err)
	}
	return unikInstances, nil
}