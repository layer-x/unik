package vsphere_api
import (
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func DeleteUnikInstance(creds Creds, unikInstanceId string) error {
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	lxlog.Debugf(logrus.Fields{"unikInstanceId": unikInstanceId}, "deleting unik instance")
	err = vsphereClient.DestroyVm(unikInstanceId)
	if err != nil {
		return lxerrors.New("failed to delete unik instance", err)
	}
	return nil
}