package vsphere_api
import (
	"github.com/layer-x/unik/types"
	"github.com/layer-x/unik/cmd/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/docker/go/canonical/json"
)

func ListUnikInstances(creds Creds) ([]*types.UnikInstance, error) {
	client, err := vsphere_utils.NewVsphereClient(creds.url)
	if err != nil {
		return nil, lxerrors.New("creating new vsphere client ", err.Error())
	}
	vms, err := client.Vms()
	if err != nil {
		return nil, lxerrors.New("retrieving list of vsphere vms", nil)
	}
	unikInstances := []*types.UnikInstance{}
	for _, vm := range vms {
		if vm.Config == nil {
			continue
		}
		metadata := vm.Config.Annotation
		var unikInstance *types.UnikInstance
		err = json.Unmarshal([]byte(metadata), unikInstance)
		if err != nil || unikInstance == nil || unikInstance.UnikernelId == "" {
			continue
		}
		switch vm.Summary.Runtime.PowerState {
		case "poweredOn":
			unikInstance.State = "running"
			break
		case "poweredOff":
			unikInstance.State = "stopped"
			break
		case "suspended":
			unikInstance.State = "paused"
			break
		default:
			unikInstance.State = "unknown"
			break
		}
		if vm.Config != nil {
			unikInstance.VMID = vm.Config.Uuid
		}
		//Todo: determine a way to get vm public ip and vm private ip

		unikInstances = append(unikInstances, unikInstance)
	}
	return unikInstances, nil
}