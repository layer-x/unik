package main
import (
"mime/multipart"
"github.com/layer-x/unik/types"
"io"
)

type UnikCPI interface {
	AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error
	BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error
	CreateVolume(volumeName string, size int) (*types.Volume, error)
	DeleteArtifacts(unikernelId string) error
	DeleteArtifactsForUnikernel(unikernelName string) error
	DeleteUnikInstance(unikInstanceId string) error
	DeleteUnikernel(unikernelId string, force bool) error
	DeleteUnikernelByName(unikernelName string, force bool) error
	DeleteVolume(volumeNameOrId string, force bool) error
	DetachVolume(volumeNameOrId string, force bool) error
	GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error)
	GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error)
	GetLogs(unikInstanceId string) (string, error)
	ListUnikInstances() ([]*types.UnikInstance, error)
	ListUnikernels() ([]*types.Unikernel, error)
	ListVolumes() ([]*types.Volume, error)
	RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error)
	StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error
}