package docker_api

type DockerRunRequest struct {
	Hostname     string   `json:"Hostname"`
	Domainname   string   `json:"Domainname"`
	User         string   `json:"User"`
	Attachstdin  bool     `json:"AttachStdin"`
	Attachstdout bool     `json:"AttachStdout"`
	Attachstderr bool     `json:"AttachStderr"`
	Tty          bool     `json:"Tty"`
	Openstdin    bool     `json:"OpenStdin"`
	Stdinonce    bool     `json:"StdinOnce"`
	Env          []string `json:"Env"`
	Cmd          []string `json:"Cmd"`
	Entrypoint   string   `json:"Entrypoint"`
	Image        string   `json:"Image"`
	Labels       struct {
		ComExampleVendor  string `json:"com.example.vendor"`
		ComExampleLicense string `json:"com.example.license"`
		ComExampleVersion string `json:"com.example.version"`
	} `json:"Labels"`
	Mounts []struct {
		Source      string `json:"Source"`
		Destination string `json:"Destination"`
		Mode        string `json:"Mode"`
		Rw          bool   `json:"RW"`
	} `json:"Mounts"`
	Workingdir      string `json:"WorkingDir"`
	Networkdisabled bool   `json:"NetworkDisabled"`
	Macaddress      string `json:"MacAddress"`
	Exposedports    struct {
		Two2TCP struct {
		} `json:"22/tcp"`
	} `json:"ExposedPorts"`
	Hostconfig struct {
		Binds   []string `json:"Binds"`
		Links   []string `json:"Links"`
		Lxcconf []struct {
			LxcUtsname string `json:"lxc.utsname"`
		} `json:"LxcConf"`
		Memory           int    `json:"Memory"`
		Memoryswap       int    `json:"MemorySwap"`
		Cpushares        int    `json:"CpuShares"`
		Cpuperiod        int    `json:"CpuPeriod"`
		Cpuquota         int    `json:"CpuQuota"`
		Cpusetcpus       string `json:"CpusetCpus"`
		Cpusetmems       string `json:"CpusetMems"`
		Blkioweight      int    `json:"BlkioWeight"`
		Memoryswappiness int    `json:"MemorySwappiness"`
		Oomkilldisable   bool   `json:"OomKillDisable"`
		Portbindings     struct {
			Two2TCP []struct {
				Hostport string `json:"HostPort"`
			} `json:"22/tcp"`
		} `json:"PortBindings"`
		Publishallports bool        `json:"PublishAllPorts"`
		Privileged      bool        `json:"Privileged"`
		Readonlyrootfs  bool        `json:"ReadonlyRootfs"`
		DNS             []string    `json:"Dns"`
		Dnssearch       []string    `json:"DnsSearch"`
		Extrahosts      interface{} `json:"ExtraHosts"`
		Volumesfrom     []string    `json:"VolumesFrom"`
		Capadd          []string    `json:"CapAdd"`
		Capdrop         []string    `json:"CapDrop"`
		Restartpolicy   struct {
			Name              string `json:"Name"`
			Maximumretrycount int    `json:"MaximumRetryCount"`
		} `json:"RestartPolicy"`
		Networkmode string        `json:"NetworkMode"`
		Devices     []interface{} `json:"Devices"`
		Ulimits     []struct {
		} `json:"Ulimits"`
		Logconfig struct {
			Type   string `json:"Type"`
			Config struct {
			} `json:"Config"`
		} `json:"LogConfig"`
		Securityopt  []string `json:"SecurityOpt"`
		Cgroupparent string   `json:"CgroupParent"`
	} `json:"HostConfig"`
}

type DockerRunResponse struct {
	Id string `json:"Id"`
}
