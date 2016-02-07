package main

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/cmd/daemon/ec2daemon"
	"os"
	"os/exec"
)

func main() {
	lxlog.ActiveDebugMode()
	buildCommand := exec.Command("docker", "build", "-t", "golang_unikernel_builder", ".")
	buildCommand.Dir = "./golang_unikernel_builder"
	buildCommand.Stdout = os.Stdout
	buildCommand.Stderr = os.Stderr
	err := buildCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "building golang unikernel builder")
		return
	}
	lxlog.Infof(logrus.Fields{}, "built golang_unikernel_builder image")
	unikDaemon := ec2daemon.NewUnikEc2Daemon()
	unikDaemon.Start(3000)
}
