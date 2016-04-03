package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"os"
	"io/ioutil"
	"github.com/layer-x/unik/pkg/daemon/osv"
)

func BuildJavaUnikernel(logger *lxlog.LxLogger, unikernelName, unikernelCompilationDir string) error {
	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir,
		"unikernel_name": unikernelName,
		"language_type": "java",
	}).Infof("compiling java sources into unikernel binary")

	//create java-wrapper dir
	javaWrapperDir, err := ioutil.TempDir(os.TempDir(), unikernelName + "-java-wrapper-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory " + unikernelName + "-java-wrapper-dir", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(javaWrapperDir)
		if err != nil {
			panic(lxerrors.New("cleaning up java-wrapper files", err))
		}
		logger.WithFields(lxlog.Fields{
			"files": javaWrapperDir,
		}).Infof("cleaned up files")
	}()

	artifactId, groupId, version, err := osv.WrapJavaApplication(javaWrapperDir, unikernelCompilationDir)
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
		"-v", javaWrapperDir + "/jar-wrapper" + ":/jar-wrapper",
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

	stageUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-e", "UNIKERNELFILE=" + unikernelCompilationDir + "/program.raw",
		"-e", "UNIKERNEL_APP_NAME=" + unikernelName,
		"osvec2stager")

	logger.LogCommand(stageUnikernelCommand, true)
	err = stageUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("staging unikernel failed", err)
	}
	logger.WithFields(lxlog.Fields{
		"unikernel_name": unikernelName,
	}).Infof("unikernel staging complete")
	return nil
}
