package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
"github.com/layer-x/unik/pkg/daemon/state"
	"os"
	"path/filepath"
)

func DeleteUnikernel(unikState *state.UnikState, creds Creds, unikernelId string, force bool) error {
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

	if unikernel, ok := unikState.Unikernels[unikernelId]; ok {
		err = os.RemoveAll(filepath.Dir(unikernel.Path))
		if err != nil {
			return lxerrors.New("failed to remove local unikernel files", err)
		}
		delete(unikState.Unikernels, unikernelId)

		err = unikState.Save(state.DEFAULT_UNIK_STATE_FILE)
		if err != nil {
			return lxerrors.New("failed to save updated unikernel index", err)
		}

		return nil
	}
	return lxerrors.New("unikernel not found", err)
}