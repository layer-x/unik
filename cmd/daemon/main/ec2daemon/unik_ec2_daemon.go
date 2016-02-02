package ec2daemon
import (
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxmartini"
	"fmt"
	"net/http"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
	"strings"
	"strconv"
	"github.com/layer-x/unik/cmd/daemon/docker_api"
	"github.com/layer-x/unik/cmd/daemon/main/ec2api"
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
	d.server.Get("/instances", func(res http.ResponseWriter) {
		instances, err := ec2api.ListUnikInstances()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		lxlog.Debugf(logrus.Fields{"instances": instances}, "Listing all unik instances")
		lxmartini.Respond(res, instances)
	})
	d.server.Get("/unikernels", func(res http.ResponseWriter) {
		unikernels, err := ec2api.ListUnikernels()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		lxlog.Debugf(logrus.Fields{"unikernels": unikernels}, "Listing all unikernels")
		lxmartini.Respond(res, unikernels)
	})
	d.server.Post("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		err := req.ParseMultipartForm(0)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form)}, "could not parse multipart form")
			lxmartini.Respond(res, err)
			return
		}
		unikernelName := params["unikernel_name"]
		if unikernelName == "" {
			lxlog.Errorf(logrus.Fields{"request": fmt.Sprintf("%v", req)}, "unikernel must be named")
			lxmartini.Respond(res, lxerrors.New("unikernel must be named", nil))
			return
		}
		uploadedTar, handler, err := req.FormFile("tarfile")
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form), "unikernel_name": unikernelName}, "parsing file from multipart form")
			lxmartini.Respond(res, err)
			return
		}
		defer uploadedTar.Close()
		force := req.FormValue("force")
		err = ec2api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form), "unikernel_name": unikernelName}, "building unikernel from unikernel source")
			lxlog.Warnf(logrus.Fields{}, "cleaning up unikernel build artifacts (volumes, snapshots)")
			err = ec2api.DeleteSnapshotAndVolumeForApp(unikernelName)
			if err != nil {
				lxlog.Errorf(logrus.Fields{"err":err, "unikernel_name": unikernelName}, "could not remove volume and/or snapshot for instance")
			}
			lxmartini.Respond(res, err)
			return
		}
		res.WriteHeader(http.StatusAccepted)
	})
	d.server.Post("/unikernels/:unikernel_name/run", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		unikernelName := params["unikernel_name"]
		if unikernelName == "" {
			lxlog.Errorf(logrus.Fields{"request": fmt.Sprintf("%v", req)}, "unikernel must be named")
			lxmartini.Respond(res, lxerrors.New("unikernel must be named", nil))
			return
		}
		instancesStr := req.URL.Query().Get("instances")
		if instancesStr == "" {
			instancesStr = "1"
		}
		instanceName := req.URL.Query().Get("name")
		instances, err := strconv.Atoi(instancesStr)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "instancess": instancesStr, "unikernel_name": unikernelName}, "invalid input for field 'instances'")
			lxmartini.Respond(res, err)
			return
		}
		instanceIds, err := ec2api.RunApp(unikernelName, instanceName, int64(instances))
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "unikernel_name": unikernelName}, "launching "+instancesStr+" instances of unikernel "+unikernelName)
			lxmartini.Respond(res, err)
			return
		}
		lxlog.Infof(logrus.Fields{"instance_ids": instanceIds}, instancesStr+" instances started of unikernel "+unikernelName)
		res.WriteHeader(http.StatusAccepted)
	})
	d.server.Delete("/instances/:instance_id", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		instanceId := params["instance_id"]
		lxlog.Infof(logrus.Fields{"request": req},"deleting instance "+instanceId)
		err := ec2api.DeleteUnikInstance(instanceId)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err}, "could not delete instance "+instanceId)
			lxmartini.Respond(res, err)
			return
		}
		res.WriteHeader(http.StatusNoContent)
	})
	d.server.Delete("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		unikernelName := params["unikernel_name"]
		if unikernelName == "" {
			lxlog.Errorf(logrus.Fields{"request": fmt.Sprintf("%v", req)}, "unikernel must be named")
			lxmartini.Respond(res, lxerrors.New("unikernel must be named", nil))
			return
		}
		forceStr := req.URL.Query().Get("force")
		lxlog.Infof(logrus.Fields{"request": req},"deleting instance "+ unikernelName)
		force := false
		if strings.ToLower(forceStr) == "true" {
			force = true
		}
		err := ec2api.DeleteApp(unikernelName, force)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err}, "could not delete unikernel "+ unikernelName)
			lxmartini.Respond(res, err)
			return
		}
		res.WriteHeader(http.StatusNoContent)
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
