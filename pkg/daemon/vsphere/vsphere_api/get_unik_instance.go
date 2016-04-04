package vsphere_api
import (
"github.com/layer-x/layerx-commons/lxerrors"
"strings"
"github.com/layer-x/unik/pkg/types"
"github.com/layer-x/unik/pkg/daemon/state"
"github.com/layer-x/layerx-commons/lxlog"
)

func GetUnikInstanceByPrefixOrName(logger *lxlog.LxLogger, unikState *state.UnikState, creds Creds, unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	unikInstances, err := ListUnikInstances(logger, unikState, creds)
	if err != nil {
		return nil, lxerrors.New("failed to retrieve known instances", err)
	}
	for _, unikInstance := range unikInstances {
		if strings.HasPrefix(unikInstance.VMID, unikInstanceIdPrefixOrName) || strings.HasPrefix(unikInstance.UnikInstanceID, unikInstanceIdPrefixOrName) || strings.HasPrefix(unikInstance.UnikInstanceName, unikInstanceIdPrefixOrName) {
			return unikInstance, nil
		}
	}
	return nil, lxerrors.New("unik instance with prefix "+ unikInstanceIdPrefixOrName +" not found", nil)
}
