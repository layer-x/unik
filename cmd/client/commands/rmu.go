package commands

import (
	"bufio"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/cmd/types"
	"net/http"
)

func Rmu(config types.UnikConfig, unikernelName string, force, verbose bool) error {
	fmt.Printf("Deleting unikernel "+unikernelName+" force=%v\n", force)
	url := config.Url

	if !verbose {
		resp, body, err := lxhttpclient.Delete(url, "/unikernels/"+unikernelName+fmt.Sprintf("?force=%v", force), nil)
		if err != nil {
			return lxerrors.New("failed deleting unikernel", err)
		}
		if resp.StatusCode != http.StatusNoContent {
			return lxerrors.New("failed deleting unikernel, got message: "+string(body), err)
		}
		return nil
	} else {
		resp, err := lxhttpclient.DeleteAsync(url, "/unikernels/"+unikernelName+fmt.Sprintf("?force=%v&verbose=%v", force, verbose), nil)
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
				return printUnikernel(body)
			}
			fmt.Printf("%s", string(line))
		}
	}
}
