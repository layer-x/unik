package types

type Unikernel struct {
	ImageId string `json:"ImageId"`
	UnikernelName string `json:"UnikernelName"`
	CreationDate  string `json:"CreationDate"`
	Created       int64  `json:"Created"`
}
