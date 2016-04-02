package main

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"flag"
	"github.com/layer-x/unik/pkg/daemon"
	"os"
"net"
	"time"
)

func main() {
	debugMode := flag.String("debug", "false", "enable verbose/debug mode")
	provider := flag.String("provider", "ec2", "cloud provider to use")
	vsphereUrl := flag.String("vsphere-url", "", "url endpoint for vsphere")
	vsphereUser := flag.String("vsphere-user", "", "user for vsphere")
	vspherePass := flag.String("vsphere-pass", "", "password for vsphere")
	flag.Parse()
	if *debugMode == "true" {
		lxlog.ActiveDebugMode()
	}

	buildCommand := exec.Command("make")
	buildCommand.Dir = "../../containers/"
	lxlog.LogCommand(buildCommand, true)
	err := buildCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "building containers")
		os.Exit(-1);
	}

	lxlog.Infof(logrus.Fields{}, "all images finished")

	opts := make(map[string]string)

	//make sure we don't attempt to multicast on a public cloud
	if *provider == "vsphere" {
		if *vsphereUrl == "" {
			lxlog.Errorf(logrus.Fields{}, "vsphere url must be set")
			os.Exit(-1);
		}
		if *vsphereUser == "" {
			lxlog.Errorf(logrus.Fields{}, "vsphere user must be set")
			os.Exit(-1);
		}
		if *vspherePass == "" {
			lxlog.Errorf(logrus.Fields{}, "vsphere pass must be set")
			os.Exit(-1);
		}
		opts["vsphereUrl"] = *vsphereUrl
		opts["vsphereUser"] = *vsphereUser
		opts["vspherePass"] = *vspherePass
	}

	unikDaemon := daemon.NewUnikDaemon(*provider, opts...)
	unikDaemon.Start(3000)
}
