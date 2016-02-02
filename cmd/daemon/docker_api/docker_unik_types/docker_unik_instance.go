package docker_unik_types
import (
	"github.com/layer-x/unik/cmd/types"
	"time"
)

type DockerUnikInstance struct {
	ID         string `json:"Id"`
	Names      []string `json:"Names"`
	Image      string `json:"Image"`
	Command    string `json:"Command"`
	Created    time.Time `json:"Created"`
	Status     string `json:"Status"`
	Ports      []struct {
		Privateport int `json:"PrivatePort"`
		Publicport  int `json:"PublicPort"`
		Type        string `json:"Type"`
	} `json:"Ports"`
	Labels     struct {
				   ComExampleVendor  string `json:"com.example.vendor"`
				   ComExampleLicense string `json:"com.example.license"`
				   ComExampleVersion string `json:"com.example.version"`
			   } `json:"Labels"`
	Sizerw     int `json:"SizeRw"`
	Sizerootfs int `json:"SizeRootFs"`
}

func covertUnikInstance(unikInstance *types.UnikInstance) *DockerUnikInstance {
	return &DockerUnikInstance{
		ID: unikInstance.UnikInstanceID,
		Names: []string{unikInstance.UnikInstanceName},
		Image: unikInstance.UnikernelName,
		Command: "",
		Status: unikInstance.State,
		Created: unikInstance.Created,
	}
}