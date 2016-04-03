package daemon
import (
"mime/multipart"
"github.com/layer-x/unik/pkg/types"
"io"
"github.com/layer-x/layerx-commons/lxlog"
)

type UnikCPI interface {
	AttachVolume(logger *lxlog.LxLogger, volumeNameOrId, unikInstanceId, deviceName string) error
	BuildUnikernel(logger *lxlog.LxLogger, unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error
	CreateVolume(logger *lxlog.LxLogger, volumeName string, size int) (*types.Volume, error)
	DeleteArtifactsForUnikernel(logger *lxlog.LxLogger, unikernelName string) error
	DeleteUnikernel(logger *lxlog.LxLogger, unikernelId string, force bool) error
	DeleteUnikernelByName(logger *lxlog.LxLogger, unikernelName string, force bool) error
	DeleteUnikInstance(logger *lxlog.LxLogger, unikInstanceId string) error
	DeleteVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error
	DetachVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error
	GetUnikInstanceByPrefixOrName(logger *lxlog.LxLogger, unikInstanceIdPrefixOrName string) (logger *lxlog.LxLogger, *types.UnikInstance, error)
	GetVolumeByIdOrName(logger *lxlog.LxLogger, volumeIdOrName string) (*types.Volume, error)
	GetLogs(logger *lxlog.LxLogger, unikInstanceId string) (string, error)
	ListUnikInstances(logger *lxlog.LxLogger, ) ([]*types.UnikInstance, error)
	ListUnikernels(logger *lxlog.LxLogger, ) ([]*types.Unikernel, error)
	ListVolumes(logger *lxlog.LxLogger, ) ([]*types.Volume, error)
	RunUnikInstance(logger *lxlog.LxLogger, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error)
	StreamLogs(logger *lxlog.LxLogger, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error
	Push(logger *lxlog.LxLogger, unikernelName string) error
	Pull(logger *lxlog.LxLogger, unikernelName string) error
}