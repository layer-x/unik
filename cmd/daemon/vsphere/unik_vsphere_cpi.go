package ec2
import (
	"mime/multipart"
	"github.com/layer-x/unik/types"
	"io"
	"github.com/layer-x/unik/cmd/daemon/vsphere/vsphere_api"
	"strings"
	"net/url"
	"github.com/layer-x/unik/cmd/daemon/vsphere/vsphere_utils"
)

type UnikVsphereCPI struct{
	creds vsphere_api.Creds
}

func NewUnikVsphereCPI(rawUrl, user, password string) *UnikVsphereCPI {
	rawUrl = "https://"+user+":"+password+"@"+strings.TrimSuffix(strings.TrimPrefix(strings.TrimPrefix(rawUrl, "http://"), "https://"), "/sdk")+"/sdk"
	u, err := url.Parse(rawUrl)
	if err != nil {
		panic("parsing provided url "+ rawUrl+": "+err.Error())
	}
	return &UnikVsphereCPI{
		creds: vsphere_api.Creds{
			url: u,
		},
	}
}
func (cpi *UnikVsphereCPI) AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	return vsphere_api.AttachVolume(cpi.creds, volumeNameOrId, unikInstanceId, deviceName)
}

func (cpi *UnikVsphereCPI) BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	return vsphere_api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
}

func (cpi *UnikVsphereCPI) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	return vsphere_api.CreateVolume(cpi.creds, volumeName, size)
}

func (cpi *UnikVsphereCPI) DeleteArtifacts(unikernelId string) error {
	return vsphere_api.DeleteArtifacts(cpi.creds, unikernelId)
}

func (cpi *UnikVsphereCPI) DeleteUnikInstance(unikInstanceId string) error {
	return vsphere_api.DeleteUnikInstance(cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) DeleteArtifactsForUnikernel(unikernelName string) error {
	return vsphere_api.DeleteArtifactsForUnikernel(cpi.creds, unikernelName)
}

func (cpi *UnikVsphereCPI) DeleteUnikernel(unikernelId string, force bool) error {
	return vsphere_api.DeleteUnikernel(cpi.creds, unikernelId, force)
}

func (cpi *UnikVsphereCPI) DeleteUnikernelByName(unikernelName string, force bool) error {
	return vsphere_api.DeleteUnikernelByName(cpi.creds, unikernelName, force)
}

func (cpi *UnikVsphereCPI) DeleteVolume(volumeNameOrId string, force bool) error {
	return vsphere_api.DeleteVolume(cpi.creds, volumeNameOrId, force)
}

func (cpi *UnikVsphereCPI) DetachVolume(volumeNameOrId string, force bool) error {
	return vsphere_api.DetachVolume(cpi.creds, volumeNameOrId, force)
}

func (cpi *UnikVsphereCPI) GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return vsphere_api.GetUnikInstanceByPrefixOrName(cpi.creds, unikInstanceIdPrefixOrName)
}

func (cpi *UnikVsphereCPI) GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	return vsphere_api.GetVolumeByIdOrName(cpi.creds, volumeIdOrName)
}

func (cpi *UnikVsphereCPI) GetLogs(unikInstanceId string) (string, error) {
	return vsphere_api.GetLogs(cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) ListUnikInstances() ([]*types.UnikInstance, error) {
	return vsphere_api.ListUnikInstances(cpi.creds)
}

func (cpi *UnikVsphereCPI) ListUnikernels() ([]*types.Unikernel, error) {
	return vsphere_api.ListUnikernels()
}

func (cpi *UnikVsphereCPI) ListVolumes() ([]*types.Volume, error) {
	return vsphere_api.ListVolumes(cpi.creds)
}

func (cpi *UnikVsphereCPI) RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return vsphere_api.RunUnikInstance(cpi.creds, unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikVsphereCPI) StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return vsphere_api.StreamLogs(cpi.creds, unikInstanceId, w, deleteInstanceOnDisconnect)
}
