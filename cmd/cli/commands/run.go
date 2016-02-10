package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/types"
	"net/http"
	"strings"
)

func Run(config types.UnikConfig, unikernelName, instanceName string, instances int, tags, env []string, verbose bool) error {
	fmt.Printf("Running %v instances of unikernel "+unikernelName+"\n", instances)
	url := config.Url
	var tagString string
	var envString string
	for _, tag := range tags {
		tagString += tag+","
	}
	tagString = strings.TrimSuffix(tagString, ",")

	for _, envVar := range env {
		envString += envVar+","
	}
	envString = strings.TrimSuffix(envString, ",")

	path := "/unikernels/"+unikernelName+"/run"+fmt.Sprintf("?instances=%v&name=%s&tags=%s&env=%s&verbose=%v", instances, instanceName, tagString, envString, verbose)
	if !verbose {
		resp, body, err := lxhttpclient.Post(url, path, nil, nil)
		if err != nil {
			return lxerrors.New("failed running unikernel", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed running unikernel, got message: "+string(body), err)
		}
		return nil
	} else {
		resp, err := lxhttpclient.PostAsync(url, path, nil, nil)
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
}
