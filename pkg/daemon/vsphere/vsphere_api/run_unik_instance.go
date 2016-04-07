package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"github.com/docker/go/canonical/json"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/pborman/uuid"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/unik/pkg/daemon/state"
)

func RunUnikInstance(logger lxlog.Logger, unikState *state.UnikState, creds Creds, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	instanceIds := []string{}
	unikernels, err := ListUnikernels(logger, unikState)
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

	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL, logger)
	if err != nil {
		return instanceIds, lxerrors.New("initiating vsphere client connection", err)
	}

	logger.WithFields(lxlog.Fields{
		"path": targetUnikernel.Path,
	}).Debugf("deploying unikernel vmdk")

	unikInstanceData := types.UnikInstanceData{
		Tags: tags,
		Env:  env,
	}
	for i := int64(0); i < instances; i ++ {
		unikInstanceId := unikernelName + "_" + uuid.New()
		if instanceName == "" {
			instanceName = unikInstanceId
		}
		logger.WithFields(lxlog.Fields{
			"instance": unikInstanceId,
		}).Debugf("starting instance for unikernel "+unikernelName)

		unikInstanceMetadata := &types.UnikInstance{
			UnikInstanceID: unikInstanceId,
			UnikInstanceName: instanceName,
			UnikernelName: unikernelName,
			Created: time.Now(),
			UnikInstanceData: unikInstanceData,
		}
		annotationBytes, err := json.Marshal(unikInstanceMetadata)
		if err != nil {
			return instanceIds, lxerrors.New("marshalling unikernel metadata", err)
		}

		err = vsphereClient.CreateVm(instanceName, string(annotationBytes))
		if err != nil {
			return instanceIds, lxerrors.New("creating base vm", err)
		}

		err = vsphereClient.CopyVmdk(targetUnikernel.Path, unikInstanceId+"/program.vmdk")
		if err != nil {
			return instanceIds, lxerrors.New("copying base program.vmdk to instance datastore folder", err)
		}

		err = vsphereClient.AttachVmdk(instanceName, unikInstanceId+"/program.vmdk")
		if err != nil {
			return instanceIds, lxerrors.New("attaching copied vmdk to instance", err)
		}

		err = vsphereClient.PowerOnVm(instanceName)
		if err != nil {
			return instanceIds, lxerrors.New("powering on vm", err)
		}

		instanceIds = append(instanceIds, unikInstanceId)
	}

	return instanceIds, nil
}
