package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
)

func BuildGolangUnikernel(logger *lxlog.LxLogger, unikernelName, unikernelCompilationDir string) error {
	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir,
		"unikernel_name": unikernelName,
		"language_type": "golang",
	}).Infof("compiling go sources into unikernel binary")
	compileUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"-v", unikernelCompilationDir +":/opt/code",
		"rumpcompiler-go-xen")

	logger.LogCommand(compileUnikernelCommand, true)
	err := compileUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("compile unikernel failed", err)
	}

	stageUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-v", unikernelCompilationDir +":/unikernel",
		"rumpstager", "-mode", "aws", "-a", unikernelName)

	logger.LogCommand(stageUnikernelCommand, true)
	err = stageUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	logger.WithFields(lxlog.Fields{"unikernel_name": unikernelName}).Infof("unikernel image created")
	return nil
}
