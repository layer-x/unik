package types

type UnikConfig struct {
	Url      string `json:"Url"`
	User     string `json:"User,omitempty"`
	Password string `json:"Password,omitempty"`
}