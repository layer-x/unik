package commands

import (
	"bufio"
	"encoding/json"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/cmd/types"
)

func Ps(config types.UnikConfig, unikernelName string, verbose bool) error {
	url := config.Url
	if !verbose {
		_, body, err := lxhttpclient.Get(url, fmt.Sprintf("/instances?verbose=%v", verbose), nil)
		if err != nil {
			return lxerrors.New("failed retrieving instances", err)
		}
		printUnikInstances(unikernelName, body)
	} else {
		resp, err := lxhttpclient.GetAsync(url, fmt.Sprintf("/instances?verbose=%v", verbose), nil)
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
				return printUnikInstances(unikernelName, body)
			}
			fmt.Printf("%s", string(line))
		}
	}

	return nil
}

func printUnikInstances(unikernelName string, body []byte) error {
	unikInstances := []*types.UnikInstance{}
	err := json.Unmarshal(body, &unikInstances)
	if err != nil {
		return lxerrors.New("failed to retrieve instances: "+string(body), err)
	}
	fmt.Printf("INSTANCE ID \t\t\t\t\t UNIKERNEL \t PUBLIC IP \t PRIVATE IP \t STATE \t NAME \n")
	for _, unikInstance := range unikInstances {
		if (unikernelName == "" || unikernelName == unikInstance.UnikernelName) && unikInstance.State != "terminated" {
			fmt.Printf("%s \t %s \t %s \t %s \t %s \t %s\n",
				unikInstance.UnikInstanceID,
				unikInstance.UnikernelName,
				unikInstance.PublicIp,
				unikInstance.PrivateIp,
				unikInstance.State,
				unikInstance.UnikInstanceName)
		}
	}
	return nil
}
