package ec2
import (
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2api"
	"mime/multipart"
	"github.com/layer-x/unik/pkg/types"
	"io"
)

type UnikEC2CPI struct{}

func NewUnikEC2CPI() *UnikEC2CPI {
	return &UnikEC2CPI{}
}

func (cpi *UnikEC2CPI) AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	return ec2api.AttachVolume(volumeNameOrId, unikInstanceId, deviceName)
}

func (cpi *UnikEC2CPI) BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	return ec2api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
}

func (cpi *UnikEC2CPI) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	return ec2api.CreateVolume(volumeName, size)
}

func (cpi *UnikEC2CPI) DeleteArtifacts(unikernelId string) error {
	return ec2api.DeleteArtifacts(unikernelId)
}

func (cpi *UnikEC2CPI) DeleteUnikInstance(unikInstanceId string) error {
	return ec2api.DeleteUnikInstance(unikInstanceId)
}

func (cpi *UnikEC2CPI) DeleteArtifactsForUnikernel(unikernelName string) error {
	return ec2api.DeleteArtifactsForUnikernel(unikernelName)
}

func (cpi *UnikEC2CPI) DeleteUnikernel(unikernelId string, force bool) error {
	return ec2api.DeleteUnikernel(unikernelId, force)
}

func (cpi *UnikEC2CPI) DeleteUnikernelByName(unikernelName string, force bool) error {
	return ec2api.DeleteUnikernelByName(unikernelName, force)
}

func (cpi *UnikEC2CPI) DeleteVolume(volumeNameOrId string, force bool) error {
	return ec2api.DeleteVolume(volumeNameOrId, force)
}

func (cpi *UnikEC2CPI) DetachVolume(volumeNameOrId string, force bool) error {
	return ec2api.DetachVolume(volumeNameOrId, force)
}

func (cpi *UnikEC2CPI) GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return ec2api.GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName)
}

func (cpi *UnikEC2CPI) GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	return ec2api.GetVolumeByIdOrName(volumeIdOrName)
}

func (cpi *UnikEC2CPI) GetLogs(unikInstanceId string) (string, error) {
	return ec2api.GetLogs(unikInstanceId)
}

func (cpi *UnikEC2CPI) ListUnikInstances() ([]*types.UnikInstance, error) {
	return ec2api.ListUnikInstances()
}

func (cpi *UnikEC2CPI) ListUnikernels() ([]*types.Unikernel, error) {
	return ec2api.ListUnikernels()
}

func (cpi *UnikEC2CPI) ListVolumes() ([]*types.Volume, error) {
	return ec2api.ListVolumes()
}

func (cpi *UnikEC2CPI) RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return ec2api.RunUnikInstance(unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikEC2CPI) StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return ec2api.StreamLogs(unikInstanceId, w, deleteInstanceOnDisconnect)
}

func (cpi *UnikEC2CPI) Push(unikernelName string) error {
	return ec2api.Push(unikernelName)
}

func (cpi *UnikEC2CPI) Pull(unikernelName string) error {
	return ec2api.Pull(unikernelName)
}