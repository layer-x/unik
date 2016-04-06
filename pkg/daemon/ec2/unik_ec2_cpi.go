package ec2
import (
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2api"
	"mime/multipart"
	"github.com/layer-x/unik/pkg/types"
	"io"
"github.com/layer-x/layerx-commons/lxlog"
)

type UnikEC2CPI struct{}

func NewUnikEC2CPI() *UnikEC2CPI {
	return &UnikEC2CPI{}
}

func (cpi *UnikEC2CPI) AttachVolume(logger *lxlog.LxLogger, volumeNameOrId, unikInstanceId, deviceName string) error {
	return ec2api.AttachVolume(logger, volumeNameOrId, unikInstanceId, deviceName)
}

func (cpi *UnikEC2CPI) BuildUnikernel(logger *lxlog.LxLogger, unikernelName, force string, uploadedTar multipart.File, header *multipart.FileHeader, desiredVolumes []*types.VolumeSpec) error {
	return ec2api.BuildUnikernel(logger, unikernelName, force, uploadedTar, header, desiredVolumes)
}

func (cpi *UnikEC2CPI) CreateVolume(logger *lxlog.LxLogger, volumeName string, size int) (*types.Volume, error) {
	return ec2api.CreateVolume(logger, volumeName, size)
}

func (cpi *UnikEC2CPI) DeleteArtifacts(logger *lxlog.LxLogger, unikernelId string) error {
	return ec2api.DeleteArtifacts(logger, unikernelId)
}

func (cpi *UnikEC2CPI) DeleteUnikInstance(logger *lxlog.LxLogger, unikInstanceId string) error {
	return ec2api.DeleteUnikInstance(logger, unikInstanceId)
}

func (cpi *UnikEC2CPI) DeleteArtifactsForUnikernel(logger *lxlog.LxLogger, unikernelName string) error {
	return ec2api.DeleteArtifactsForUnikernel(logger, unikernelName)
}

func (cpi *UnikEC2CPI) DeleteUnikernel(logger *lxlog.LxLogger, unikernelId string, force bool) error {
	return ec2api.DeleteUnikernel(logger, unikernelId, force)
}

func (cpi *UnikEC2CPI) DeleteUnikernelByName(logger *lxlog.LxLogger, unikernelName string, force bool) error {
	return ec2api.DeleteUnikernelByName(logger, unikernelName, force)
}

func (cpi *UnikEC2CPI) DeleteVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error {
	return ec2api.DeleteVolume(logger, volumeNameOrId, force)
}

func (cpi *UnikEC2CPI) DetachVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error {
	return ec2api.DetachVolume(logger, volumeNameOrId, force)
}

func (cpi *UnikEC2CPI) GetUnikInstanceByPrefixOrName(logger *lxlog.LxLogger, unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return ec2api.GetUnikInstanceByPrefixOrName(logger, unikInstanceIdPrefixOrName)
}

func (cpi *UnikEC2CPI) GetVolumeByIdOrName(logger *lxlog.LxLogger, volumeIdOrName string) (*types.Volume, error) {
	return ec2api.GetVolumeByIdOrName(logger, volumeIdOrName)
}

func (cpi *UnikEC2CPI) GetLogs(logger *lxlog.LxLogger, unikInstanceId string) (string, error) {
	return ec2api.GetLogs(logger, unikInstanceId)
}

func (cpi *UnikEC2CPI) ListUnikInstances(logger *lxlog.LxLogger) ([]*types.UnikInstance, error) {
	return ec2api.ListUnikInstances(logger)
}

func (cpi *UnikEC2CPI) ListUnikernels(logger *lxlog.LxLogger) ([]*types.Unikernel, error) {
	return ec2api.ListUnikernels(logger)
}

func (cpi *UnikEC2CPI) ListVolumes(logger *lxlog.LxLogger) ([]*types.Volume, error) {
	return ec2api.ListVolumes(logger)
}

func (cpi *UnikEC2CPI) RunUnikInstance(logger *lxlog.LxLogger, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return ec2api.RunUnikInstance(logger, unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikEC2CPI) StreamLogs(logger *lxlog.LxLogger, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return ec2api.StreamLogs(logger, unikInstanceId, w, deleteInstanceOnDisconnect)
}

func (cpi *UnikEC2CPI) Push(logger *lxlog.LxLogger, unikernelName string) error {
	return ec2api.Push(logger, unikernelName)
}

func (cpi *UnikEC2CPI) Pull(logger *lxlog.LxLogger, unikernelName string) error {
	return ec2api.Pull(logger, unikernelName)
}