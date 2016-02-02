package ec2daemon
import (
"io"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"os"
"os/exec"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"path/filepath"
	"github.com/layer-x/layerx-commons/lxerrors"
	"strings"
	"mime/multipart"
)


func BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	unikernels, err := ListUnikernels()
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				lxlog.Warnf(logrus.Fields{"unikernelName": unikernelName, "ami": unikernel.AMI},
					"deleting unikernel before building new app")
				err = deleteUnikernel(unikernel.AMI, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this app. try again with force=true", err)
			}
		}
	}

	appPath, err := filepath.Abs("./test_outputs/"+"unikernels/"+unikernelName+"/")
	if err != nil {
		return lxerrors.New("getting absolute path for ./test_outputs/"+"unikernels/"+unikernelName+"/", err)
	}
	err = os.MkdirAll(appPath, 0777)
	if err != nil {
		return lxerrors.New("making directory", err)
	}
	//clean up artifacts even if we fail
	defer func(){
		err = os.RemoveAll(appPath)
		if err != nil {
			panic(lxerrors.New("cleaning up app files", err))
		}
		lxlog.Infof(logrus.Fields{"files": appPath}, "cleaned up files")
	}()
	lxlog.Infof(logrus.Fields{"path":appPath, "unikernel_name": unikernelName}, "created output directory for app")
	savedTar, err := os.OpenFile(appPath+filepath.Base(handler.Filename), os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		return lxerrors.New("creating empty file for copying to", err)
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		return lxerrors.New("copying uploaded file to disk", err)
	}
	lxlog.Infof(logrus.Fields{"bytes": bytesWritten}, "file written to disk")
	err = lxfileutils.Untar(savedTar.Name(), appPath)
	if err != nil {
		return lxerrors.New("untarring saved tar", err)
	}
	lxlog.Infof(logrus.Fields{"path": appPath, "unikernel_name": unikernelName}, "app tarball untarred")
	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", appPath+":/opt/code/",
		"-v", "/dev:/dev",
		"-e", "UNIKERNEL_APP_NAME="+unikernelName,
		"-e", "UNIKERNELFILE=/opt/code/rumprun-program_xen.bin.ec2dir",
		"golang_unikernel_builder")
	buildUnikernelCommand.Stdout = os.Stdout
	buildUnikernelCommand.Stderr = os.Stderr
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "app image created")
	return nil
}