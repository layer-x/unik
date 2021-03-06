package types

type Unikernel struct {
	Id string `json:"UnikernelId"`
	UnikernelName string `json:"UnikernelName"`
	CreationDate  string `json:"CreationDate"`
	Created       int64  `json:"Created"`
	Devices       []*DeviceMapping `json:"Devices"`
	//vsphere only
	Path		string `json:"Path"`
}
