package main

import (
	"C"
	"os"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"github.com/hashicorp/mdns"
	"net"
	"bytes"
	"github.com/layer-x/layerx-commons/lxstream"
	"github.com/layer-x/unik/vendor/github.com/go-martini/martini"
)

//export gomaincaller
func gomaincaller() {
	var instanceData UnikInstanceData

	resp, err := http.Get("http://169.254.169.254/latest/user-data")
	if err != nil { //if AWS user-data doesnt work, try multicast
		//get MAC Addr (needed for vsphere)
		ifaces, err := net.Interfaces()
		if err != nil {
			panic("retrieving network interfaces" + err.Error())
		}
		macAddress := ""
		for _, iface := range ifaces {
			if iface.Name != "lo" {
				macAddress = iface.HardwareAddr.String()
			}
		}
		if macAddress == "" {
			panic("could not find mac address")
		}

		// Make a channel for results and start listening
		ipChan := make(chan string)
		entriesCh := make(chan *mdns.ServiceEntry, 4)
		go func() {
			for entry := range entriesCh {
				ipChan <- entry.AddrV4.String()
			}
		}()
		// Start the lookup
		err = mdns.Lookup("_unik._tcp", entriesCh)
		if err == nil {
			var instanceData UnikInstanceData
			resp, err := http.Get("http://"+<- ipChan+":3001/bootstrap?mac_address="+macAddress)
			if err != nil {
				panic(err)
			}
			defer resp.Body.Close()
			data, err := ioutil.ReadAll(resp.Body)
			if err != nil {
				panic(err)
			}
			err = json.Unmarshal(data, &instanceData)
			if err != nil {
				panic(err)
			}
			for key, value := range instanceData.Env {
				os.Setenv(key, value)
			}
		} else {
			panic("expected mdns to work, but failed:" + err.Error())
		}
	} else {
		defer resp.Body.Close()
		data, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			panic(err)
		}
		err = json.Unmarshal(data, &instanceData)
		if err != nil {
			panic(err)
		}
		for key, value := range instanceData.Env {
			os.Setenv(key, value)
		}
	}

	//make logs available via http request
	logs := bytes.Buffer{}
	err = lxstream.Tee(os.Stdout, &logs)
	if err != nil {
		panic(err)
	}
	err = lxstream.Tee(os.Stderr, &logs)
	if err != nil {
		panic(err)
	}

	//handle logs request
	m := martini.Classic()
	m.Get("/logs", func() string {
		return logs.String()
	})
	go m.RunOnAddr(":3000")

	main()
}

//make sure this remains the same as defined in
//github.com/layer-x/unik/pkg/daemon/ec2api/run_unik_instance.go
type UnikInstanceData struct {
	Tags map[string]string `json:"Tags"`
	Env  map[string]string `json:"Env"`
}