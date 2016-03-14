package vsphere_api
import (
	"github.com/layer-x/unik/pkg/types"
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
	"github.com/layer-x/unik/pkg/daemon/state"
)

func ListUnikernels(unikState *state.UnikState) ([]*types.Unikernel, error) {
	unikernels := []*types.Unikernel{}
	for _, unikernel := range unikState.Unikernels {
		unikernels = append(unikernels, unikernel)
	}
	lxlog.Debugf(logrus.Fields{"count": len(unikernels)}, "read unikernels")
	return unikernels, nil
}
