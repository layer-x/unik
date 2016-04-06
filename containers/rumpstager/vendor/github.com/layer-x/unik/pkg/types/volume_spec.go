package types

import "mime/multipart"

type VolumeSpec struct {
	MountPoint    string
	DataFolder    string
	Size          int64
	DataTar       multipart.File
	DataTarHeader *multipart.FileHeader
}
