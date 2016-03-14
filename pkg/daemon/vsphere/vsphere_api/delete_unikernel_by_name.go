package vsphere_api
import (
"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/unik/pkg/daemon/state"
)

func DeleteUnikernelByName(unikState *state.UnikState, creds Creds, unikernelName string, force bool) error {
	unikernels, err := ListUnikernels(unikState)
	if err != nil {
		return lxerrors.New("could not get unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			err = DeleteUnikernel(unikState, creds, unikernel.Id, force)
			if err != nil {
				return lxerrors.New("could not delete unikernel "+unikernel.Id, err)
			}
			return nil
		}
	}
	return lxerrors.New("could not find unikernel "+unikernelName, nil)
}
