package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/pkg/types"
	"net/http"
)

func Push(config types.UnikConfig, unikernelId string, verbose bool) error {
	url := config.Url

	if !verbose {
		resp, body, err := lxhttpclient.Post(url, fmt.Sprintf("/unikernels/%s/push?verbose=%v", unikernelId, verbose), nil, nil)
		if err != nil {
			return lxerrors.New("failed to send push request", err)
		}
		if resp.StatusCode != http.StatusAccepted {
			return lxerrors.New("faield to send push request: "+string(body), nil)
		}
	} else {
		resp, err := lxhttpclient.PostAsync(url, fmt.Sprintf("/unikernels/%s/push?verbose=%v", unikernelId, verbose), nil, nil)
		if err != nil {
			return lxerrors.New("failed to send push request", err)
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
				if resp.StatusCode != http.StatusAccepted {
					return lxerrors.New("faield to send push request: "+string(body), nil)
				}
				return nil
			}
			fmt.Printf("%s", string(line))
		}
	}
	return nil
}
