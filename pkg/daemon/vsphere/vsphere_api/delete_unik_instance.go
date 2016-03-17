package vsphere_api
import (
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
"github.com/layer-x/unik/pkg/daemon/state"
)

func DeleteUnikInstance(unikState *state.UnikState, creds Creds, unikInstanceIdOrPrefix string) error {
	unikInstance, err := GetUnikInstanceByPrefixOrName(unikState, unikInstanceIdOrPrefix)
	if err != nil {
		return lxerrors.New("retrieving unik instance for prefix "+ unikInstanceIdOrPrefix, err)
	}
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	lxlog.Debugf(logrus.Fields{"unikInstanceId": unikInstance.UnikInstanceID}, "deleting unik instance")
	err = vsphereClient.DestroyVm(unikInstance.UnikInstanceID)
	if err != nil {
		return lxerrors.New("failed to delete unik instance", err)
	}
	return nil
}
