package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"io/ioutil"
	"os"
	"os/exec"
	"github.com/layer-x/unik/pkg/daemon/osv"
	"github.com/layer-x/unik/pkg/daemon/state"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func BuildJavaUnikernel(logger *lxlog.LxLogger, unikState *state.UnikState, unikernelName, unikernelId, unikernelCompilationDir, vmdkFolder string, vsphereClient *vsphere_utils.VsphereClient) error {
	//create java-wrapper dir
	javaWrapperDir, err := ioutil.TempDir(os.TempDir(), unikernelName+"-java-wrapper-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory "+unikernelName+"-java-wrapper-dir", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(javaWrapperDir)
		if err != nil {
			logger.Panicf("cleaning up java-wrapper files: %s", err.Error())
		}
		logger.WithFields(lxlog.Fields{
			"files": javaWrapperDir,
		}).Infof("cleaned up files")
	}()

	artifactId, groupId, version, err := osv.WrapJavaApplication(logger, javaWrapperDir, unikernelCompilationDir)
	if err != nil {
		return lxerrors.New("generating java wrapper application " + unikernelCompilationDir, err)
	}
	logger.WithFields(lxlog.Fields{
		"artifactId": artifactId,
		"groupid": groupId,
		"version": version,
	}).Infof("generated java wrapper")

	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelCompilationDir + ":/unikernel",
		"-v", javaWrapperDir+"/jar-wrapper" + ":/jar-wrapper",
		"-e", "GROUP_ID=" + groupId,
		"-e", "ARTIFACT_ID=" + artifactId,
		"-e", "VERSION=" + version,
		"osvcompiler",
	)
	logger.WithFields(lxlog.Fields{
		"cmd": buildUnikernelCommand.Args,
	}).Infof("running build command")
	logger.LogCommand(buildUnikernelCommand, true)
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	logger.WithFields(lxlog.Fields{
		"unikernel_name": unikernelName,
	}).Infof("unikernel image created")

	vsphereClient.Mkdir("unik") //ignore errors since it may already exist
	err = vsphereClient.Mkdir(vmdkFolder)
	if err != nil {
		return lxerrors.New("could not create directory "+vmdkFolder, err)
	}
	err = vsphereClient.ImportVmdk(unikernelCompilationDir + "/root.vmdk", vmdkFolder+"/program.vmdk")
	if err != nil {
		return lxerrors.New("could not import vmdk "+vmdkFolder, err)
	}

	unikState.Unikernels[unikernelId] = &types.Unikernel{
		Id: unikernelId, //same as unikernel name
		UnikernelName: unikernelName,
		CreationDate: time.Now().String(),
		Created: time.Now().Unix(),
		Path: vmdkFolder+"/program.vmdk",
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

