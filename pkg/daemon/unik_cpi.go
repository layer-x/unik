package daemon
import (
"mime/multipart"
"github.com/layer-x/unik/pkg/types"
"io"
"github.com/layer-x/layerx-commons/lxlog"
)

type UnikCPI interface {
	AttachVolume(logger lxlog.Logger, volumeNameOrId, unikInstanceId, deviceName string) error
	BuildUnikernel(logger lxlog.Logger, unikernelName, force string, uploadedTar multipart.File, header *multipart.FileHeader, desiredVolumes []*types.VolumeSpec) error
	CreateVolume(logger lxlog.Logger, volumeName string, size int) (*types.Volume, error)
	DeleteArtifactsForUnikernel(logger lxlog.Logger, unikernelName string) error
	DeleteUnikernel(logger lxlog.Logger, unikernelId string, force bool) error
	DeleteUnikernelByName(logger lxlog.Logger, unikernelName string, force bool) error
	DeleteUnikInstance(logger lxlog.Logger, unikInstanceId string) error
	DeleteVolume(logger lxlog.Logger, volumeNameOrId string, force bool) error
	DetachVolume(logger lxlog.Logger, volumeNameOrId string, force bool) error
	GetUnikInstanceByPrefixOrName(logger lxlog.Logger, unikInstanceIdPrefixOrName string) (*types.UnikInstance, error)
	GetVolumeByIdOrName(logger lxlog.Logger, volumeIdOrName string) (*types.Volume, error)
	GetLogs(logger lxlog.Logger, unikInstanceId string) (string, error)
	ListUnikInstances(logger lxlog.Logger) ([]*types.UnikInstance, error)
	ListUnikernels(logger lxlog.Logger) ([]*types.Unikernel, error)
	ListVolumes(logger lxlog.Logger) ([]*types.Volume, error)
	RunUnikInstance(logger lxlog.Logger, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error)
	StreamLogs(logger lxlog.Logger, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error
	Push(logger lxlog.Logger, unikernelName string) error
	Pull(logger lxlog.Logger, unikernelName string) error
}