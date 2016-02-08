package unik_client
import (
	"github.com/layer-x/unik/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
"encoding/json"
"net/http"
"fmt"
	"strings"
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

func (c *UnikClient) DeleteUnikInstance(instanceId string) error {
	resp, body, err := lxhttpclient.Delete(c.url, "/instances/"+instanceId, nil)
	if err != nil {
		return lxerrors.New("failed deleting instance", err)
	}
	if resp.StatusCode != http.StatusNoContent {
		return lxerrors.New("failed deleting instance, got message: "+string(body), err)
	}
	return nil
}

func (c *UnikClient) RunUnikernel(unikernelName, instanceName string, instances int, tags map[string]string) error {
	tagString := ""
	for key, val := range tags {
		tagString += key+"="+val+","
	}
	tagString = strings.TrimSuffix(tagString, ",")

	path := "/unikernels/" + unikernelName + "/run" + fmt.Sprintf("?instances=%v&name=%s&tags=%s", instances, instanceName, tagString)
	resp, body, err := lxhttpclient.Post(c.url, path, nil, nil)
	if err != nil {
		return lxerrors.New("failed running unikernel", err)
	}
	if resp.StatusCode != http.StatusAccepted {
		return lxerrors.New("failed running unikernel, got message: " + string(body), err)
	}
	return nil
}