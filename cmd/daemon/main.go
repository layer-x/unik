package main

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"flag"
"github.com/layer-x/unik/pkg/daemon"
)

func main() {
	debugMode := flag.String("debug", "false", "enable verbose/debug mode")
	provider := flag.String("provider", "ec2", "cloud provider to use")
	flag.Parse()
	if *debugMode == "true" {
		lxlog.ActiveDebugMode()
	}
	buildCommand := exec.Command("docker", "build", "-t", "rumpstager", ".")
	buildCommand.Dir = "../../rumpstager"
	lxlog.LogCommand(buildCommand, true)
	err := buildCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "building rumpstager")
		return
	}
	buildCommand = exec.Command("docker", "build", "-t", "rumpstager", ".")
	buildCommand.Dir = "../../rumpcompiler"
	lxlog.LogCommand(buildCommand, true)
	err = buildCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "building rumpstager")
		return
	}
	lxlog.Infof(logrus.Fields{}, "built rumpstager image")
	unikDaemon := daemon.NewUnikDaemon(*provider)
	unikDaemon.Start(3000)
}
