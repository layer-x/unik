package vsphere_api
import (
"github.com/layer-x/layerx-commons/lxlog"
"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
"github.com/layer-x/unik/pkg/daemon/state"
)

func DeleteUnikInstance(logger *lxlog.LxLogger, unikState *state.UnikState, creds Creds, unikInstanceIdOrPrefix string) error {
	unikInstance, err := GetUnikInstanceByPrefixOrName(logger, unikState, creds, unikInstanceIdOrPrefix)
	if err != nil {
		return lxerrors.New("retrieving unik instance for prefix "+ unikInstanceIdOrPrefix, err)
	}
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL, logger)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	logger.WithFields(lxlog.Fields{
		"unikInstanceId": unikInstance.UnikInstanceID,
	}).Debugf("deleting unik instance")
	err = vsphereClient.DestroyVm(unikInstance.UnikInstanceID)
	if err != nil {
		return lxerrors.New("failed to delete unik instance", err)
	}
	return nil
}
