package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"os/exec"
	"github.com/layer-x/unik/pkg/daemon/state"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func BuildGolangUnikernel(logger *lxlog.LxLogger, unikState *state.UnikState, unikernelName, unikernelId, unikernelCompilationDir, vmdkFolder string, vsphereClient *vsphere_utils.VsphereClient) error {
	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir, 
		"unikernel_name": unikernelName,
	}).Infof("building golang unikernel")

	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelCompilationDir + ":/opt/code",
		"rumpcompiler-go-hw",
	)
	logger.WithFields(lxlog.Fields{
		"cmd": buildUnikernelCommand.Args,
	}).Infof("running build kernel command")
	logger.LogCommand(buildUnikernelCommand, true)
	err := buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel kernel failed", err)
	}
	logger.WithFields(lxlog.Fields{
		"unikernel_name": unikernelName,
	}).Infof("unikernel .bin created")

	buildImageCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-v", unikernelCompilationDir + ":/unikernel",
		"rumpstager",
		"-mode",
		"vmware",
	)
	logger.WithFields(lxlog.Fields{
		"cmd": buildImageCommand.Args,
	}).Infof("runninig build image command")
	logger.LogCommand(buildImageCommand, true)
	err = buildImageCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel image failed", err)
	}
	logger.WithFields(lxlog.Fields{
		"unikernel_name": unikernelName,
	}).Infof("unikernel image created")

	vsphereClient.Mkdir("unik") //ignore errors since it may already exist
	err = vsphereClient.Mkdir(vmdkFolder)
	if err != nil {
		return lxerrors.New("could not create directory "+vmdkFolder, err)
	}
	err = vsphereClient.ImportVmdk(unikernelCompilationDir + "/root.vmdk", vmdkFolder)
	if err != nil {
		return lxerrors.New("could not import vmdk "+vmdkFolder, err)
	}

	unikState.Unikernels[unikernelId] = &types.Unikernel{
		Id: unikernelId, //same as unikernel name
		UnikernelName: unikernelName,
		CreationDate: time.Now().String(),
		Created: time.Now().Unix(),
		Path: vmdkFolder+"/root.vmdk",
	}

	err = unikState.Save(logger)
	if err != nil {
		return lxerrors.New("failed to save updated unikernel index", err)
	}

	logger.WithFields(lxlog.Fields{
		"unikernel": unikernelId,
	}).Infof("saved unikernel index")
	return nil
}

