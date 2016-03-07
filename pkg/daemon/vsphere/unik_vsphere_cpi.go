package ec2
import (
	"mime/multipart"
	"github.com/layer-x/unik/pkg/types"
	"io"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_api"
	"strings"
	"net/url"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
)

type UnikVsphereCPI struct{
	creds vsphere_api.Creds
}

func NewUnikVsphereCPI(rawUrl, user, password string) *UnikVsphereCPI {
	rawUrl = "https://"+user+":"+password+"@"+strings.TrimSuffix(strings.TrimPrefix(strings.TrimPrefix(rawUrl, "http://"), "https://"), "/sdk")+"/sdk"
	u, err := url.Parse(rawUrl)
	if err != nil {
		lxlog.Fatalf(logrus.Fields{"raw-url": rawUrl, "err": err},"parsing provided url")
	}
	return &UnikVsphereCPI{
		creds: vsphere_api.Creds{
			url: u,
		},
	}
}
func (cpi *UnikVsphereCPI) AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	return vsphere_api.BuildUnikernel(unikernelName, force, uploadedTar, handler)
}

func (cpi *UnikVsphereCPI) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteArtifacts(unikernelId string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteUnikInstance(unikInstanceId string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteArtifactsForUnikernel(unikernelName string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteUnikernel(unikernelId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteUnikernelByName(unikernelName string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteVolume(volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DetachVolume(volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetLogs(unikInstanceId string) (string, error) {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) ListUnikInstances() ([]*types.UnikInstance, error) {
	return vsphere_api.ListUnikInstances(cpi.creds)
}

func (cpi *UnikVsphereCPI) ListUnikernels() ([]*types.Unikernel, error) {
	return vsphere_api.ListUnikernels()
}

func (cpi *UnikVsphereCPI) ListVolumes() ([]*types.Volume, error) {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return vsphere_api.RunUnikInstance(cpi.creds, unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikVsphereCPI) StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return lxerrors.New("method not implemented", nil)
}
