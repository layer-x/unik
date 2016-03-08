package vsphere_api
import (
	"github.com/layer-x/unik/pkg/types"
	"github.com/layer-x/layerx-commons/lxerrors"
	"io/ioutil"
"encoding/json"
"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

const VSPHERE_UNIKERNEL_FOLDER = "unik"

func ListUnikernels(creds Creds) ([]*types.Unikernel, error) {
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.url)
	if err != nil {
		return nil, lxerrors.New("initiating vsphere client connection", err)
	}
	lxlog.Debugf(logrus.Fields{"path": VSPHERE_UNIKERNEL_FOLDER}, "reading unikernel list from datastore")
	unikernelDirs, err := vsphereClient.Ls(VSPHERE_UNIKERNEL_FOLDER)
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