package ec2api

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
)

func BuildGolangUnikernel(unikernelName, unikernelCompilationDir string) error {
	lxlog.Infof(logrus.Fields{"path": unikernelCompilationDir, "unikernel_name": unikernelName, "language_type": "golang"}, "compiling go sources into unikernel binary")
	compileUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"-v", unikernelCompilationDir +":/opt/code",
		"rumpcompiler-go-xen")

	lxlog.LogCommand(compileUnikernelCommand, true)
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

	lxlog.LogCommand(stageUnikernelCommand, true)
	err = stageUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel image created")
	return nil
}
