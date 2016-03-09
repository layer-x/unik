package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
)

func DeleteUnikernel(creds Creds, unikernelId string, force bool) error {
	datastoreFolder := VSPHERE_UNIKERNEL_FOLDER + "/" + unikernelId
	unikInstances, err := ListUnikInstances(creds)
	if err != nil {
		return lxerrors.New("could not check to see running unik instances", err)
	}
	for _, instance := range unikInstances {
		if instance.UnikernelId == unikernelId {
			if force == true {
				err = DeleteUnikInstance(instance.UnikInstanceID)
				if err != nil {
					return lxerrors.New("could not delete unik instance "+instance.UnikInstanceID, err)
				}
			} else {
				return lxerrors.New("attempted to delete unikernel "+unikernelId+", however instance "+instance.UnikInstanceID+" is still running. override with force=true", nil)
			}
		}
	}
	lxlog.Infof(logrus.Fields{"unikernel": unikernelId, "force": force}, "deleting unikernel")

	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.url)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}
	err = vsphereClient.Rmdir(datastoreFolder)
	if err != nil {
		return lxerrors.New("removing unikernel folder", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel": unikernelId, "force": force}, "deleted unikernel")
	return nil
}