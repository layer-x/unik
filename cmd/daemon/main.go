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

	host, err := os.Hostname()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "retreiving hostname")
		os.Exit(-1);
	}

	opts := []string{}

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
		opts = append(opts, *vsphereUrl, *vsphereUser, *vspherePass)

		lxlog.Infof(logrus.Fields{"host": host}, "Starting unik discovery service")
		conn, err := net.ListenUDP("udp", &net.UDPAddr{IP: net.IPv4zero, Port: 0})
		if err != nil {
			lxlog.Fatalf(logrus.Fields{"err": err, "conn": conn}, "error establishing UDP socket connection")
		}
		go func() {
			for {
				_, err = conn.WriteToUDP([]byte("unik"), &net.UDPAddr{IP: net.IP{255, 255, 255, 255}, Port: 9876})
				if err != nil {
					lxlog.Fatalf(logrus.Fields{"err": err}, "failed to broadcast unik ip")
				}

//				BROADCAST_IPv4 := net.IPv4(255, 255, 255, 255)
//				socket, err := net.DialUDP("udp4", nil, &net.UDPAddr{
//					IP:   BROADCAST_IPv4,
//					Port: 9876,
//				})
//				if err != nil {
//					lxlog.Fatalf(logrus.Fields{"err": err, "socket": socket}, "error establishing UDP socket connection")
//				}
//				_, err = socket.WriteTo([]byte("unik"), socket.RemoteAddr())
//				if err != nil {
//					lxlog.Fatalf(logrus.Fields{"err": err, "socket": socket}, "failed to broadcast unik ip")
//				}
				time.Sleep(2000 * time.Millisecond)
			}
		}()

		//		info := []string{"Unik"}
		//		service, err := mdns.NewMDNSService(host, "_unik._tcp.local", "", "", 8000, nil, info)
		//		if err != nil {
		//			lxlog.Errorf(logrus.Fields{"err": err}, "creating new mDNS service")
		//			os.Exit(-1);
		//		}
		//		server, err := mdns.NewServer(&mdns.Config{Zone: service})
		//		if err != nil {
		//			lxlog.Errorf(logrus.Fields{"err": err}, "starting mDNS server")
		//			os.Exit(-1);
		//		}
		//		defer server.Shutdown()
		//		lxlog.Infof(logrus.Fields{"server": server},"Started unik discovery service")
	}

	unikDaemon := daemon.NewUnikDaemon(*provider, opts...)
	unikDaemon.Start(3000)
}
