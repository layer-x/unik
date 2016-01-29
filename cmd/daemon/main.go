package main
import (
	"os/exec"
	"github.com/layer-x/layerx-commons/lxerrors"
)

const DOCKER = "docker"

func main() {

}

func initialize() error {
	var err error
	err = verifyDocker()
	if err != nil {
		return lxerrors.New("verifying docker", err)
	}

	return nil
}

func verifyDocker() error {
	err := exec.Command(DOCKER, "ps").Start()
	if err != nil {
		return lxerrors.New("could not execute "+DOCKER+" binary", err)
	}
	return nil
}