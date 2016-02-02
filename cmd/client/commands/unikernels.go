package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"encoding/json"
)

func Images(config types.UnikConfig) error {
	url := config.Url
	_, body, err := lxhttpclient.Get(url, "/unikernels", nil)
	if err != nil {
		return lxerrors.New("failed retrieving unikernels", err)
	}
	var unikernels []*types.Unikernel
	err = json.Unmarshal(body, &unikernels)
	if err != nil {
		return lxerrors.New("failed to retrieve unikernels: " + string(body), err)
	}
	fmt.Printf("UNIKERNEL \t\t\t AMI \t\t\t CREATED\n")
	for _, unikInstance := range unikernels {
		fmt.Printf("%s \t\t\t %s \t\t %ss\n",
			unikInstance.UnikernelName,
			unikInstance.AMI,
			unikInstance.CreationDate)
	}
	return nil
}
