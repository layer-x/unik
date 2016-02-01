package ec2daemon
import (
"io"
"net/http"
	"github.com/layer-x/layerx-commons/lxmartini"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"os"
"os/exec"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"path/filepath"
	"github.com/layer-x/layerx-commons/lxerrors"
	"fmt"
)


func (d *UnikEc2Daemon) buildUnikernel(res http.ResponseWriter, req *http.Request) {
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
	appPath, err := filepath.Abs("./test_outputs/"+"apps/"+appName+"/")
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err}, "getting absolute path for ./test_outputs/"+"apps/"+appName+"/")
		lxmartini.Respond(res, err)
		return
	}
	err = os.MkdirAll(appPath, 0777)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName, "app_path": appPath}, "making directory")
		lxmartini.Respond(res, err)
		return
	}
	lxlog.Infof(logrus.Fields{"path":appPath, "app_name": appName}, "created output directory for app")
	savedTar, err := os.OpenFile(appPath+handler.Filename, os.O_CREATE|os.O_RDWR, 0666)
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
	err = lxfileutils.Untar(savedTar.Name(), appPath)
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err, "app_name": appName}, "untarring saved tar")
		lxmartini.Respond(res, err)
		return
	}
	lxlog.Infof(logrus.Fields{"path": appPath, "app_name": appName}, "app tarball untarred")
	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"-v", appPath+":/opt/code",
		"-E", "UNIK_IMAGE_NAME"+appName,
		"-E", "UNIK_IMAGE_ID"+appName,
		"golang_unikernel_builder")
	buildUnikernelCommand.Stdout = os.Stdout
	buildUnikernelCommand.Stderr = os.Stderr
	err = buildUnikernelCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err}, "building unikernel failed")
		lxmartini.Respond(res, err)
		return
	}
	d.apps[appName] = app{
		name: appName,
		filepath: appPath+"rumprun-program.bin.ec2dir",
	}
	res.WriteHeader(http.StatusAccepted)
}