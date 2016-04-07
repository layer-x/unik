package vsphere_api
import (
	"github.com/layer-x/unik/pkg/types"
"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/state"
)

func ListUnikernels(logger lxlog.Logger, unikState *state.UnikState) ([]*types.Unikernel, error) {
	unikernels := []*types.Unikernel{}
	for _, unikernel := range unikState.Unikernels {
		unikernels = append(unikernels, unikernel)
	}
	logger.WithFields(lxlog.Fields{
		"count": len(unikernels),
	}).Debugf("read unikernels")
	return unikernels, nil
}
