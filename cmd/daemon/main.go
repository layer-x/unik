package main

import (
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"flag"
	"github.com/layer-x/unik/pkg/daemon"
	"os"
)

func main() {
	debugMode := flag.String("debug", "false", "enable verbose/debug mode")
	provider := flag.String("provider", "ec2", "cloud provider to use")
	vsphereUrl := flag.String("vsphere-url", "", "url endpoint for vsphere")
	vsphereUser := flag.String("vsphere-user", "", "user for vsphere")
	vspherePass := flag.String("vsphere-pass", "", "password for vsphere")
	flag.Parse()
	logger := lxlog.New()
	if *debugMode == "true" {
		logger.SetLogLevel(lxlog.DebugLevel)
	}

	buildCommand := exec.Command("make")
	buildCommand.Dir = "../../containers/"
	logger.LogCommand(buildCommand, true)
	err := buildCommand.Run()
	if err != nil {
		logger.WithErr(err).Errorf("building containers")
		os.Exit(-1);
	}

	logger.Infof("all images finished")

	opts := make(map[string]string)

	if *provider == "vsphere" {
		if *vsphereUrl == "" {
			logger.Errorf("vsphere url must be set")
			os.Exit(-1);
		}
		if *vsphereUser == "" {
			logger.Errorf("vsphere user must be set")
			os.Exit(-1);
		}
		if *vspherePass == "" {
			logger.Errorf("vsphere pass must be set")
			os.Exit(-1);
		}
		opts["vsphereUrl"] = *vsphereUrl
		opts["vsphereUser"] = *vsphereUser
		opts["vspherePass"] = *vspherePass
	}

	unikDaemon := daemon.NewUnikDaemon(*provider, opts)
	unikDaemon.Start(3000)
}
