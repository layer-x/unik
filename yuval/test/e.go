package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net"
	"net/http"
)

func main() { httpd() }

func httpd() {
	ifaces, _ := net.Interfaces()
	// handle err
	for _, i := range ifaces {
		addrs, _ := i.Addrs()
		// handle err
		for _, addr := range addrs {
			var ip net.IP
			switch v := addr.(type) {
			case *net.IPNet:
				ip = v.IP
			case *net.IPAddr:
				ip = v.IP
			}
			// process IP address
			fmt.Println("IPs:", ip)
		}
	}

	files, _ := ioutil.ReadDir("/dev")
	for _, f := range files {
		fmt.Printf("%s ", f.Name())
	}

	if intrfs, err := net.Interfaces(); err == nil {

		for _, intr := range intrfs {
			fmt.Println("interface:", intr.Name)
		}

	} else {
		fmt.Println("no interfaces", err)

	}

	fmt.Println("Starting to listen!!!")
	log.Fatal(http.ListenAndServe(":8080", http.FileServer(http.Dir("/"))))
}
