package unik_client
import (
	"github.com/layer-x/unik/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
"encoding/json"
"net/http"
"fmt"
	"strings"
"io"
	"bufio"
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
	err = json.Unmarshal(body, &unikInstances)
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

func (c *UnikClient) ListUnikernels() ([]*types.Unikernel, error) {
	_, body, err := lxhttpclient.Get(c.url, fmt.Sprintf("/unikernels?verbose=%v", false), nil)
	if err != nil {
		return nil, lxerrors.New("failed retrieving unikernels", err)
	}
	var unikernels []*types.Unikernel
	err = json.Unmarshal(body, &unikernels)
	if err != nil {
		return nil, lxerrors.New("failed to retrieve unikernels: "+string(body), err)
	}
	return unikernels, nil
}

func (c *UnikClient) DeleteUnikernel(unikernelName string, force bool) error {
	resp, body, err := lxhttpclient.Delete(c.url, "/unikernels/"+unikernelName+fmt.Sprintf("?force=%v", force), nil)
	if err != nil {
		return lxerrors.New("failed deleting unikernel", err)
	}
	if resp.StatusCode != http.StatusNoContent {
		return lxerrors.New("failed deleting unikernel, got message: "+string(body), err)
	}
	return nil
}

func (c *UnikClient) GetUnikInstanceLogs(unikInstanceId string) (string, error) {
	resp, body, err := lxhttpclient.Get(c.url, "/instances/"+unikInstanceId+"/logs"+fmt.Sprintf("?follow=%v", false), nil)
	if err != nil {
		return "", lxerrors.New("failed retrieving logs", err)
	}
	if resp.StatusCode != http.StatusOK {
		return "", lxerrors.New("failed retrieving logs, got message: "+string(body), err)
	}
	return string(body), nil
}

func (c *UnikClient) FollowUnikInstanceLogs(unikInstanceId string, stdout io.Writer) error {
	resp, err := http.Get("http://" + c.url + "/instances/" + unikInstanceId + "/logs" + fmt.Sprintf("?follow=%v", true))
	if err != nil {
		return lxerrors.New("error performing GET request", err)
	}
	reader := bufio.NewReader(resp.Body)
	for {
		line, err := reader.ReadBytes('\n')
		if err != nil {
			return lxerrors.New("reading line", err)
		}
		stdout.Write(append(line, byte('\n')))
	}
}
