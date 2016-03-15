package vsphere

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
	"github.com/layer-x/layerx-commons/lxmartini"
	"net/http"
	"fmt"
	"encoding/json"
	"github.com/layer-x/unik/pkg/daemon/state"
)

type UnikVsphereCPI struct {
	creds    vsphere_api.Creds
	unikState *state.UnikState
}

func NewUnikVsphereCPI(rawUrl, user, password string) *UnikVsphereCPI {
	rawUrl = "https://" + user + ":" + password + "@" + strings.TrimSuffix(strings.TrimPrefix(strings.TrimPrefix(rawUrl, "http://"), "https://"), "/sdk") + "/sdk"
	u, err := url.Parse(rawUrl)
	if err != nil {
		lxlog.Fatalf(logrus.Fields{"raw-url": rawUrl, "err": err}, "parsing provided url")
	}
	unikState, err := state.NewStateFromFile(state.DEFAULT_UNIK_STATE_FILE)
	if err != nil {
		lxlog.Warnf(logrus.Fields{"state": unikState, "err": err}, "could not load unik state, creating fresh")
		unikState = state.NewCleanState()
	}
	lxlog.Infof(logrus.Fields{"state": unikState}, "loaded unik state")
	return &UnikVsphereCPI{
		creds: vsphere_api.Creds{
			URL: u,
		},
		unikState: unikState,
	}
}

func (cpi *UnikVsphereCPI) ListenForBootstrap(port int) {
	m := lxmartini.QuietMartini()
	m.Get("/bootstrap", func(res http.ResponseWriter, req *http.Request) string {
		splitAddr := strings.Split(req.RemoteAddr, ":")
		if len(splitAddr) < 1 {
			lxlog.Errorf(logrus.Fields{"req.RemoteAddr": req.RemoteAddr}, "could not parse remote addr into ip/port combination")
			return ""
		}
		instanceIp := splitAddr[0]
		macAddress := req.URL.Query().Get("mac_address")
		lxlog.Infof(logrus.Fields{"Ip": instanceIp, "mac-address": macAddress}, "Instance registered with mDNS")
		//mac address = the instance id in vsphere
		if unikInstance, ok := cpi.unikState.UnikInstances[macAddress]; ok {
			unikInstance.PrivateIp = instanceIp
			unikInstance.PublicIp = instanceIp
		} else {
			lxlog.Errorf(logrus.Fields{"state": cpi.unikState}, "could not find mac address in known vsphere instances")
			return ""
		}
		cpi.unikState.Save(state.DEFAULT_UNIK_STATE_FILE)
		unikInstance, err := cpi.GetUnikInstanceByPrefixOrName(macAddress)
		if err != nil {
			lxlog.Errorf(logrus.Fields{"state": cpi.unikState}, "could not find mac address in known vsphere instances")
			return ""
		}
		data, _ := json.Marshal(unikInstance.UnikInstanceData)
		return string(data)
	})
	go m.RunOnAddr(fmt.Sprintf(":%v", port))
}

func (cpi *UnikVsphereCPI) AttachVolume(volumeNameOrId, unikInstanceId, deviceName string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) BuildUnikernel(unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	return vsphere_api.BuildGoUnikernel(cpi.unikState, cpi.creds, unikernelName, force, uploadedTar, handler)
}

func (cpi *UnikVsphereCPI) CreateVolume(volumeName string, size int) (*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteUnikInstance(unikInstanceId string) error {
	return vsphere_api.DeleteUnikInstance(cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) DeleteArtifactsForUnikernel(unikernelName string) error {
	return nil
}

func (cpi *UnikVsphereCPI) DeleteUnikernel(unikernelId string, force bool) error {
	return vsphere_api.DeleteUnikernel(cpi.unikState, cpi.creds, unikernelId, force)
}

func (cpi *UnikVsphereCPI) DeleteUnikernelByName(unikernelName string, force bool) error {
	return vsphere_api.DeleteUnikernelByName(cpi.unikState, cpi.creds, unikernelName, force)
}

func (cpi *UnikVsphereCPI) DeleteVolume(volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DetachVolume(volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return vsphere_api.GetUnikInstanceByPrefixOrName(cpi.unikState, cpi.creds, unikInstanceIdPrefixOrName)
}

func (cpi *UnikVsphereCPI) GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetLogs(unikInstanceId string) (string, error) {
	return vsphere_api.GetLogs(cpi.unikState, cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) ListUnikInstances() ([]*types.UnikInstance, error) {
	return vsphere_api.ListUnikInstances(cpi.unikState, cpi.creds)
}

func (cpi *UnikVsphereCPI) ListUnikernels() ([]*types.Unikernel, error) {
	return vsphere_api.ListUnikernels(cpi.unikState)
}

func (cpi *UnikVsphereCPI) ListVolumes() ([]*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return vsphere_api.RunUnikInstance(cpi.unikState, cpi.creds, unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikVsphereCPI) StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return vsphere_api.StreamLogs(cpi.unikState, cpi.creds, unikInstanceId, w, deleteInstanceOnDisconnect)
}
