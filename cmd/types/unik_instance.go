package types
import "time"

type UnikInstance struct {
	UnikInstanceID string `json:"UnikID"`
	UnikInstanceName string `json:"UnikInstanceName"`
	AmazonID string `json:"AmazonID"`
	UnikernelId string `json:"UnikernelId"`
	UnikernelName string `json:"UnikernelName"`
	Created time.Time `json:"Created"`
	PublicIp string `json:"PublicIp"`
	PrivateIp string `json:"PrivateIp"`
	State string `json:"State"`
}