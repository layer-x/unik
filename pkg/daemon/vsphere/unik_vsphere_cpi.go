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
	"github.com/layer-x/layerx-commons/lxmartini"
	"net/http"
	"fmt"
	"encoding/json"
	"github.com/layer-x/unik/pkg/daemon/state"
"net"
"time"
)

type UnikVsphereCPI struct {
	creds    vsphere_api.Creds
	unikState *state.UnikState
}

func NewUnikVsphereCPI(logger *lxlog.LxLogger, rawUrl, user, password string) *UnikVsphereCPI {
	rawUrl = "https://" + user + ":" + password + "@" + strings.TrimSuffix(strings.TrimPrefix(strings.TrimPrefix(rawUrl, "http://"), "https://"), "/sdk") + "/sdk"
	u, err := url.Parse(rawUrl)
	if err != nil {
		logger.WithErr(err).WithFields(lxlog.Fields{
			"raw-url": rawUrl,
		}).Fatalf("parsing provided url")
	}
	unikState, err := state.NewStateFromVsphere(u, logger)
	if err != nil {
		logger.WithErr(err).WithFields(lxlog.Fields{
			"state": unikState,
		}).Warnf("could not load unik state, creating fresh")
		unikState = state.NewCleanState(u)
	}
	logger.WithFields(lxlog.Fields{
		"state": unikState,
	}).Infof("loaded unik state")
	return &UnikVsphereCPI{
		creds: vsphere_api.Creds{
			URL: u,
		},
		unikState: unikState,
	}
}

func (cpi *UnikVsphereCPI) StartInstanceDiscovery(logger *lxlog.LxLogger) {
	logger.Infof("Starting unik discovery (udp heartbeat broadcast)")
	info := []byte("unik")
	BROADCAST_IPv4 := net.IPv4(255, 255, 255, 255)
	socket, err := net.DialUDP("udp4", nil, &net.UDPAddr{
		IP:   BROADCAST_IPv4,
		Port: 9876,
	})
	if err != nil {
		logger.WithErr(err).WithFields(lxlog.Fields{
			"broadcast-ip": BROADCAST_IPv4,
		}).Fatalf("failed to dial udp broadcast connection")
	}
	go func(){
		for {
			_, err = socket.Write(info)
			if err != nil {
				logger.WithErr(err).WithFields(lxlog.Fields{
					"broadcast-ip": BROADCAST_IPv4,
				}).Fatalf("failed writing to broadcast udp socket")
			}
			time.Sleep(2000 * time.Millisecond)
		}
	}()
}

func (cpi *UnikVsphereCPI) ListenForBootstrap(logger *lxlog.LxLogger, port int) {
	m := lxmartini.QuietMartini()
	m.Get("/bootstrap", func(res http.ResponseWriter, req *http.Request) string {
		splitAddr := strings.Split(req.RemoteAddr, ":")
		if len(splitAddr) < 1 {
			logger.WithFields(lxlog.Fields{
				"req.RemoteAddr": req.RemoteAddr,
			}).Errorf("could not parse remote addr into ip/port combination")
			return ""
		}
		instanceIp := splitAddr[0]
		macAddress := req.URL.Query().Get("mac_address")
		logger.WithFields(lxlog.Fields{
			"Ip": instanceIp, 
			"mac-address": macAddress,
		}).Infof("Instance registered with mDNS")
		//mac address = the instance id in vsphere
		unikInstance, err := cpi.GetUnikInstanceByPrefixOrName(logger, macAddress)
		if err != nil {
			logger.WithFields(lxlog.Fields{
				"state": cpi.unikState,
			}).Errorf("could not find unik instance by mac address")
			return ""
		}
		unikInstance.PrivateIp = instanceIp
		unikInstance.PublicIp = instanceIp
		cpi.unikState.UnikInstances[macAddress] = unikInstance
		cpi.unikState.Save(logger)
		data, _ := json.Marshal(unikInstance.UnikInstanceData)
		return string(data)
	})
	go m.RunOnAddr(fmt.Sprintf(":%v", port))
}

func (cpi *UnikVsphereCPI) AttachVolume(logger *lxlog.LxLogger, volumeNameOrId, unikInstanceId, deviceName string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) BuildUnikernel(logger *lxlog.LxLogger, unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader, desiredVolumes []*types.VolumeSpec) error {
	return vsphere_api.BuildUnikernel(logger, cpi.unikState, cpi.creds, unikernelName, force, uploadedTar, handler, desiredVolumes)
}

func (cpi *UnikVsphereCPI) CreateVolume(logger *lxlog.LxLogger, volumeName string, size int) (*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DeleteUnikInstance(logger *lxlog.LxLogger, unikInstanceId string) error {
	return vsphere_api.DeleteUnikInstance(logger, cpi.unikState, cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) DeleteArtifactsForUnikernel(logger *lxlog.LxLogger, unikernelName string) error {
	return nil
}

func (cpi *UnikVsphereCPI) DeleteUnikernel(logger *lxlog.LxLogger, unikernelId string, force bool) error {
	return vsphere_api.DeleteUnikernel(logger, cpi.unikState, cpi.creds, unikernelId, force)
}

func (cpi *UnikVsphereCPI) DeleteUnikernelByName(logger *lxlog.LxLogger, unikernelName string, force bool) error {
	return vsphere_api.DeleteUnikernelByName(logger, cpi.unikState, cpi.creds, unikernelName, force)
}

func (cpi *UnikVsphereCPI) DeleteVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) DetachVolume(logger *lxlog.LxLogger, volumeNameOrId string, force bool) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetUnikInstanceByPrefixOrName(logger *lxlog.LxLogger, unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	return vsphere_api.GetUnikInstanceByPrefixOrName(logger, cpi.unikState, cpi.creds, unikInstanceIdPrefixOrName)
}

func (cpi *UnikVsphereCPI) GetVolumeByIdOrName(logger *lxlog.LxLogger, volumeIdOrName string) (*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) GetLogs(logger *lxlog.LxLogger, unikInstanceId string) (string, error) {
	return vsphere_api.GetLogs(logger, cpi.unikState, cpi.creds, unikInstanceId)
}

func (cpi *UnikVsphereCPI) ListUnikInstances(logger *lxlog.LxLogger) ([]*types.UnikInstance, error) {
	return vsphere_api.ListUnikInstances(logger, cpi.unikState, cpi.creds)
}

func (cpi *UnikVsphereCPI) ListUnikernels(logger *lxlog.LxLogger) ([]*types.Unikernel, error) {
	return vsphere_api.ListUnikernels(logger, cpi.unikState)
}

func (cpi *UnikVsphereCPI) ListVolumes(logger *lxlog.LxLogger) ([]*types.Volume, error) {
	return nil, lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) RunUnikInstance(logger *lxlog.LxLogger, unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	return vsphere_api.RunUnikInstance(logger, cpi.unikState, cpi.creds, unikernelName, instanceName, instances, tags, env)
}

func (cpi *UnikVsphereCPI) StreamLogs(logger *lxlog.LxLogger, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	return vsphere_api.StreamLogs(logger, cpi.unikState, cpi.creds, unikInstanceId, w, deleteInstanceOnDisconnect)
}

func (cpi *UnikVsphereCPI) Push(logger *lxlog.LxLogger, unikernelName string) error {
	return lxerrors.New("method not implemented", nil)
}

func (cpi *UnikVsphereCPI) Pull(logger *lxlog.LxLogger, unikernelName string) error {
	return lxerrors.New("method not implemented", nil)
}