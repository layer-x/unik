package types
import "time"

type Volume struct {
	Size int `json:"Size"`
	CreateTime time.Time `json:"CreateTime"`
	State string `json:"State"`
	VolumeId string `json:"VolumeId"`
	Name string	`json:"Name"`
	Attachments []*Attachment `json:"Attachments"`
}

type Attachment struct {
	AttachTime time.Time `json:"AttachTime"`
	Device string `json:"Device"`
	InstanceId string `json:"InstanceId"`
	State string `json:"State"`
	VolumeId string `json:"VolumeId"`
}

func (v *Volume) IsAttached() bool {
	return v.State == "in-use" && len(v.Attachments) > 0
}