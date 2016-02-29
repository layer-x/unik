package main

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"os"
	"os/exec"
	"flag"
)

func main() {
	debugMode := flag.String("debug", "false", "enable verbose/debug mode")
	flag.Parse()
	if *debugMode == "true" {
		lxlog.ActiveDebugMode()
	}
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
	unikDaemon := NewUnikDaemon()
	unikDaemon.Start(3000)
}
