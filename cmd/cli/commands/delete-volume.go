package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/types"
	"net/http"
)

func DeleteVolume(config types.UnikConfig, volumeName string, force, verbose bool) error {
	fmt.Printf("Deleting volume %s force==%v\n", volumeName, force)
	url := config.Url

	path := fmt.Sprintf("/volumes/"+volumeName+"?force=%v&verbose=%v", force, verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Delete(url, path, nil)
		if err != nil {
			return lxerrors.New("failed deleting volume", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed deleting volume, got message: " + string(body), err)
		}
		fmt.Printf("%s\n", string(body))
		return nil
	} else {
		resp, err := lxhttpclient.DeleteAsync(url, path, nil)
		if err != nil {
			return lxerrors.New("error performing DELETE request", err)
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
				fmt.Printf("%s\n", string(body))
				return nil
			}
			fmt.Printf("%s", string(line))
		}
	}
}
