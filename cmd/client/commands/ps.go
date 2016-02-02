package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
"encoding/json"
)

func Ps(config types.UnikConfig, appName string) error {
	url := config.Url
	_, body, err := lxhttpclient.Get(url, "/instances", nil)
	if err != nil {
		return lxerrors.New("failed retrieving instances", err)
	}
	var unikInstances []*types.UnikInstance
	err = json.Unmarshal(body, &unikInstances)
	if err != nil {
		return lxerrors.New("failed to retrieve instances: "+string(body), err)
	}
	fmt.Printf("INSTANCE ID \t\t\t UNIKERNEL \t\t\t PUBLIC IP \t\t\t PRIVATE IP \t\t\t STATE\n")
	for _, unikInstance := range unikInstances {
		if appName == "" || appName == unikInstance.AppName {
			fmt.Printf("%s \t %s \t %s \t %s \t %s\n",
				unikInstance.ID,
				unikInstance.AppName,
				unikInstance.PublicIp,
				unikInstance.PrivateIp,
				unikInstance.State)
		}
	}

	return nil
}