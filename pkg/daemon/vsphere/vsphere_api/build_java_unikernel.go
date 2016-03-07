package vsphere_api
import (
	"mime/multipart"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"encoding/json"
	"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/unik/pkg/types"
"time"
"io/ioutil"
	"os"
	"path/filepath"
	"github.com/layer-x/layerx-commons/lxexec"
"strings"
"github.com/layer-x/layerx-commons/lxfileutils"
"io"
"os/exec"
)

const DEFAULT_OVA_NAME = "unikernel.ova"

func BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	unikernels, err := ListUnikernels()
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				lxlog.Warnf(logrus.Fields{"unikernelName": unikernelName, "ami": unikernel.Id},
					"deleting unikernel before building new unikernel")
				err = DeleteUnikernel(unikernel.Id, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this unikernel. try again with force=true", err)
			}
		}
	}

	unikernelPath, err := filepath.Abs("./test_outputs/" + "unikernels/" + unikernelName + "/")
	if err != nil {
		return lxerrors.New("getting absolute path for ./test_outputs/"+"unikernels/"+unikernelName+"/", err)
	}
	err = os.MkdirAll(unikernelPath, 0777)
	if err != nil {
		return lxerrors.New("making directory", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(unikernelPath)
		if err != nil {
			panic(lxerrors.New("cleaning up unikernel files", err))
		}
		lxlog.Infof(logrus.Fields{"files": unikernelPath}, "cleaned up files")
	}()
	lxlog.Infof(logrus.Fields{"path": unikernelPath, "unikernel_name": unikernelName}, "created output directory for unikernel")
	savedTar, err := os.OpenFile(unikernelPath+filepath.Base(handler.Filename), os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		return lxerrors.New("creating empty file for copying to", err)
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		return lxerrors.New("copying uploaded file to disk", err)
	}
	lxlog.Infof(logrus.Fields{"bytes": bytesWritten}, "file written to disk")
	err = lxfileutils.Untar(savedTar.Name(), unikernelPath)
	if err != nil {
		lxlog.Warnf(logrus.Fields{"saved tar name":savedTar.Name()}, "failed to untar using gzip, trying again without")
		err = lxfileutils.UntarNogzip(savedTar.Name(), unikernelPath)
		if err != nil {
			return lxerrors.New("untarring saved tar", err)
		}
	}
	lxlog.Infof(logrus.Fields{"path": unikernelPath, "unikernel_name": unikernelName}, "unikernel tarball untarred")
	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelPath+":/opt/code/",
		"-v", "/dev:/dev",
		"-e", "UNIKERNEL_APP_NAME="+unikernelName,
		"-e", "UNIKERNELFILE=/opt/code/rumprun-program_xen.bin.ec2dir",
		"golang_unikernel_builder")
	lxlog.LogCommand(buildUnikernelCommand, true)
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel image created")
	return nil


	unikernelFolder := VSPHERE_UNIKERNEL_FOLDER+"/"+unikernelName
	lxlog.Debugf(logrus.Fields{"path": unikernelFolder}, "saving unikernerl to folder")
	unikernelMetadata := &types.Unikernel{
		Id: unikernelName,
		UnikernelName: unikernelName,
		CreationDate: time.Now().String(),
		Created: time.Now().Unix(),
		Path: unikernelFolder,
	}
	annotationBytes, err := json.Marshal(unikernelMetadata)
	if err != nil {
		return lxerrors.New("marshalling unikernel metadata", err)
	}

	lxlog.Debugf(logrus.Fields{"metadata": string(annotationBytes)}, "saving uniknel metadata to folder")
	err = writeFile(unikernelFolder+"/unikernel-metadata.json", annotationBytes)
	if err != nil {
		return lxerrors.New("writing unikernel metadata", err)
	}

	//TODO: copy output file instead of osv base
	unikernelOvaPath := OSV_APPLIANCE_PATH

	destinationPath := unikernelFolder+"/"+DEFAULT_OVA_NAME

	if force {
		_, err = lxexec.RunCommand("cp", unikernelOvaPath, destinationPath)
		if err != nil {
			return lxerrors.New("copying output ova to destination "+destinationPath, err)
		}
	} else {
		_, err = lxexec.RunCommand("cp", "-r", unikernelOvaPath, destinationPath)
		if err != nil {
			return lxerrors.New("copying output ova to destination "+destinationPath, err)
		}
	}
	return nil
}

func writeFile(path, data []byte) error {
	err := ioutil.WriteFile(path, data, 0777)
	if err != nil {
		err := os.MkdirAll(filepath.Dir(path), 0777)
		if err != nil {
			return err
		}
		f, err := os.Create(path)
		if err != nil {
			return err
		}
		defer f.Close()
		_, err = f.Write(data)
		if err != nil {
			return err
		}
	}
	return nil
}
