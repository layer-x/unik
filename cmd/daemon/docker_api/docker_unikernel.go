package docker_api
import (
	"github.com/layer-x/unik/cmd/types"
)

type DockerUnikernel struct {
	Repotags    []string `json:"RepoTags"`
	ID          string `json:"Id"`
	Size        int `json:"Size"`
	Virtualsize int `json:"VirtualSize"`
}

func convertUnikernel(unikernel *types.Unikernel) *DockerUnikernel {
	return &DockerUnikernel{
		Repotags: []string{unikernel.UnikernelName+":latest"},
		ID: unikernel.AMI,
		Size: 1000,
		Virtualsize: 1000,
	}
}