package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/pkg/types"
	"net/http"
	"encoding/json"
)

func ListVolumes(config types.UnikConfig, verbose bool) error {
	fmt.Printf("Listing volumes...\n")
	url := config.Url
	path := fmt.Sprintf("/volumes" + "?&verbose=%v", verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Get(url, path, nil)
		if err != nil {
			return lxerrors.New("failed listing volumes", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed listing volumes, got message: " + string(body), err)
		}
		return printUnikVolumes(body)
	} else {
		resp, err := lxhttpclient.GetAsync(url, path, nil)
		if err != nil {
			return lxerrors.New("error performing GET request", err)
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
				return printUnikVolumes(body)
			}
			fmt.Printf("%s", string(line))
		}
	}
}


func printUnikVolumes(body []byte) error {
	volumes := []*types.Volume{}
	err := json.Unmarshal(body, &volumes)
	if err != nil {
		return lxerrors.New("failed to retrieve volumes: " + string(body), err)
	}
	fmt.Printf("VOLUME NAME \t\t\t\t\t AWS_ID \t CREATED \t SIZE \t STATE \t ATTACHED-INSTANCE \n")
	for _, volume := range volumes {
		instanceId := ""
		if volume.IsAttached() {
			instanceId = volume.Attachments[0].UnikInstanceId
		}
		fmt.Printf("%s \t %s \t %s \t %v \t %s \t %s\n",
			volume.Name,
			volume.VolumeId,
			volume.CreateTime.String(),
			volume.Size,
			volume.State,
			instanceId)
	}

	return nil
}