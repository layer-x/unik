package vsphere_api
import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"github.com/docker/go/canonical/json"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/pborman/uuid"
)

func RunUnikInstance(creds Creds, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	instanceIds := []string{}
	unikernels, err := ListUnikernels(creds)
	if err != nil {
		return instanceIds, lxerrors.New("could not retrieve unikernel list", err)
	}
	var targetUnikernel *types.Unikernel
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			targetUnikernel = unikernel
			break
		}
	}
	if targetUnikernel == nil {
		return instanceIds, lxerrors.New("could not find a unikernel with name "+unikernelName, nil)
	}

	unikernelOvaPath := targetUnikernel.Path+"/"+DEFAULT_OVA_NAME

	lxlog.Debugf(logrus.Fields{"path": unikernelOvaPath}, "deploying unikernel OVA")
	govc := &Govc{
		url: creds.url.String(),
	}

	unikInstanceData := types.UnikInstanceData{
		Tags: tags,
		Env:  env,
	}
	for i := 0; i < instances; i ++ {
		instanceId := unikernelName + "_" + uuid.New()
		if instanceName == "" {
			instanceName = instanceId
		}
		lxlog.Debugf(logrus.Fields{"instance": instanceId}, "starting instance for unikernel "+unikernelName)

		unikInstanceMetadata := &types.UnikInstance{
			UnikInstanceID: instanceId,
			UnikInstanceName: instanceName,
			UnikernelName: unikernelName,
			Created: time.Now().Unix(),
			UnikInstanceData: unikInstanceData,
		}
		annotationBytes, err := json.Marshal(unikInstanceMetadata)
		if err != nil {
			return instanceIds, lxerrors.New("marshalling unikernel metadata", err)
		}
		err = govc.importOva(unikernelName, string(annotationBytes), unikernelOvaPath)
		if err != nil {
			return instanceIds, lxerrors.New("importing osv appliance to vsphere", err)
		}
		instanceIds = append(instanceIds, instanceId)
	}

	return instanceIds, nil
}
