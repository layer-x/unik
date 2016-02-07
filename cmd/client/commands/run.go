package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/cmd/types"
	"net/http"
)

func Run(config types.UnikConfig, unikernelName, instanceName string, instances int, verbose bool) error {
	fmt.Printf("Running %v instances of unikernel "+unikernelName+"\n", instances)
	url := config.Url

	if !verbose {
		resp, body, err := lxhttpclient.Post(url, "/unikernels/"+unikernelName+"/run"+fmt.Sprintf("?instances=%v&name=%s&verbose=%v", instances, instanceName, verbose), nil, nil)
		if err != nil {
			return lxerrors.New("failed running unikernel", err)
		}
		if resp.StatusCode != http.StatusAccepted {
			return lxerrors.New("failed running unikernel, got message: "+string(body), err)
		}
		return nil
	} else {
		resp, err := lxhttpclient.PostAsync(url, "/unikernels/"+unikernelName+"/run"+fmt.Sprintf("?instances=%v&name=%s&verbose=%v", instances, instanceName, verbose), nil, nil)
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
