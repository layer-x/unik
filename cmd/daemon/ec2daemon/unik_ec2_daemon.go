package ec2daemon

import (
	"encoding/json"
	"fmt"
	"github.com/Sirupsen/logrus"
	"github.com/docker/docker/pkg/ioutils"
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/unik/cmd/daemon/docker_api"
	"github.com/layer-x/unik/cmd/daemon/ec2api"
	"github.com/layer-x/unik/types"
	"github.com/pborman/uuid"
	"net/http"
	"strconv"
	"strings"
)

type UnikEc2Daemon struct {
	server *martini.ClassicMartini
}

func NewUnikEc2Daemon() *UnikEc2Daemon {
	return &UnikEc2Daemon{
		server: lxmartini.QuietMartini(),
	}
}

func (d *UnikEc2Daemon) registerHandlers() {
	streamOrRespond := func(res http.ResponseWriter, req *http.Request, action func() (interface{}, error)) {
		verbose := req.URL.Query().Get("verbose")
		if strings.ToLower(verbose) == "true" {
			httpOutStream := ioutils.NewWriteFlusher(res)
			uuid := uuid.New()
			lxlog.AddLogger(uuid, logrus.DebugLevel, httpOutStream)
			defer lxlog.DeleteLogger(uuid)

			jsonObject, err := action()
			if err != nil {
				lxmartini.Respond(res, err)
				lxlog.Errorf(logrus.Fields{"err": err}, "error performing action")
				return
			}
			if text, ok := jsonObject.(string); ok {
				_, err = httpOutStream.Write([]byte(text + "\n"))
				return
			}
			if jsonObject != nil {
				httpOutStream.Write([]byte("BEGIN_JSON_DATA\n"))
				data, err := json.Marshal(jsonObject)
				if err != nil {
					lxmartini.Respond(res, lxerrors.New("could not marshal message to json", err))
					return
				}
				data = append(data, byte('\n'))
				_, err = httpOutStream.Write(data)
				if err != nil {
					lxmartini.Respond(res, lxerrors.New("could not write data", err))
					return
				}
				return
			} else {
				res.WriteHeader(http.StatusNoContent)
			}
		}
		jsonObject, err := action()
		if err != nil {
			lxmartini.Respond(res, err)
			lxlog.Errorf(logrus.Fields{"err": err}, "error performing action")
			return
		}
		if jsonObject != nil {
			lxmartini.Respond(res, jsonObject)
		} else {
			res.WriteHeader(http.StatusNoContent)
		}
	}

	d.server.Get("/instances", func(res http.ResponseWriter, req *http.Request) {
		streamOrRespond(res, req, func() (interface{}, error) {
			unikInstances, err := ec2api.ListUnikInstances()
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			} else {
				lxlog.Debugf(logrus.Fields{"instances": unikInstances}, "Listing all unik instances")
			}
			return unikInstances, err
		})
	})
	d.server.Get("/unikernels", func(res http.ResponseWriter, req *http.Request) {
		streamOrRespond(res, req, func() (interface{}, error) {
			unikernels, err := ec2api.ListUnikernels()
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
			} else {
				lxlog.Debugf(logrus.Fields{"unikernels": unikernels}, "Listing all unikernels")
			}
			return unikernels, err
		})
	})
	d.server.Post("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, func() (interface{}, error) {
			lxlog.Debugf(logrus.Fields{"req": req}, "parsing multipart form")
			err := req.ParseMultipartForm(0)
			if err != nil {
				return nil, err
			}
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			lxlog.Debugf(logrus.Fields{"form": req.Form}, "parsing form file marked 'tarfile'")
			uploadedTar, handler, err := req.FormFile("tarfile")
			if err != nil {
				return nil, err
			}
			defer uploadedTar.Close()
			force := req.FormValue("force")
			lxlog.Debugf(logrus.Fields{"unikernelName": unikernelName, "force": force, "uploadedTar": uploadedTar}, "building unikernel")
			err = ec2api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err, "form": fmt.Sprintf("%v", req.Form), "unikernel_name": unikernelName}, "building unikernel from unikernel source")
				lxlog.Warnf(logrus.Fields{}, "cleaning up unikernel build artifacts (volumes, snapshots)")
				snapshotErr := ec2api.DeleteSnapshotAndVolumeForApp(unikernelName)
				if snapshotErr != nil {
					lxlog.Errorf(logrus.Fields{"err": snapshotErr, "unikernel_name": unikernelName}, "could not remove volume and/or snapshot for instance")
				}
				return nil, err
			}
			lxlog.Debugf(logrus.Fields{}, "verifying unikernel succeeded")
			unikernels, err := ec2api.ListUnikernels()
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
				return nil, err
			}
			for _, unikernel := range unikernels {
				if unikernel.UnikernelName == unikernelName {
					return unikernel, nil
				}
			}
				return "unikernel created", nil
		})
	})
	d.server.Post("/unikernels/:unikernel_name/run", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, func() (interface{}, error) {
			lxlog.Debugf(logrus.Fields{"request": req, "query": req.URL.Query()}, "recieved run request")
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				lxlog.Errorf(logrus.Fields{"request": fmt.Sprintf("%v", req)}, "unikernel must be named")
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			instancesStr := req.URL.Query().Get("instances")
			if instancesStr == "" {
				instancesStr = "1"
			}
			instanceName := req.URL.Query().Get("name")
			instances, err := strconv.Atoi(instancesStr)
			if err != nil {
				return nil, err
			}
			fullTagString := req.URL.Query().Get("tags")
			tagPairs := strings.Split(fullTagString, ",")
			tags := make(map[string]string)

			envDelimiter := req.URL.Query().Get("useDelimiter")
			if envDelimiter == "" {
				envDelimiter = ","
			}
			envPairDelimiter := req.URL.Query().Get("usePairDelimiter")
			if envPairDelimiter == "" {
				envPairDelimiter = "="
			}

			for _, tagPair := range tagPairs {
				splitTag := strings.Split(tagPair, "=")
				if len(splitTag) != 2 {
					lxlog.Warnf(logrus.Fields{"tagPair": tagPair}, "was given a tag string with an invalid format, ignoring")
					continue
				}
				tags[splitTag[0]] = splitTag[1]
			}

			fullEnvString := req.URL.Query().Get("env")
			envPairs := strings.Split(fullEnvString, envDelimiter)
			env := make(map[string]string)

			for _, envPair := range envPairs {
				splitEnv := strings.Split(envPair, envPairDelimiter)
				if len(splitEnv) != 2 {
					lxlog.Warnf(logrus.Fields{"envPair": envPair}, "was given a env string with an invalid format, ignoring")
					continue
				}
				env[splitEnv[0]] = splitEnv[1]
			}

			instanceIds, err := ec2api.RunUnikInstance(unikernelName, instanceName, int64(instances), tags, env)
			if err != nil {
				return nil, err
			}
			lxlog.Debugf(logrus.Fields{}, "verifying instances started")
			successfulInstances := []*types.UnikInstance{}
			unikInstances, err := ec2api.ListUnikInstances()
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			}
			for _, unikInstance := range unikInstances {
			CheckIds:
				for _, instanceId := range instanceIds {
					if unikInstance.UnikInstanceID == instanceId {
						successfulInstances = append(successfulInstances, unikInstance)
						break CheckIds
					}
				}
			}
			lxlog.Infof(logrus.Fields{"instance_ids": instanceIds}, instancesStr+" instances started of unikernel "+unikernelName)
			return successfulInstances, nil
		})
	})
	d.server.Delete("/instances/:instance_id", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, func() (interface{}, error) {
			instanceId := params["instance_id"]
			lxlog.Infof(logrus.Fields{"request": req}, "deleting instance "+instanceId)
			err := ec2api.DeleteUnikInstance(instanceId)
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not delete instance "+instanceId)
				return nil, err
			}
			return nil, err
		})
	})
	d.server.Delete("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, func() (interface{}, error) {
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				lxlog.Errorf(logrus.Fields{"request": fmt.Sprintf("%v", req)}, "unikernel must be named")
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			forceStr := req.URL.Query().Get("force")
			lxlog.Infof(logrus.Fields{"request": req}, "deleting instance "+unikernelName)
			force := false
			if strings.ToLower(forceStr) == "true" {
				force = true
			}
			err := ec2api.DeleteUnikernelByName(unikernelName, force)
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err}, "could not delete unikernel "+unikernelName)
				return nil, err
			}
			return nil, nil
		})
	})
	d.server.Get("/instances/:instance_id/logs", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, func() (interface{}, error) {
			unikInstanceId := params["instance_id"]
			follow := req.URL.Query().Get("follow")
			res.Write([]byte("getting logs for " + unikInstanceId + "...\n"))
			if f, ok := res.(http.Flusher); ok {
				f.Flush()
			} else {
				lxlog.Errorf(logrus.Fields{}, "no flush!")
				return nil, lxerrors.New("not a flusher", nil)
			}
			if strings.ToLower(follow) == "true" {
				deleteOnDisconnectStr := req.URL.Query().Get("delete")
				deleteOnDisconnect := false
				if strings.ToLower(deleteOnDisconnectStr) == "true" {
					deleteOnDisconnect = true
				}

				output := ioutils.NewWriteFlusher(res)
				err := ec2api.StreamLogs(unikInstanceId, output, deleteOnDisconnect)
				if err != nil {
					lxlog.Warnf(logrus.Fields{"err": err, "unikInstanceId": unikInstanceId}, "streaming logs stopped")
					return nil, err
				}
			}
			logs, err := ec2api.GetLogs(unikInstanceId)
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err": err, "unikInstanceId": unikInstanceId}, "failed to perform get logs request")
				return nil, err
			}
			return logs, nil
		})
	})
}

func (d *UnikEc2Daemon) addDockerHandlers() {
	d.server = docker_api.AddDockerApi(d.server)
}

func (d *UnikEc2Daemon) Start(port int) {
	d.registerHandlers()
	d.addDockerHandlers()
	d.server.RunOnAddr(fmt.Sprintf(":%v", port))
}
