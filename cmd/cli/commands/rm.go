package commands

import (
	"bufio"
	"encoding/json"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/types"
	"net/http"
)

func Rm(config types.UnikConfig, instanceId string, verbose bool) error {
	url := config.Url
	if !verbose {
		fmt.Printf("Deleting instance " + instanceId + "\n")
		resp, body, err := lxhttpclient.Delete(url, "/instances/"+instanceId, nil)
		if err != nil {
			return lxerrors.New("failed deleting instance", err)
		}
		if resp.StatusCode != http.StatusNoContent {
			return lxerrors.New("failed deleting instance, got message: "+string(body), err)
		}
		return nil

	} else {
		resp, err := lxhttpclient.DeleteAsync(url, "/instances/"+instanceId+"?verbose=true", nil)
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
				return printUnikInstance(body)
			}
			fmt.Printf("%s", string(line))
		}
	}
}

func printUnikInstance(body []byte) error {
	unikInstance := types.UnikInstance{}
	err := json.Unmarshal(body, &unikInstance)
	if err != nil {
		return lxerrors.New("failed to retrieve instances: "+string(body), err)
	}
	fmt.Printf("INSTANCE ID \t\t\t\t\t UNIKERNEL \t PUBLIC IP \t PRIVATE IP \t STATE \t NAME \n")
	fmt.Printf("%s \t %s \t %s \t %s \t %s \t %s\n",
		unikInstance.UnikInstanceID,
		unikInstance.UnikernelName,
		unikInstance.PublicIp,
		unikInstance.PrivateIp,
		unikInstance.State,
		unikInstance.UnikInstanceName)
	return nil
}
