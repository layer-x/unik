package main
import (
	"github.com/layer-x/unik/fakes"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"os/exec"
	"os"
)

func main() {
	lxlog.ActiveDebugMode()
	buildCommand := exec.Command("docker", "build", "-t", "golang_unikernel_builder", ".")
	buildCommand.Dir = "./golang_unikernel_builder"
	buildCommand.Stdout = os.Stdout
	buildCommand.Stderr = os.Stderr
	err := buildCommand.Run()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err":err}, "building golang unikernel builder")
		return
	}
//	lxlog.Infof(logrus.Fields{"out":string(out)}, "built golang_unikernel_builder image")
	fakeDaemon := fakes.NewFakeUnikDaemon()
	fakeDaemon.Start(3000)
}
