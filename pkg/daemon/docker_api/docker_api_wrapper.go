package docker_api

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strings"

	"github.com/docker/docker/pkg/stdcopy"
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2api"
)

func AddDockerApi(logger lxlog.Logger, m *martini.ClassicMartini) *martini.ClassicMartini {
	m.Get("/v1.20/containers/json", func(res http.ResponseWriter, req *http.Request) {
		unikInstances, err := ec2api.ListUnikInstances(logger)
		if err != nil {
			logger.WithErr(err).Errorf("could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		dockerInstances := []*DockerUnikInstance{}
		for _, instance := range unikInstances {
			dockerInstance := convertUnikInstance(instance)
			dockerInstances = append(dockerInstances, dockerInstance)
		}
		logger.WithFields(lxlog.Fields{
			"dockerInstances": dockerInstances,
		}).Debugf("Listing all unik instances for docker")
		lxmartini.Respond(res, dockerInstances)
	})
	m.Get("/v1.20/images/json", func(res http.ResponseWriter, req *http.Request) {
		unikernels, err := ec2api.ListUnikernels(logger)
		if err != nil {
			logger.WithErr(err).Errorf("could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		dockerUnikernels := []*DockerUnikernel{}
		for _, unikernel := range unikernels {
			dockerInstance := convertUnikernel(unikernel)
			dockerUnikernels = append(dockerUnikernels, dockerInstance)
		}
		logger.WithFields(lxlog.Fields{
			"dockerUnikernels": dockerUnikernels,
		}).Debugf("Listing all unikernels for docker")
		lxmartini.Respond(res, dockerUnikernels)
	})
	m.Post("/v1.20/containers/create", func(res http.ResponseWriter, req *http.Request) {
		defer req.Body.Close()
		body, err := ioutil.ReadAll(req.Body)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"req": req,
			}).Errorf("could not read request body")
			lxmartini.Respond(res, lxerrors.New("could not read request body", err))
			return
		}
		var runRequest DockerRunRequest
		err = json.Unmarshal(body, &runRequest)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"body": string(body),
			}).Errorf("could not unmarshal body to docker run request json")
			lxmartini.Respond(res, lxerrors.New("could not unmarshal body to docker run request json", err))
			return
		}
		instanceName := runRequest.Hostname
		unikernelName := runRequest.Image
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"instancess": 1, 
				"unikernel_name": unikernelName,
			}).Errorf("invalid input for field 'instances'")
			lxmartini.Respond(res, err)
			return
		}
		instanceIds, err := ec2api.RunUnikInstance(logger, unikernelName, instanceName, 1, nil, nil)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"unikernel_name": unikernelName,
			}).Errorf("launching 1 instances of unikernel " + unikernelName + " for docker")
			lxmartini.Respond(res, err)
			return
		}
		logger.WithFields(lxlog.Fields{
			"instance_ids": instanceIds, "request": runRequest,
		}).Infof("1 instances started of unikernel " + unikernelName + " for docker")
		lxmartini.Respond(res, DockerRunResponse{Id: instanceName})
	})
	m.Get("/v1.20/containers/:instance_id/logs", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		logger.WithFields(lxlog.Fields{
			"req": req,
		}).Infof("received docker logs request")
		unikInstanceId := params["instance_id"]
		hijacker := res.(http.Hijacker)
		conn, _, err := hijacker.Hijack()
		if err != nil {
			logger.WithErr(err).Errorf("failed to hijack connection")
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

		logs, err := ec2api.GetLogs(logger, unikInstanceId)
		if err != nil {
			logger.WithErr(err).Errorf("failed to get logs")
			lxmartini.Respond(res, err)
			return
		}
		for _, logLine := range strings.Split(logs, "\n") {
			outStream.Write([]byte(logLine + "\n"))
		}
	})
	m.Get("/v1.20/containers/:instance_id/json", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		unikInstanceId := params["instance_id"]
		unikInstance, err := ec2api.GetUnikInstanceByPrefixOrName(logger, unikInstanceId)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"unikInstanceId": unikInstanceId,
			}).Errorf("could not get unik instance")
			lxmartini.Respond(res, err)
			return
		}
		lxmartini.Respond(res, convertUnikInstanceVerbose(unikInstance))
	})

	m.Get("/v1.22/containers/json", func(res http.ResponseWriter, req *http.Request) {
		unikInstances, err := ec2api.ListUnikInstances(logger)
		if err != nil {
			logger.WithErr(err).Errorf("could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		dockerInstances := []*DockerUnikInstanceVerbose{}
		unikernels, err := ec2api.ListUnikernels(logger)
		if err != nil {
			logger.WithErr(err).Errorf("could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}

		for _, instance := range unikInstances {
			dockerInstance := convertUnikInstanceVerbose(instance)
			for _, unikernel := range unikernels {
				if unikernel.Id == instance.UnikernelId {
					dockerInstance.Image = unikernel.UnikernelName
				}
			}
			dockerInstances = append(dockerInstances, dockerInstance)
		}
		logger.WithFields(lxlog.Fields{
			"dockerInstances": dockerInstances,
		}).Debugf("Listing all unik instances for docker")
		lxmartini.Respond(res, dockerInstances)
	})
	m.Get("/v1.22/images/json", func(res http.ResponseWriter, req *http.Request) {
		unikernels, err := ec2api.ListUnikernels(logger)
		if err != nil {
			logger.WithErr(err).Errorf("could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		dockerUnikernels := []*DockerUnikernel{}
		for _, unikernel := range unikernels {
			dockerInstance := convertUnikernel(unikernel)
			dockerUnikernels = append(dockerUnikernels, dockerInstance)
		}
		logger.WithFields(lxlog.Fields{
			"dockerUnikernels": dockerUnikernels,
		}).Debugf("Listing all unikernels for docker")
		lxmartini.Respond(res, dockerUnikernels)
	})
	m.Post("/v1.22/containers/create", func(res http.ResponseWriter, req *http.Request) {
		defer req.Body.Close()
		body, err := ioutil.ReadAll(req.Body)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"req": req,
			}).Errorf("could not read request body")
			lxmartini.Respond(res, lxerrors.New("could not read request body", err))
			return
		}
		var runRequest DockerRunRequest
		err = json.Unmarshal(body, &runRequest)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"body": string(body),
			}).Errorf("could not unmarshal body to docker run request json")
			lxmartini.Respond(res, lxerrors.New("could not unmarshal body to docker run request json", err))
			return
		}
		instanceName := runRequest.Hostname
		unikernelName := runRequest.Image
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"instancess": 1, 
				"unikernel_name": unikernelName,
			}).Errorf("invalid input for field 'instances'")
			lxmartini.Respond(res, err)
			return
		}
		instanceIds, err := ec2api.RunUnikInstance(logger, unikernelName, instanceName, 1, nil, nil)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"unikernel_name": unikernelName,
			}).Errorf("launching 1 instances of unikernel " + unikernelName + " for docker")
			lxmartini.Respond(res, err)
			return
		}
		logger.WithFields(lxlog.Fields{
			"instance_ids": instanceIds, 
			"request": runRequest,
		}).Infof("1 instances started of unikernel " + unikernelName + " for docker")
		lxmartini.Respond(res, DockerRunResponse{Id: instanceIds[0]})
	})
	m.Get("/v1.22/containers/:instance_id/logs", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		logger.WithFields(lxlog.Fields{
			"req": req,
		}).Infof("received docker logs request")
		unikInstanceId := params["instance_id"]
		hijacker := res.(http.Hijacker)
		conn, _, err := hijacker.Hijack()
		if err != nil {
			logger.WithErr(err).Errorf("failed to hijack connection")
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

		logs, err := ec2api.GetLogs(logger, unikInstanceId)
		if err != nil {
			logger.WithErr(err).Errorf("failed to get logs")
			lxmartini.Respond(res, err)
			return
		}
		for _, logLine := range strings.Split(logs, "\n") {
			outStream.Write([]byte(logLine + "\n"))
		}
	})
	m.Post("/v1.22/containers/:instance_id/start", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		logger.WithFields(lxlog.Fields{
			"req": req,
		}).Infof("received docker start container request")
		res.WriteHeader(http.StatusNoContent)
	})
	m.Get("/v1.22/containers/:instance_id/json", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		unikInstanceId := params["instance_id"]
		unikInstance, err := ec2api.GetUnikInstanceByPrefixOrName(logger, unikInstanceId)
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"unikInstanceId": unikInstanceId,
			}).Errorf("could not get unik instance")
			lxmartini.Respond(res, err)
			return
		}
		lxmartini.Respond(res, convertUnikInstanceVerbose(unikInstance))
	})
	return m
}
