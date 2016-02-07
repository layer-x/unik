package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/cmd/types"
	"net/http"
)

func Logs(config types.UnikConfig, unikInstanceId string, follow bool) error {
	url := config.Url
	if !follow {
		resp, body, err := lxhttpclient.Get(url, "/instances/"+unikInstanceId+"/logs"+fmt.Sprintf("?follow=%v", follow), nil)
		if err != nil {
			return lxerrors.New("failed retrieving logs", err)
		}
		if resp.StatusCode != http.StatusOK {
			return lxerrors.New("failed retrieving logs, got message: "+string(body), err)
		}
		fmt.Printf("%s\n", string(body))
		return nil
	} else {
		resp, err := http.Get("http://" + url + "/instances/" + unikInstanceId + "/logs" + fmt.Sprintf("?follow=%v", follow))
		if err != nil {
			return lxerrors.New("error performing GET request", err)
		}
		reader := bufio.NewReader(resp.Body)
		for {
			line, err := reader.ReadBytes('\n')
			if err != nil {
				return lxerrors.New("reading line", err)
			}
			fmt.Printf("%s", string(line))
		}
	}
}
