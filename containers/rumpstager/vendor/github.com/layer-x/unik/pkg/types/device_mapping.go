package types

type DeviceMapping struct {
	DeviceName        string `json:"DeviceName"`
	MountPoint        string `json:"MountPoint"`
	DefaultSnapshotId string `json:"DefaultSnapshotId"` //aws: snapshot-id, vsphere: path to vmdk
}
