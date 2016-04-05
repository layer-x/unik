package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"github.com/layer-x/unik/pkg/types"
	"path/filepath"
	"fmt"
)

func BuildGolangUnikernel(logger *lxlog.LxLogger, unikernelName, unikernelCompilationDir string, desiredVolumes []*types.VolumeSpec) error {
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

	volumeArgs := []string{}
	if len(desiredVolumes) > 0 {
		volumeArgs = append(volumeArgs, "-v")
		for _, spec := range desiredVolumes {
			if spec.Size != 0 {
				volumeArgs = append(volumeArgs, filepath.Base(spec.DataFolder)+":"+spec.MountPoint+","+fmt.Sprintf("%v", spec.Size))
			} else {
				volumeArgs = append(volumeArgs, filepath.Base(spec.DataFolder)+":"+spec.MountPoint)
			}
		}
	}

	stageUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-v", unikernelCompilationDir +":/unikernel",
		"rumpstager", "-mode", "aws", "-a", unikernelName, volumeArgs...)

	logger.LogCommand(stageUnikernelCommand, true)
	err = stageUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	logger.WithFields(lxlog.Fields{"unikernel_name": unikernelName}).Infof("unikernel image created")
	return nil
}
