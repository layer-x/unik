package vsphere_api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxexec"
)

type Govc struct {
	url string
}

func (govc *Govc) importOva(name, annotation, ovaPath string) (error) {
	lxlog.Debugf(logrus.Fields{"name":name, "annotation":annotation, "path":ovaPath}, "running import ova command")
	result, err := lxexec.RunCommand("govc",
		"import.ova",
		"-k",
		"-json=true",
		"-url", govc.url,
		"-name", name,
		"-annotation", annotation,
		ovaPath,
	)
	if err != nil {
		return lxerrors.New("executing command", err)
	}
	lxlog.Debugf(logrus.Fields{"result":result}, "running import ova command finished successfully")
	return nil
}

func (govc *Govc) powerOnVm(name string) (error) {
	lxlog.Debugf(logrus.Fields{"name":name}, "booting vm")
	result, err := lxexec.RunCommand("govc",
		"vm.power",
		"-k",
		"-json=true",
		"-url", govc.url,
		"-name", name,
		"-annotation", annotation,
		ovaPath,
	)
	if err != nil {
		return lxerrors.New("executing command", err)
	}
	lxlog.Debugf(logrus.Fields{"result":result}, "running import ova command finished successfully")
	return nil

}