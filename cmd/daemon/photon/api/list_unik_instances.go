package api
import (
	"github.com/layer-x/unik/types"
	"github.com/vmware/photon-controller-go-sdk/photon"
	"github.com/layer-x/layerx-commons/lxerrors"
	"encoding/json"
)

func ListUnikInstances(client *photon.Client, projectId string) ([]*types.UnikInstance, error) {
	client.Images.CreateFromFile()
	client.VMs.GetNetworks()
	vms, err := client.Projects.GetVMs(projectId, nil)
	if err != nil {
		return nil, lxerrors.New("retrieving vm list from photon-controller", err)
	}
	var unikInstances []*types.UnikInstance
	for _, vm := range vms.Items {
		unikInstance := GetUnikInstanceMetadata(vm)
		if unikInstance != nil {
			unikInstances = append(unikInstances, unikInstance)
		}
	}
	return unikInstances, nil
}

func GetUnikInstanceMetadata(vm *photon.VM) (*types.UnikInstance) {
	var unikInstance *types.UnikInstance
	for _, tag := range vm.Tags {
		json.Unmarshal([]byte(tag), unikInstance)
		if unikInstance != nil {
			break
		}
	}
	if unikInstance == nil {
		return nil
	}
	unikInstance.UnikInstanceData = vm.State
}

//'CREATING' or 'STARTED' or 'SUSPENDED' or 'STOPPED' or 'ERROR' or 'DELETED'