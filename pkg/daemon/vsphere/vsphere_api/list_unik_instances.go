package vsphere_api
import (
	"github.com/layer-x/unik/pkg/types"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/docker/go/canonical/json"
	"github.com/layer-x/unik/pkg/daemon/state"
	vspheretypes "github.com/vmware/govmomi/vim25/types"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
)

func ListUnikInstances(unikState *state.UnikState, creds Creds) ([]*types.UnikInstance, error) {
	client, err := vsphere_utils.NewVsphereClient(creds.URL)
	if err != nil {
		return nil, lxerrors.New("creating new vsphere client ", err)
	}
	vms, err := client.Vms()
	if err != nil {
		return nil, lxerrors.New("retrieving list of vsphere vms", err)
	}
	lxlog.Debugf(logrus.Fields{"vms": vms}, "Found a collection of vms")
	unikInstances := []*types.UnikInstance{}
	for _, vm := range vms {
		lxlog.Debugf(logrus.Fields{"vm": vm}, "Found a vm")
		if vm.Config == nil {
			lxlog.Debugf(logrus.Fields{"Config": vm.Config}, "VM has no config")
			continue
		}
		metadata := vm.Config.Annotation
		var unikInstance types.UnikInstance
		err = json.Unmarshal([]byte(metadata), &unikInstance)
		if err != nil {
			lxlog.Warnf(logrus.Fields{"Annotation": vm.Config.Annotation, "err": err}, "could not read annotation")
			continue
		}
		//we use mac address as the vm id
		if vm.Config != nil && vm.Config.Hardware.Device != nil {
			FindEthLoop:
			for _, device := range vm.Config.Hardware.Device {
				switch device.(type){
				case *vspheretypes.VirtualVmxnet:
					eth := device.(*vspheretypes.VirtualVmxnet)
					unikInstance.VMID = eth.MacAddress
					break FindEthLoop
				default:
					continue
				}
			}
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
		for _, registeredUnikInstance := range unikState.UnikInstances {
			if unikInstance.VMID == registeredUnikInstance.VMID {
				unikInstance.PublicIp = registeredUnikInstance.PublicIp
				unikInstance.PrivateIp = registeredUnikInstance.PrivateIp
				break
			}
		}
		unikInstances = append(unikInstances, &unikInstance)
		if unikInstance.VMID == "" {
			lxlog.Warnf(logrus.Fields{"unik_instance": unikInstance}, "unik instance was found on vsphere but has not registered with known mac address yet")
		} else {
			lxlog.Debugf(logrus.Fields{"unik_instance": unikInstance}, "unik instance was found on vsphere")
		}
	}
	return unikInstances, nil
}