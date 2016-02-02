package types

type UnikInstance struct {
	ID string `json:"ID"`
	UnikernelId string `json:"UnikernelId"`
	AppName string `json:"AppName"`
	PublicIp string `json:"PublicIp"`
	PrivateIp string `json:"PrivateIp"`
	State string `json:"State"`
}