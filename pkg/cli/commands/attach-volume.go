package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/pkg/types"
	"net/http"
)

func AttachVolume(config types.UnikConfig, unikInstanceName, volumeName, device string, verbose bool) error {
	fmt.Printf("Attaching volume %s to unik instance %s as '%s'\n", volumeName, unikInstanceName, device)
	url := config.Url

	path := fmt.Sprintf("/instances/"+unikInstanceName+"/volumes/"+volumeName+"?device=%s&verbose=%v", device, verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Post(url, path, nil, nil)
		if err != nil {
			return lxerrors.New("failed attaching volume", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed attaching volume, got message: " + string(body), err)
		}
		println(string(body))
		return nil
	} else {
		resp, err := lxhttpclient.PostAsync(url, path, nil, nil)
		if err != nil {
			return lxerrors.New("error performing POST request", err)
		}
		reader := bufio.NewReader(resp.Body)
		for {
			line, err := reader.ReadBytes('\n')
			if err != nil {
				return lxerrors.New("reading line", err)
			}
			if string(line) == TERMINATE_OUTPUT {
				body, err := reader.ReadBytes('\n')
				if err != nil {
					return lxerrors.New("reading final line", err)
				}
				println(string(body))
				return nil
			}
			fmt.Printf("%s", string(line))
		}
	}
}
