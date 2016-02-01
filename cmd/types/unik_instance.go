package types

type UnikInstance struct {
	Name string `json:"Name"`
	ID string `json:"Id"`
	Unikernel Unikernel `json:"Unikernel"`
}