package docker_api

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strings"

	"github.com/Sirupsen/logrus"
	"github.com/docker/docker/pkg/stdcopy"
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/unik/cmd/daemon/ec2api"
)

func AddDockerApi(m *martini.ClassicMartini) *martini.ClassicMartini {
	m.Get("/v1.20/containers/json", func(res http.ResponseWriter, req *http.Request) {
		unikInstances, err := ec2api.ListUnikInstances()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		dockerInstances := []*DockerUnikInstance{}
		for _, instance := range unikInstances {
			dockerInstance := convertUnikInstance(instance)
			dockerInstances = append(dockerInstances, dockerInstance)
		}
		lxlog.Debugf(logrus.Fields{"dockerInstances": dockerInstances}, "Listing all unik instances for docker")
		lxmartini.Respond(res, dockerInstances)
	})
	m.Get("/v1.20/images/json", func(res http.ResponseWriter, req *http.Request) {
		unikernels, err := ec2api.ListUnikernels()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		dockerUnikernels := []*DockerUnikernel{}
		for _, unikernel := range unikernels {
			dockerInstance := convertUnikernel(unikernel)
			dockerUnikernels = append(dockerUnikernels, dockerInstance)
		}
		lxlog.Debugf(logrus.Fields{"dockerUnikernels": dockerUnikernels}, "Listing all unikernels for docker")
		lxmartini.Respond(res, dockerUnikernels)
	})
	m.Post("/v1.20/containers/create", func(res http.ResponseWriter, req *http.Request) {
		defer req.Body.Close()
		body, err := ioutil.ReadAll(req.Body)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"req": req, "err": err}, "could not read request body")
			lxmartini.Respond(res, lxerrors.New("could not read request body", err))
			return
		}
		var runRequest DockerRunRequest
		err = json.Unmarshal(body, &runRequest)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"body": string(body), "err": err}, "could not unmarshal body to docker run request json")
			lxmartini.Respond(res, lxerrors.New("could not unmarshal body to docker run request json", err))
			return
		}
		instanceName := runRequest.Hostname
		unikernelName := runRequest.Image
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err, "instancess": 1, "unikernel_name": unikernelName}, "invalid input for field 'instances'")
			lxmartini.Respond(res, err)
			return
		}
		instanceIds, err := ec2api.RunUnikInstance(unikernelName, instanceName, 1, nil, nil)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err, "unikernel_name": unikernelName}, "launching 1 instances of unikernel "+unikernelName+" for docker")
			lxmartini.Respond(res, err)
			return
		}
		lxlog.Infof(logrus.Fields{"instance_ids": instanceIds, "request": runRequest}, "1 instances started of unikernel "+unikernelName+" for docker")
		lxmartini.Respond(res, DockerRunResponse{Id: instanceIds[0]})
	})
	m.Get("/v1.20/containers/:instance_id/logs", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		lxlog.Infof(logrus.Fields{"req": req}, "received docker logs request")
		unikInstanceId := params["instance_id"]
		hijacker := res.(http.Hijacker)
		conn, _, err := hijacker.Hijack()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "failed to hijack connection")
			lxmartini.Respond(res, err)
			return
		}
		defer conn.Close()
		// Flush the options to make sure the client sets the raw mode
		conn.Write([]byte{})
		outStream := conn.(io.Writer)
		_, upgrade := req.Header["Upgrade"]

		if upgrade {
			fmt.Fprintf(outStream, "HTTP/1.1 101 UPGRADED\r\nContent-Type: application/vnd.docker.raw-stream\r\nConnection: Upgrade\r\nUpgrade: tcp\r\n\r\n")
		} else {
			fmt.Fprintf(outStream, "HTTP/1.1 200 OK\r\nContent-Type: application/vnd.docker.raw-stream\r\n\r\n")
		}

		outStream = stdcopy.NewStdWriter(outStream, stdcopy.Stdout)

		logs, err := ec2api.GetLogs(unikInstanceId)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "failed to get logs")
			lxmartini.Respond(res, err)
			return
		}
		for _, logLine := range strings.Split(logs, "\n") {
			outStream.Write([]byte(logLine + "\n"))
		}
	})
	m.Get("/v1.20/containers/:instance_id/json", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		unikInstanceId := params["instance_id"]
		unikInstance, err := ec2api.GetUnikInstanceByPrefixOrName(unikInstanceId)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err, "unikInstanceId": unikInstanceId}, "could not get unik instance")
			lxmartini.Respond(res, err)
			return
		}
		lxmartini.Respond(res, convertUnikInstanceVerbose(unikInstance))
	})

	//	m.Post("/v1.20/containers/:instance_id/attach", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
	//		unikInstanceId := params["instance_id"]
	//
	//		hijacker := res.(http.Hijacker)
	//		conn, _, err := hijacker.Hijack()
	//		if err != nil {
	//			panic(err)
	//		}
	//		defer conn.Close()
	//		// Flush the options to make sure the client sets the raw mode
	//		conn.Write([]byte{})
	//		//		inStream := conn.(io.ReadCloser)
	//		outStream := conn.(io.Writer)
	//		outStream.Write([]byte("getting logs for "+unikInstanceId+"...\n"))
	//
	//		//		if c.Upgrade {
	//		//			fmt.Fprintf(outStream, "HTTP/1.1 101 UPGRADED\r\nContent-Type: application/vnd.docker.raw-stream\r\nConnection: Upgrade\r\nUpgrade: tcp\r\n\r\n")
	//		//		} else {
	//		fmt.Fprintf(outStream, "HTTP/1.1 200 OK\r\nContent-Type: application/vnd.docker.raw-stream\r\n\r\n")
	//		//		}
	//
	//
	//		outStream.Write([]byte("getting logs for "+unikInstanceId+"...\n"))
	//		//		if f, ok := res.(http.Flusher); ok {
	//		//			f.Flush()
	//		//		} else {
	//		//			lxlog.Errorf(logrus.Fields{}, "no flush!")
	//		//			lxmartini.Respond(res, "no flush!")
	//		//			return
	//		//		}
	//		//		output := ioutils.NewWriteFlusher(outStream)
	//		//		defer output.Close()
	//
	//		err = ec2api.StreamLogs(unikInstanceId, outStream)
	//		if err != nil {
	//			lxlog.Warnf(logrus.Fields{"err":err, "unikInstanceId": unikInstanceId}, "streaming logs stopped")
	//			lxmartini.Respond(res, err)
	//			return
	//		}
	//	})
	return m
}
