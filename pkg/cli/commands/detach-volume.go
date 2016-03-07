package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/pkg/types"
	"net/http"
)

func DetachVolume(config types.UnikConfig, volumeName string, force, verbose bool) error {
	fmt.Printf("Detaching volume %s from all unik instances\n", volumeName)
	url := config.Url

	path := fmt.Sprintf("/volumes/"+volumeName+"/detach/?force=%v&verbose=%v", force, verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Post(url, path, nil, nil)
		if err != nil {
			return lxerrors.New("failed detaching volume", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed detaching volume, got message: " + string(body), err)
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
