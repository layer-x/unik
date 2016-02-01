package ec2daemon
import (
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxmartini"
	"fmt"
	"net/http"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
)

type app struct {
	name string
	filepath string
}

type UnikEc2Daemon struct {
	server *martini.ClassicMartini
	apps map[string]app
}

func NewUnikEc2Daemon() *UnikEc2Daemon {
	return &UnikEc2Daemon{
		server: lxmartini.QuietMartini(),
	}
}

func (d *UnikEc2Daemon) registerHandlers() {
	d.server.Post("/build", func(res http.ResponseWriter, req *http.Request) {
		err := req.ParseMultipartForm(0)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form)}, "could not parse multipart form")
			lxmartini.Respond(res, err)
			return
		}
		appName := req.FormValue("app_name")
		if appName == "" {
			lxlog.Errorf(logrus.Fields{"form": fmt.Sprintf("%v", req.Form)}, "app must be named")
			lxmartini.Respond(res, lxerrors.New("app must be named", nil))
			return
		}
		uploadedTar, handler, err := req.FormFile("tarfile")
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form), "app_name": appName}, "parsing file from multipart form")
			lxmartini.Respond(res, err)
			return
		}
		defer uploadedTar.Close()
		force := req.FormValue("force")
		err = buildUnikernel(appName, force, uploadedTar, handler)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form), "app_name": appName}, "building unikernel from app source")
			lxmartini.Respond(res, err)
			return
		}
		res.WriteHeader(http.StatusAccepted)
	})
	d.server.Get("/instances", func(res http.ResponseWriter) {
		instances, err := listUnikInstances()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		lxlog.Debugf(logrus.Fields{"instances": instances}, "Listing all unik instances")
		lxmartini.Respond(res, instances)
	})
	d.server.Get("/unikernels", func(res http.ResponseWriter) {
		unikernels, err := listUnikernels()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		lxlog.Debugf(logrus.Fields{"unikernels": unikernels}, "Listing all unikernels")
		lxmartini.Respond(res, unikernels)
	})
}


func (d *UnikEc2Daemon) Start(port int) {
	d.registerHandlers()
	d.server.RunOnAddr(fmt.Sprintf(":%v", port))
}
