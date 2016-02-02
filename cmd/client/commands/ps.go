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
	unikInstances := []*types.UnikInstance{}
	err = json.Unmarshal(body, &unikInstances)
	if err != nil {
		return lxerrors.New("failed to retrieve instances: "+string(body), err)
	}
	fmt.Printf("INSTANCE ID \t\t\t\t\t UNIKERNEL \t PUBLIC IP \t PRIVATE IP \t STATE\n")
	for _, unikInstance := range unikInstances {
		if (appName == "" || appName == unikInstance.AppName) && unikInstance.State != "terminated" {
			fmt.Printf("%s \t %s \t %s \t %s \t %s\n",
				unikInstance.UnikInstanceID,
				unikInstance.AppName,
				unikInstance.PublicIp,
				unikInstance.PrivateIp,
				unikInstance.State)
//			fmt.Printf("UnikInstanceID: %s\n", unikInstance.UnikInstanceID)
//			fmt.Printf("UnikernelId: %s\n", unikInstance.UnikernelId)
//			fmt.Printf("AmazonID: %s\n", unikInstance.AmazonID)
//			fmt.Printf("AppName: %s\n", unikInstance.AppName)
//			fmt.Printf("PrivateIp: %s\n", unikInstance.PrivateIp)
//			fmt.Printf("PublicIp: %s\n", unikInstance.PublicIp)
//			fmt.Printf("State: %s\n", unikInstance.State)
		}
	}

	return nil
}