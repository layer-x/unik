package vsphere_api
import (
	"github.com/layer-x/unik/pkg/types"
	"github.com/layer-x/layerx-commons/lxerrors"
	"io/ioutil"
"encoding/json"
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
)

const VSPHERE_UNIKERNEL_FOLDER = "./vsphere_unikernel_folder"

func ListUnikernels() ([]*types.Unikernel, error) {
	lxlog.Debugf(logrus.Fields{"path": VSPHERE_UNIKERNEL_FOLDER}, "reading unikernel list from disk")
	unikernelDirs, err := ioutil.ReadDir(VSPHERE_UNIKERNEL_FOLDER)
	if err != nil {
		return nil, lxerrors.New("reading unikernel directory", err)
	}
	unikernels := []*types.Unikernel{}
	for _, dir := range unikernelDirs {
		unikernelFolder := VSPHERE_UNIKERNEL_FOLDER+"/"+dir.Name()
		metadata, err := readFile(unikernelFolder+"/unikernel-metadata.json")
		if err != nil {
			return nil, lxerrors.New("reading unikernel metadata file "+unikernelFolder+"/unikernel-metadata.json", err)
		}
		var unikernel *types.Unikernel
		err = json.Unmarshal(metadata, unikernel)
		if err != nil {
			return nil, lxerrors.New("marshalling data to unikernel json", err)
		}
		unikernels = append(unikernels, unikernel)
	}
	lxlog.Debugf(logrus.Fields{"count": len(unikernels)}, "read unikernels")
	return unikernels, nil
}

func readFile(path string) ([]byte, error) {
	data, err := ioutil.ReadFile(path)
	if err != nil {
		return "", err
	}
	return data, nil
}