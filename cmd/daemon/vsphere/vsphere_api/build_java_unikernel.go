package vsphere_api
import (
	"mime/multipart"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
	"encoding/json"
	"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/unik/types"
"time"
"io/ioutil"
	"os"
	"path/filepath"
	"github.com/layer-x/layerx-commons/lxexec"
)

const DEFAULT_OVA_NAME = "unikernel.ova"

const OSV_APPLIANCE_PATH = "./osv_appliance/osv-v0.24.esx.ova"

func BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
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
