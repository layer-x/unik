package main
import (
	"github.com/layer-x/unik/fakes"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"os/exec"
)

func main() {
	lxlog.ActiveDebugMode()
	buildCommand := exec.Command("docker", "build", "-t", "golang_unikernel_builder", ".")
	out, err := buildCommand.Output()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err}, "copying dockerfile to app directory")
		return
	}
	lxlog.Infof(logrus.Fields{"out":string(out)}, "built golang_unikernel_builder image")
	fakeDaemon := fakes.NewFakeUnikDaemon()
	fakeDaemon.Start(3000)
}
