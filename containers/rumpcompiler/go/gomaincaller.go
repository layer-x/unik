package main

import (
	"C"
	"os"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"net"
	"bytes"
	"errors"
	"io"
	"bufio"
	"fmt"
	"log"
	"strings"
)

//export gomaincaller
func gomaincaller() {
	var instanceData UnikInstanceData

	//make logs available via http request
	logs := bytes.Buffer{}
	err := teeStdout(&logs)
	if err != nil {
		log.Fatal(err)
	}
	err = teeStderr(&logs)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Beginning bootstrap...")
	resp, err := http.Get("http://169.254.169.254/latest/user-data")
	if err != nil { //if AWS user-data doesnt work, try multicast
		fmt.Printf("Not an EC2 instance? "+err.Error()+" listening for UDP Heartbaet...")
		//get MAC Addr (needed for vsphere)
		ifaces, err := net.Interfaces()
		if err != nil {
			log.Fatal("retrieving network interfaces" + err.Error())
		}
		macAddress := ""
		for _, iface := range ifaces {
			fmt.Printf("found an interface: %v\n", iface)
			if iface.Name != "lo" {
				macAddress = iface.HardwareAddr.String()
			}
		}
		if macAddress == "" {
			log.Fatal("could not find mac address")
		}

		var instanceData UnikInstanceData
		resp, err := http.Get("http://"+getUnikIp()+":3001/bootstrap?mac_address=" + macAddress)
		if err != nil {
			log.Fatal(err)
		}
		defer resp.Body.Close()
		data, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			log.Fatal(err)
		}
		err = json.Unmarshal(data, &instanceData)
		if err != nil {
			log.Fatal(err)
		}
		for key, value := range instanceData.Env {
			os.Setenv(key, value)
		}
	} else {
		defer resp.Body.Close()
		data, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			log.Fatal(err)
		}
		err = json.Unmarshal(data, &instanceData)
		if err != nil {
			log.Fatal(err)
		}
		for key, value := range instanceData.Env {
			os.Setenv(key, value)
		}
	}

	//handle logs request
	mux := http.NewServeMux()
	mux.HandleFunc("/logs", func(res http.ResponseWriter, req *http.Request) {
		fmt.Fprintf(res, "logs: %s", string(logs.Bytes()))
	})
	go http.ListenAndServe(":3000", mux)

	main()
}

func getUnikIp() string {
	fmt.Printf("begin listening for unik heartbeat...")
	socket, err := net.ListenUDP("udp4", &net.UDPAddr{
		IP:   net.IPv4(0, 0, 0, 0),
		Port: 9876,
	})
	if err != nil {
		log.Fatalf("error listening for udp4: "+err.Error())
	}
	for {
		data := make([]byte, 4096)
		_, remoteAddr, err := socket.ReadFromUDP(data)
		if err != nil {
			log.Fatalf("error reading from udp: "+err.Error())
		}
		fmt.Printf("recieved an ip: %s with data: %s", remoteAddr.IP.String(), string(data))
		if strings.Contains(string(data), "unik") {
			return remoteAddr.IP.String()
		}
	}
}

//make sure this remains the same as defined in
//github.com/layer-x/unik/pkg/daemon/ec2api/run_unik_instance.go
type UnikInstanceData struct {
	Tags map[string]string `json:"Tags"`
	Env  map[string]string `json:"Env"`
}

func teeStdout(writer io.Writer) error {
	r, w, err := os.Pipe()
	if err != nil {
		return errors.New("creating pipe: " + err.Error())
	}
	stdout := os.Stdout
	os.Stdout = w
	multi := io.MultiWriter(stdout, writer)
	reader := bufio.NewReader(r)
	go func() {
		for {
			_, err := io.Copy(multi, reader)
			if err != nil {
				log.Fatalf("copying pipe reader to multi writer: "+err.Error())
			}
		}
	}()
	return nil
}

func teeStderr(writer io.Writer) error {
	r, w, err := os.Pipe()
	if err != nil {
		return errors.New("creating pipe: " + err.Error())
	}
	stdout := os.Stderr
	os.Stderr = w
	multi := io.MultiWriter(stdout, writer)
	reader := bufio.NewReader(r)
	go func() {
		for {
			_, err := io.Copy(multi, reader)
			if err != nil {
				log.Fatalf("copying pipe reader to multi writer: "+err.Error())
			}
		}
	}()
	return nil
}
