package types

type Unikernel struct {
	AMI string `json:"AMI_ID"`
	UnikernelName string `json:"UnikernelName"`
	CreationDate string `json:"CreationDate"`
	Created int64 `json:"Created"`
}
