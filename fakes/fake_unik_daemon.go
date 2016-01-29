package fakes
import (
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxmartini"
	"fmt"
	"net/http"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
	"os"
	"io"
	"archive/tar"
	"path/filepath"
	"os/exec"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxfileutils"
)

type app struct {
	name string
	filepath string
}

type FakeUnikDaemon struct {
	server *martini.ClassicMartini
	apps map[string]app
}

func NewFakeUnikDaemon() *FakeUnikDaemon {
	return &FakeUnikDaemon{
		server: lxmartini.QuietMartini(),
		apps: make(map[string]app),
	}
}

func (d *FakeUnikDaemon) registerHandlers() {
	d.server.Post("/build", d.buildUnikernel)
}

func (d *FakeUnikDaemon) Start(port int) {
	d.registerHandlers()
	d.server.RunOnAddr(fmt.Sprintf(":%v", port))
}

func (d *FakeUnikDaemon) buildUnikernel(res http.ResponseWriter, req *http.Request) {
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
	if app, hasAlready := d.apps[appName]; hasAlready {
		lxlog.Errorf(logrus.Fields{"app": app}, "app already exists")
		lxmartini.Respond(res, lxerrors.New("app "+appName+" already exists", nil))
		return
	}

	uploadedTar, handler, err := req.FormFile("tarfile")
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "form": fmt.Sprintf("%v", req.Form), "app_name": appName}, "parsing file from multipart form")
		lxmartini.Respond(res, err)
		return
	}
	defer uploadedTar.Close()
	appPath := "./test_outputs/"+"apps/"+appName+"/"
	err = os.Mkdir(appPath, 0666)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName, "app_path": appPath}, "making directory")
		lxmartini.Respond(res, err)
		return
	}
	savedTar, err := os.OpenFile(appPath+handler.Filename, os.O_CREATE|os.O_RDWR, 0777)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName}, "creating empty file for copying to")
		lxmartini.Respond(res, err)
		return
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName}, "copying uploaded file to disk")
		lxmartini.Respond(res, err)
		return
	}
	lxlog.Infof(logrus.Fields{"bytes": bytesWritten}, "file written to disk")

	err = untar(savedTar, appPath)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName}, "untarring saved tar")
		lxmartini.Respond(res, err)
		return
	}
	lxlog.Infof(logrus.Fields{"path": appPath, "app_name": appName}, "app tarball untarred")
	err = lxfileutils.CopyFile("./Dockerfile", appPath+"Dockerfile")
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName}, "copying dockerfile to app directory")
		lxmartini.Respond(res, err)
		return
	}
	buildUnikernelCommand := exec.Command("docker", "run", "--rm", "-v", appPath+":/opt/code", "golang_unikernel_builder")
	out, err := buildUnikernelCommand.Output()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err}, "copying dockerfile to app directory")
		lxmartini.Respond(res, err)
		return
	}
	lxlog.Infof(logrus.Fields{"out":string(out)}, "result of docker run command")
	d.apps[appName] = app{
		name: appName,
		filepath: appPath+"rumprun-program.bin.ec2dir",
	}
	res.WriteHeader(http.StatusAccepted)
}

func untar(tarball *os.File, target string) error {
	tarReader := tar.NewReader(tarball)

	for {
		header, err := tarReader.Next()
		if err == io.EOF {
			break
		} else if err != nil {
			return err
		}

		path := filepath.Join(target, header.Name)
		info := header.FileInfo()
		if info.IsDir() {
			if err = os.MkdirAll(path, info.Mode()); err != nil {
				return err
			}
			continue
		}

		file, err := os.OpenFile(path, os.O_CREATE | os.O_TRUNC | os.O_WRONLY, info.Mode())
		if err != nil {
			return err
		}
		defer file.Close()
		_, err = io.Copy(file, tarReader)
		if err != nil {
			return err
		}
	}
	return nil
}