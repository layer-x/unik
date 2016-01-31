package types

type Unikernel struct {
	Name string `json:"Name"`
	ID string `json:"Id"`
	Image Image `json:"Image"`
}