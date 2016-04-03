package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/layer-x/unik/pkg/daemon/state"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func DeleteUnikernel(logger *lxlog.LxLogger, unikState *state.UnikState, creds Creds, unikernelId string, force bool) error {
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL, logger)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	unikInstances, err := ListUnikInstances(logger, unikState, creds)
	if err != nil {
		return lxerrors.New("could not check to see running unik instances", err)
	}
	for _, instance := range unikInstances {
		if instance.UnikernelId == unikernelId {
			if force == true {
				err = DeleteUnikInstance(logger, unikState, creds, instance.UnikInstanceID)
				if err != nil {
					return lxerrors.New("could not delete unik instance "+instance.UnikInstanceID, err)
				}
			} else {
				return lxerrors.New("attempted to delete unikernel "+unikernelId+", however instance "+instance.UnikInstanceID+" is still running. override with force=true", nil)
			}
		}
	}
	logger.WithFields(lxlog.Fields{
		"unikernel": unikernelId,
		"force": force,
	}).Infof("deleting unikernel")

	if _, ok := unikState.Unikernels[unikernelId]; ok {
		err = vsphereClient.Rmdir("unik/"+unikernelId)
		if err != nil {
			return lxerrors.New("failed to remove remote unikernel folder", err)
		}
		delete(unikState.Unikernels, unikernelId)

		err = unikState.Save(logger)
		if err != nil {
			return lxerrors.New("failed to save updated unikernel index", err)
		}

		return nil
	}
	return lxerrors.New("unikernel not found", err)
}