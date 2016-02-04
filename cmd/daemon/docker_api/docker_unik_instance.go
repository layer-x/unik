package docker_api
import (
	"github.com/layer-x/unik/cmd/types"
	"time"
)

type DockerUnikInstance struct {
	ID         string `json:"Id"`
	Names      []string `json:"Names"`
	Image      string `json:"Image"`
	Command    string `json:"Command"`
	Created    int64 `json:"Created"`
	Status     string `json:"Status"`
	//	Ports      []struct {
	//		Privateport int `json:"PrivatePort"`
	//		Publicport  int `json:"PublicPort"`
	//		Type        string `json:"Type"`
	//	} `json:"Ports"`
	//	Labels     struct {
	//				   ComExampleVendor  string `json:"com.example.vendor"`
	//				   ComExampleLicense string `json:"com.example.license"`
	//				   ComExampleVersion string `json:"com.example.version"`
	//			   } `json:"Labels"`
	Sizerw     int `json:"SizeRw"`
	Sizerootfs int `json:"SizeRootFs"`
}

func convertUnikInstance(unikInstance *types.UnikInstance) *DockerUnikInstance {
	return &DockerUnikInstance{
		ID: unikInstance.UnikInstanceID,
		Names: []string{unikInstance.UnikInstanceName},
		Image: unikInstance.UnikernelName,
		Command: "N/A",
		Created: unikInstance.Created.Unix(),
		Status: unikInstance.State,
		//		Ports: []struct {
		//			Privateport int `json:"PrivatePort"`
		//			Publicport  int `json:"PublicPort"`
		//			Type        string `json:"Type"`
		//		}{},
		//		Labels: struct {
		//			ComExampleVendor  string `json:"com.example.vendor"`
		//			ComExampleLicense string `json:"com.example.license"`
		//			ComExampleVersion string `json:"com.example.version"`
		//		}{
		//			ComExampleVendor: "UnikVendor",
		//			ComExampleLicense: "UnikLicense",
		//			ComExampleVersion: "UnikVersion0.0.0",
		//		},
		Sizerw: 1000,
		Sizerootfs: 1000,
	}
}

type DockerUnikInstanceVerbose struct {
	Apparmorprofile string `json:"AppArmorProfile"`
	Args            []string `json:"Args"`
	Config          struct {
						Attachstderr    bool `json:"AttachStderr"`
						Attachstdin     bool `json:"AttachStdin"`
						Attachstdout    bool `json:"AttachStdout"`
						Cmd             []string `json:"Cmd"`
						Domainname      string `json:"Domainname"`
						Entrypoint      interface{} `json:"Entrypoint"`
						Env             []string `json:"Env"`
						Exposedports    interface{} `json:"ExposedPorts"`
						Hostname        string `json:"Hostname"`
						Image           string `json:"Image"`
						Labels          struct {
											ComExampleVendor  string `json:"com.example.vendor"`
											ComExampleLicense string `json:"com.example.license"`
											ComExampleVersion string `json:"com.example.version"`
										} `json:"Labels"`
						Macaddress      string `json:"MacAddress"`
						Networkdisabled bool `json:"NetworkDisabled"`
						Onbuild         interface{} `json:"OnBuild"`
						Openstdin       bool `json:"OpenStdin"`
						Stdinonce       bool `json:"StdinOnce"`
						Tty             bool `json:"Tty"`
						User            string `json:"User"`
						Volumes         interface{} `json:"Volumes"`
						Workingdir      string `json:"WorkingDir"`
					} `json:"Config"`
	Created         time.Time `json:"Created"`
	Driver          string `json:"Driver"`
	Execdriver      string `json:"ExecDriver"`
	Execids         interface{} `json:"ExecIDs"`
	Hostconfig      hostConfig `json:"HostConfig"`
	Hostnamepath    string `json:"HostnamePath"`
	Hostspath       string `json:"HostsPath"`
	Logpath         string `json:"LogPath"`
	ID              string `json:"Id"`
	Image           string `json:"Image"`
	Mountlabel      string `json:"MountLabel"`
	Name            string `json:"Name"`
	Networksettings struct {
						Bridge      string `json:"Bridge"`
						Gateway     string `json:"Gateway"`
						Ipaddress   string `json:"IPAddress"`
						Ipprefixlen int `json:"IPPrefixLen"`
						Macaddress  string `json:"MacAddress"`
						Portmapping interface{} `json:"PortMapping"`
						Ports       interface{} `json:"Ports"`
					} `json:"NetworkSettings"`
	Path            string `json:"Path"`
	Processlabel    string `json:"ProcessLabel"`
	Resolvconfpath  string `json:"ResolvConfPath"`
	Restartcount    int `json:"RestartCount"`
	State           struct {
						Error      string `json:"Error"`
						Exitcode   int `json:"ExitCode"`
						Finishedat time.Time `json:"FinishedAt"`
						Oomkilled  bool `json:"OOMKilled"`
						Paused     bool `json:"Paused"`
						Pid        int `json:"Pid"`
						Restarting bool `json:"Restarting"`
						Running    bool `json:"Running"`
						Startedat  time.Time `json:"StartedAt"`
					} `json:"State"`
	Mounts          []struct {
		Source      string `json:"Source"`
		Destination string `json:"Destination"`
		Mode        string `json:"Mode"`
		Rw          bool `json:"RW"`
	} `json:"Mounts"`
}

func convertUnikInstanceVerbose(unikInstance *types.UnikInstance) *DockerUnikInstanceVerbose {
	return &DockerUnikInstanceVerbose{
		ID: unikInstance.UnikInstanceID,
		Config: struct {
			Attachstderr    bool `json:"AttachStderr"`
			Attachstdin     bool `json:"AttachStdin"`
			Attachstdout    bool `json:"AttachStdout"`
			Cmd             []string `json:"Cmd"`
			Domainname      string `json:"Domainname"`
			Entrypoint      interface{} `json:"Entrypoint"`
			Env             []string `json:"Env"`
			Exposedports    interface{} `json:"ExposedPorts"`
			Hostname        string `json:"Hostname"`
			Image           string `json:"Image"`
			Labels          struct {
								ComExampleVendor  string `json:"com.example.vendor"`
								ComExampleLicense string `json:"com.example.license"`
								ComExampleVersion string `json:"com.example.version"`
							} `json:"Labels"`
			Macaddress      string `json:"MacAddress"`
			Networkdisabled bool `json:"NetworkDisabled"`
			Onbuild         interface{} `json:"OnBuild"`
			Openstdin       bool `json:"OpenStdin"`
			Stdinonce       bool `json:"StdinOnce"`
			Tty             bool `json:"Tty"`
			User            string `json:"User"`
			Volumes         interface{} `json:"Volumes"`
			Workingdir      string `json:"WorkingDir"`
		}{
			Image: unikInstance.UnikernelName,
		},
		Hostconfig: hostConfig{
			Logconfig: struct {
				Config interface{} `json:"Config"`
				Type   string `json:"Type"`
			}{
				Type: "json-file",
			},
		},
		Image: unikInstance.UnikernelName,
		Created: unikInstance.Created,
	}
}

type hostConfig struct {
	Binds           interface{} `json:"Binds"`
	Blkioweight     int `json:"BlkioWeight"`
	Capadd          interface{} `json:"CapAdd"`
	Capdrop         interface{} `json:"CapDrop"`
	Containeridfile string `json:"ContainerIDFile"`
	Cpusetcpus      string `json:"CpusetCpus"`
	Cpusetmems      string `json:"CpusetMems"`
	Cpushares       int `json:"CpuShares"`
	Cpuperiod       int `json:"CpuPeriod"`
	Devices         []interface{} `json:"Devices"`
	DNS             interface{} `json:"Dns"`
	Dnssearch       interface{} `json:"DnsSearch"`
	Extrahosts      interface{} `json:"ExtraHosts"`
	Ipcmode         string `json:"IpcMode"`
	Links           interface{} `json:"Links"`
	Lxcconf         []interface{} `json:"LxcConf"`
	Memory          int `json:"Memory"`
	Memoryswap      int `json:"MemorySwap"`
	Oomkilldisable  bool `json:"OomKillDisable"`
	Networkmode     string `json:"NetworkMode"`
	Portbindings    struct {
					} `json:"PortBindings"`
	Privileged      bool `json:"Privileged"`
	Readonlyrootfs  bool `json:"ReadonlyRootfs"`
	Publishallports bool `json:"PublishAllPorts"`
	Restartpolicy   struct {
						Maximumretrycount int `json:"MaximumRetryCount"`
						Name              string `json:"Name"`
					} `json:"RestartPolicy"`
	Logconfig       struct {
						Config interface{} `json:"Config"`
						Type   string `json:"Type"`
					} `json:"LogConfig"`
	Securityopt     interface{} `json:"SecurityOpt"`
	Volumesfrom     interface{} `json:"VolumesFrom"`
	Ulimits         []struct {
	} `json:"Ulimits"`
}