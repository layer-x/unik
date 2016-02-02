package types

type UnikInstance struct {
	UnikInstanceID string `json:"UnikID"`
	AmazonID string `json:"AmazonID"`
	UnikernelId string `json:"UnikernelId"`
	AppName string `json:"AppName"`
	PublicIp string `json:"PublicIp"`
	PrivateIp string `json:"PrivateIp"`
	State string `json:"State"`
}