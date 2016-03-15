package vsphere_api
import (
	"mime/multipart"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"io"
	"os/exec"
	"github.com/layer-x/unik/pkg/daemon/state"
)

func BuildGoUnikernel(unikState *state.UnikState, creds Creds, unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	unikernelId := unikernelName //vsphere specific
	localVmdkFolder := state.DEFAULT_UNIK_STATE_FOLDER + unikernelId + "/"
	var err error
	defer func() {
		if err != nil {
			lxlog.Errorf(logrus.Fields{"error": err}, "error encountered, cleaning up unikernel artifacts")
			if !strings.Contains(err.Error(), "already exists") {
				os.RemoveAll(localVmdkFolder)
				delete(unikState.Unikernels, unikernelId)
			}
		}
	}()

	unikernels, err := ListUnikernels(unikState)
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				lxlog.Warnf(logrus.Fields{"unikernelName": unikernelName, "ami": unikernel.Id},
					"deleting unikernel before building new unikernel")
				err = DeleteUnikernel(unikState, creds, unikernel.Id, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this unikernel. try again with force=true", err)
			}
		}
	}

	unikernelDir, err := ioutil.TempDir(os.TempDir(), unikernelName+"-src-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory "+unikernelName+"-src-dir", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(unikernelDir)
		if err != nil {
			panic(lxerrors.New("cleaning up unikernel files", err))
		}
		lxlog.Infof(logrus.Fields{"files": unikernelDir}, "cleaned up files")
	}()
	lxlog.Infof(logrus.Fields{"path": unikernelDir, "unikernel_name": unikernelName}, "created output directory for unikernel")
	savedTar, err := os.OpenFile(unikernelDir+"/" + filepath.Base(handler.Filename), os.O_CREATE | os.O_RDWR, 0666)
	if err != nil {
		return lxerrors.New("creating empty file for copying to", err)
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		return lxerrors.New("copying uploaded file to disk", err)
	}
	lxlog.Infof(logrus.Fields{"bytes": bytesWritten}, "file written to disk")
	err = lxfileutils.Untar(savedTar.Name(), unikernelDir)
	if err != nil {
		lxlog.Warnf(logrus.Fields{"saved tar name":savedTar.Name()}, "failed to untar using gzip, trying again without")
		err = lxfileutils.UntarNogzip(savedTar.Name(), unikernelDir)
		if err != nil {
			return lxerrors.New("untarring saved tar", err)
		}
	}
	lxlog.Infof(logrus.Fields{"path": unikernelDir, "unikernel_name": unikernelName}, "unikernel tarball untarred")

	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelDir + ":/opt/code",
		"rumpcompiler-go-hw",
	)
	lxlog.Infof(logrus.Fields{"cmd": buildUnikernelCommand.Args}, "running build kernel command")
	lxlog.LogCommand(buildUnikernelCommand, true)
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel kernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel .bin created")

	buildImageCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", "/dev:/dev",
		"-v", unikernelDir + ":/unikernel",
		"rump-go-stager",
	)
	lxlog.Infof(logrus.Fields{"cmd": buildImageCommand.Args}, "runninig build image command")
	lxlog.LogCommand(buildImageCommand, true)
	err = buildImageCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel image failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel image created")

	err = os.MkdirAll(localVmdkFolder, 0777)
	if err != nil {
		return lxerrors.New("creating local vmdk folder", err)
	}

	saveVmdkCommand := exec.Command("cp", unikernelDir + "/root.vmdk", localVmdkFolder + "/program.vmdk")
	lxlog.LogCommand(saveVmdkCommand, true)
	err = saveVmdkCommand.Run()
	if err != nil {
		return lxerrors.New("copying vmdk from tmp dir to local storage failed", err)
	}

	unikState.Unikernels[unikernelId] = &types.Unikernel{
		Id: unikernelId, //same as unikernel name
		UnikernelName: unikernelName,
		CreationDate: time.Now().String(),
		Created: time.Now().Unix(),
		Path: localVmdkFolder + "/program.vmdk",
	}

	err = unikState.Save(state.DEFAULT_UNIK_STATE_FILE)
	if err != nil {
		return lxerrors.New("failed to save updated unikernel index", err)
	}

	lxlog.Infof(logrus.Fields{"unikernel": unikernelId}, "saved unikernel index")
	return nil
}

