package main

import (
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"net"
"io"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
)


func forward(remoteAddr string, conn net.Conn, errc chan error) {
	client, err := net.Dial("tcp", remoteAddr)
	if err != nil {
		errc <- lxerrors.New("Dial failed on remoteAddr: "+remoteAddr, err)
		return
	}
	lxlog.Infof(logrus.Fields{"remoteAddr": remoteAddr}, fmt.Sprintf("Connected to localhost %v\n", conn))
	go func() {
		defer client.Close()
		defer conn.Close()
		io.Copy(client, conn)
	}()
	go func() {
		defer client.Close()
		defer conn.Close()
		io.Copy(conn, client)
	}()
}

func listen(localAddr, remoteAddr string, errc chan error) {
	//Usage listen:port forward:port"

	listener, err := net.Listen("tcp", localAddr)
	if err != nil {
		errc <- lxerrors.New(fmt.Sprintf("Failed to setup listener on local addr "+localAddr), err)
		return
	}

	for {
		conn, err := listener.Accept()
		if err != nil {
			errc <- lxerrors.New(fmt.Sprintf("ERROR: failed to accept listener: %v", conn), err)
			return
		}
		lxlog.Infof(logrus.Fields{"localAddr": localAddr, "remoteAddr": remoteAddr}, fmt.Sprintf("Accepted connection %v\n", conn))
		go forward(remoteAddr, conn, errc)
	}
}