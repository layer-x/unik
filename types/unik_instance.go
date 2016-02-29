package types

import "time"

type UnikInstance struct {
	UnikInstanceID   string           `json:"UnikID"`
	UnikInstanceName string           `json:"UnikInstanceName"`
	VMID             string           `json:"VMID"`
	UnikernelId      string           `json:"UnikernelId"`
	UnikernelName    string           `json:"UnikernelName"`
	Created          time.Time        `json:"Created"`
	PublicIp         string           `json:"PublicIp"`
	PrivateIp        string           `json:"PrivateIp"`
	State            string           `json:"State"`
	UnikInstanceData UnikInstanceData `json:"UnikInstanceData"`
}

type UnikInstanceData struct {
	Tags			 map[string]string `json:"Tags"`
	Env				 map[string]string `json:"Env"`
}