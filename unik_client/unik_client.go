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

func (c *UnikClient) RunUnikernel(unikernelName, instanceName string, instances int, tags map[string]string, env map[string]string) error {
	tagString := ""
	for key, val := range tags {
		tagString += key+"="+val+","
	}
	tagString = strings.TrimSuffix(tagString, ",")

	envString := ""
	for key, val := range env {
		envString += key+"="+val+","
	}
	envString = strings.TrimSuffix(envString, ",")

	path := "/unikernels/" + unikernelName + "/run" + fmt.Sprintf("?instances=%v&name=%s&tags=%s&env=%s", instances, instanceName, tagString, envString)
	resp, body, err := lxhttpclient.Post(c.url, path, nil, nil)
	if err != nil {
		return lxerrors.New("failed running unikernel", err)
	}
	if resp.StatusCode != http.StatusOK {
		return lxerrors.New("failed running unikernel, got message: " + string(body) + " with status code "+fmt.Sprintf("%v", resp.StatusCode), err)
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

func (c *UnikClient) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	path := fmt.Sprintf("/volumes/"+volumeName+"?size=%v", size)
	resp, body, err := lxhttpclient.Post(c.url, path, nil, nil)
	if err != nil {
		return nil, lxerrors.New("failed to create volume", err)
	}
	if resp.StatusCode != http.StatusOK {
		return nil, lxerrors.New("failed to create volume, got message: "+string(body), err)
	}
	var volume *types.Volume
	err = json.Unmarshal(body, volume)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal volume json", err)
	}
	return volume, nil
}

func (c *UnikClient) DeleteVolume(volumeName string, force bool) (string, error) {
	path := fmt.Sprintf("/volumes/"+volumeName+"?force=%v", force)
	resp, body, err := lxhttpclient.Delete(c.url, path, nil)
	if err != nil {
		return "", lxerrors.New("failed to delete volume", err)
	}
	if resp.StatusCode != http.StatusOK {
		return "", lxerrors.New("failed to delete volume, got message: "+string(body), err)
	}

	return string(body), nil
}

func (c *UnikClient) AttachVolume(volumeName, instanceName, device string) (string, error) {
	path := fmt.Sprintf("/instances/"+instanceName+"/volumes/"+volumeName+"?device=%s", device)
	resp, body, err := lxhttpclient.Post(c.url, path, nil, nil)
	if err != nil {
		return "", lxerrors.New("failed to attach volume", err)
	}
	if resp.StatusCode != http.StatusOK {
		return "", lxerrors.New("failed to attach volume, got message: "+string(body), err)
	}

	return string(body), nil
}


func (c *UnikClient) DetachVolume(volumeName string, force bool) (string, error) {
	path := fmt.Sprintf("/volumes/"+volumeName+"/detach/?force=%v", force)
	resp, body, err := lxhttpclient.Post(c.url, path, nil, nil)
	if err != nil {
		return "", lxerrors.New("failed to detach volume", err)
	}
	if resp.StatusCode != http.StatusOK {
		return "", lxerrors.New("failed to detach volume, got message: "+string(body), err)
	}

	return string(body), nil
}

func (c *UnikClient) GetVolumes() ([]*types.Volume, error) {
	path := fmt.Sprintf("/volumes")
	resp, body, err := lxhttpclient.Get(c.url, path, nil)
	if err != nil {
		return nil, lxerrors.New("failed listing volumes", err)
	}
	if resp.StatusCode != http.StatusOK {
		return nil, lxerrors.New("failed listing volumes, got message: " + string(body), err)
	}
	volumes := []*types.Volume{}
	err = json.Unmarshal(body, &volumes)
	if err != nil {
		return nil, lxerrors.New("failed to retrieve volumes: " + string(body), err)
	}
	return volumes, nil
}

func (c *UnikClient) GetVolume(volumeName string) (*types.Volume, error) {
	volumes, err := c.GetVolumes()
	if err != nil {
		return nil, lxerrors.New("could not get volume list", err)
	}
	for _, volume := range volumes {
		if strings.Contains(volume.Name, volumeName) {
			return volume, nil
		}
	}
	return nil, lxerrors.New("could not find volume "+volumeName, nil)
}