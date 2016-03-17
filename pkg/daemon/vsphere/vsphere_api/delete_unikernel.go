package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
"github.com/layer-x/unik/pkg/daemon/state"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func DeleteUnikernel(unikState *state.UnikState, creds Creds, unikernelId string, force bool) error {
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	unikInstances, err := ListUnikInstances(unikState, creds)
	if err != nil {
		return lxerrors.New("could not check to see running unik instances", err)
	}
	for _, instance := range unikInstances {
		if instance.UnikernelId == unikernelId {
			if force == true {
				err = DeleteUnikInstance(creds, instance.UnikInstanceID)
				if err != nil {
					return lxerrors.New("could not delete unik instance "+instance.UnikInstanceID, err)
				}
			} else {
				return lxerrors.New("attempted to delete unikernel "+unikernelId+", however instance "+instance.UnikInstanceID+" is still running. override with force=true", nil)
			}
		}
	}
	lxlog.Infof(logrus.Fields{"unikernel": unikernelId, "force": force}, "deleting unikernel")

	if _, ok := unikState.Unikernels[unikernelId]; ok {
		err = vsphereClient.Rmdir("unik/"+unikernelId)
		if err != nil {
			return lxerrors.New("failed to remove remote unikernel folder", err)
		}
		delete(unikState.Unikernels, unikernelId)

		err = unikState.Save()
		if err != nil {
			return lxerrors.New("failed to save updated unikernel index", err)
		}

		return nil
	}
	return lxerrors.New("unikernel not found", err)
}