package photon
import (
"github.com/vmware/photon-controller-go-sdk/photon"
"strings"
)

type UnikPhotonCPI struct{
	client *photon.Client
}

func NewUnikPhotonCPI(photonUrl string) *UnikPhotonCPI {
	photonUrl = strings.TrimPrefix(photonUrl, "http://")
	return &UnikPhotonCPI{
		client: photon.NewClient("http://"+photonUrl, "", nil),
	}
}
func (cpi *UnikPhotonCPI) AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	return ec2api.AttachVolume(volumeNameOrId, unikInstanceId, deviceName)
}

func (cpi *UnikPhotonCPI) BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	return ec2api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
}

func (cpi *UnikPhotonCPI) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	return ec2api.CreateVolume(volumeName, size)
}

func (cpi *UnikPhotonCPI) DeleteArtifacts(unikernelId string) error {
	return ec2api.DeleteArtifacts(unikernelId)
}

func (cpi *UnikPhotonCPI) DeleteUnikInstance(unikInstanceId string) error {
	return ec2api.DeleteUnikInstance(unikInstanceId)
}

func (cpi *UnikPhotonCPI) DeleteArtifactsForUnikernel(unikernelName string) error {
	return ec2api.DeleteArtifactsForUnikernel(unikernelName)
}

func (cpi *UnikPhotonCPI) DeleteUnikernel(unikernelId string, force bool) error {
	return ec2api.DeleteUnikernel(unikernelId, force)
}

func (cpi *UnikPhotonCPI) DeleteUnikernelByName(unikernelName string, force bool) error {
	return ec2api.DeleteUnikernelByName(unikernelName, force)
}

func (cpi *UnikPhotonCPI) DeleteVolume(volumeNameOrId string, force bool) error {
	return ec2api.DeleteVolume(volumeNameOrId, force)
}

func (cpi *UnikPhotonCPI) DetachVolume(volumeNameOrId string, force bool) error {
	return ec2api.DetachVolume(volumeNameOrId, force)
}

func (cpi *UnikPhotonCPI) GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return ec2api.GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName)
}

func (cpi *UnikPhotonCPI) GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	return ec2api.GetVolumeByIdOrName(volumeIdOrName)
}

func (cpi *UnikPhotonCPI) GetLogs(unikInstanceId string) (string, error) {
	return ec2api.GetLogs(unikInstanceId)
}

func (cpi *UnikPhotonCPI) ListUnikInstances() ([]*types.UnikInstance, error) {
	return ec2api.ListUnikInstances()
}

func (cpi *UnikPhotonCPI) ListUnikernels() ([]*types.Unikernel, error) {
	return ec2api.ListUnikernels()
}

func (cpi *UnikPhotonCPI) ListVolumes() ([]*types.Volume, error) {
	return ec2api.ListVolumes()
}

func (cpi *UnikPhotonCPI) RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return ec2api.RunUnikInstance(unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikPhotonCPI) StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return ec2api.StreamLogs(unikInstanceId, w, deleteInstanceOnDisconnect)
}
