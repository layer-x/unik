package commands

import (
	"bufio"
	"encoding/json"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/types"
)

const TERMINATE_OUTPUT = "BEGIN_JSON_DATA\n"

func Unikernels(config types.UnikConfig, verbose bool) error {
	url := config.Url

	if !verbose {
		_, body, err := lxhttpclient.Get(url, fmt.Sprintf("/unikernels?verbose=%v", verbose), nil)
		if err != nil {
			return lxerrors.New("failed retrieving unikernels", err)
		}
		return printUnikernels(body)
	} else {
		resp, err := lxhttpclient.GetAsync(url, fmt.Sprintf("/unikernels?verbose=%v", verbose), nil)
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
				return printUnikernels(body)
			}
			fmt.Printf("%s", string(line))
		}
	}
}

func printUnikernels(body []byte) error {
	var unikernels []*types.Unikernel
	err := json.Unmarshal(body, &unikernels)
	if err != nil {
		return lxerrors.New("failed to retrieve unikernels: "+string(body), err)
	}
	fmt.Printf("UNIKERNEL \t\t\t AMI \t\t\t CREATED\n")
	for _, unikInstance := range unikernels {
		fmt.Printf("%s \t\t\t %s \t\t %ss\n",
			unikInstance.UnikernelName,
			unikInstance.ImageId,
			unikInstance.CreationDate)
	}
	return nil
}
