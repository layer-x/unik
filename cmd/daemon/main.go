package main

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
	"flag"
"github.com/layer-x/unik/pkg/daemon"
	"os"
	"github.com/hashicorp/mdns"
)

func main() {
	debugMode := flag.String("debug", "false", "enable verbose/debug mode")
	provider := flag.String("provider", "ec2", "cloud provider to use")
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

	host, err := os.Hostname()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "retreiving hostname")
		os.Exit(-1);
	}

	//make sure we don't attempt to multicast on a public cloud
	if *provider != "ec2" {
		lxlog.Infof(logrus.Fields{"host": host},"Starting unik discovery service")
		info := []string{"Unik"}
		service, err := mdns.NewMDNSService(host, "_unik._tcp", "", "", 8000, nil, info)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "creating new mDNS service")
			os.Exit(-1);
		}
		server, err := mdns.NewServer(&mdns.Config{Zone: service})
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "starting mDNS server")
			os.Exit(-1);
		}
		defer server.Shutdown()
		lxlog.Infof(logrus.Fields{"server": server},"Started unik discovery service")
	}

	unikDaemon := daemon.NewUnikDaemon(*provider)
	unikDaemon.Start(3000)
}
