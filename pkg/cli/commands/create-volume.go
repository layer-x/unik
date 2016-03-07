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

func CreateVolume(config types.UnikConfig, volumeName string, size int, verbose bool) error {
	if size < 1 {
		size = 1
	}
	fmt.Printf("Creating volume %s of size %v GB\n", volumeName, size)
	url := config.Url

	path := fmt.Sprintf("/volumes/" + volumeName + "?size=%v&verbose=%v", size, verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Post(url, path, nil, nil)
		if err != nil {
			return lxerrors.New("failed creating volume", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed creating volume, got message: " + string(body), err)
		}
		return printUnikVolume(body)
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
				return printUnikVolume(body)
			}
			fmt.Printf("%s", string(line))
		}
	}
}


func printUnikVolume(body []byte) error {
	volume := types.Volume{}
	err := json.Unmarshal(body, &volume)
	if err != nil {
		return lxerrors.New("failed to retrieve volumes: " + string(body), err)
	}
	fmt.Printf("VOLUME NAME \t\t\t\t\t AWS_ID \t CREATED \t SIZE \t STATE \t ATTACHED-INSTANCE \n")
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
	return nil
}