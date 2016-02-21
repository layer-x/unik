package docker_api

import (
	"github.com/layer-x/unik/types"
)

type DockerUnikInstance struct {
	ID         string   `json:"Id"`
	Names      []string `json:"Names"`
	Image      string   `json:"Image"`
	Command    string   `json:"Command"`
	Created    int64    `json:"Created"`
	Status     string   `json:"Status"`
	Sizerw     int      `json:"SizeRw"`
	Sizerootfs int      `json:"SizeRootFs"`
}

type DockerUnikInstancev21 struct {
	ID string `json:"Id"`
	Names []string `json:"Names"`
	Image string `json:"Image"`
	ImageID string `json:"ImageID"`
	Command string `json:"Command"`
	Created int64 `json:"Created"`
	Status string `json:"Status"`
	Ports []interface{} `json:"Ports"`
	Labels struct {
	   } `json:"Labels"`
	SizeRw int `json:"SizeRw"`
	SizeRootFs int `json:"SizeRootFs"`
}

func convertUnikInstance(unikInstance *types.UnikInstance) *DockerUnikInstance {
	return &DockerUnikInstance{
		ID:         unikInstance.UnikInstanceID,
		Names:      []string{unikInstance.UnikInstanceName},
		Image:      unikInstance.UnikernelName,
		Command:    "N/A",
		Created:    unikInstance.Created.Unix(),
		Status:     unikInstance.State,
		Sizerw:     1000,
		Sizerootfs: 1000,
	}
}

func convertUnikInstancev21(unikInstance *types.UnikInstance) *DockerUnikInstancev21 {
	return &DockerUnikInstancev21{
		ID:         unikInstance.UnikInstanceID,
		Names:      []string{unikInstance.UnikInstanceName},
		Image:      unikInstance.UnikernelName,
		Command:    "N/A",
		Created:    unikInstance.Created.Unix(),
		Status:     unikInstance.State,
		SizeRw:     1000000000,
		SizeRootFs: 1000000000,
	}
}

//func convertUnikInstanceInspect(unikInstance *types.UnikInstance) *dockertypes.ContainerJSON {
//	size := 100000
//	var running bool
//	var removalInProgress bool
//	if unikInstance.State == "running" {
//		running = true
//	}
//	if unikInstance.State == "shutting-down" {
//		removalInProgress = true
//	}
//	return &dockertypes.ContainerJSON{
//		ContainerJSONBase: dockertypes.ContainerJSONBase{
//			ID: unikInstance.UnikInstanceID,
//			Name: unikInstance.UnikInstanceName,
//			Image: unikInstance.UnikernelName,
//			Created: unikInstance.Created.Unix(),
//			Args: []string{"N/A"},
//			State: &container.State{
//				Running: running,
//				RemovalInProgress: removalInProgress,
//			},
//			SizeRw: &size,
//			SizeRootFs: &size,
//		},
//	}
//}

type DockerUnikInstanceVerbose struct {
	ID string `json:"Id"`
	Names []string `json:"Names"`
	Image string `json:"Image"`
	ImageID string `json:"ImageID"`
	Command string `json:"Command"`
	Created int64 `json:"Created"`
	Status string `json:"Status"`
	Ports []struct {
		PrivatePort int `json:"PrivatePort"`
		PublicPort int `json:"PublicPort"`
		Type string `json:"Type"`
	} `json:"Ports"`
	Labels struct {
		   ComExampleVendor string `json:"com.example.vendor"`
		   ComExampleLicense string `json:"com.example.license"`
		   ComExampleVersion string `json:"com.example.version"`
	   } `json:"Labels"`
	SizeRw int `json:"SizeRw"`
	SizeRootFs int `json:"SizeRootFs"`
	NetworkSettings struct {
		   Networks struct {
						Bridge struct {
								   NetworkID string `json:"NetworkID"`
								   EndpointID string `json:"EndpointID"`
								   Gateway string `json:"Gateway"`
								   IPAddress string `json:"IPAddress"`
								   IPPrefixLen int `json:"IPPrefixLen"`
								   IPv6Gateway string `json:"IPv6Gateway"`
								   GlobalIPv6Address string `json:"GlobalIPv6Address"`
								   GlobalIPv6PrefixLen int `json:"GlobalIPv6PrefixLen"`
								   MacAddress string `json:"MacAddress"`
							   } `json:"bridge"`
					} `json:"Networks"`
	   } `json:"NetworkSettings"`
}

func convertUnikInstanceVerbose(unikInstance *types.UnikInstance) *DockerUnikInstanceVerbose {
	return &DockerUnikInstanceVerbose{
		ID: unikInstance.UnikInstanceID,
		Command: "go run main.go",
		Names: []string{unikInstance.UnikInstanceName},
		Status: unikInstance.State,
		Image:   "iditdemo",
		ImageID:   unikInstance.UnikernelId,
		Created: unikInstance.Created.Unix(),
	}
}

type hostConfig struct {
	Binds           interface{}   `json:"Binds"`
	Blkioweight     int           `json:"BlkioWeight"`
	Capadd          interface{}   `json:"CapAdd"`
	Capdrop         interface{}   `json:"CapDrop"`
	Containeridfile string        `json:"ContainerIDFile"`
	Cpusetcpus      string        `json:"CpusetCpus"`
	Cpusetmems      string        `json:"CpusetMems"`
	Cpushares       int           `json:"CpuShares"`
	Cpuperiod       int           `json:"CpuPeriod"`
	Devices         []interface{} `json:"Devices"`
	DNS             interface{}   `json:"Dns"`
	Dnssearch       interface{}   `json:"DnsSearch"`
	Extrahosts      interface{}   `json:"ExtraHosts"`
	Ipcmode         string        `json:"IpcMode"`
	Links           interface{}   `json:"Links"`
	Lxcconf         []interface{} `json:"LxcConf"`
	Memory          int           `json:"Memory"`
	Memoryswap      int           `json:"MemorySwap"`
	Oomkilldisable  bool          `json:"OomKillDisable"`
	Networkmode     string        `json:"NetworkMode"`
	Portbindings    struct {
	} `json:"PortBindings"`
	Privileged      bool `json:"Privileged"`
	Readonlyrootfs  bool `json:"ReadonlyRootfs"`
	Publishallports bool `json:"PublishAllPorts"`
	Restartpolicy   struct {
		Maximumretrycount int    `json:"MaximumRetryCount"`
		Name              string `json:"Name"`
	} `json:"RestartPolicy"`
	Logconfig struct {
		Config interface{} `json:"Config"`
		Type   string      `json:"Type"`
	} `json:"LogConfig"`
	Securityopt interface{} `json:"SecurityOpt"`
	Volumesfrom interface{} `json:"VolumesFrom"`
	Ulimits     []struct {
	} `json:"Ulimits"`
}
