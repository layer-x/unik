package state

import (
	"github.com/layer-x/unik/pkg/types"
	"io/ioutil"
	"github.com/layer-x/layerx-commons/lxerrors"
	"encoding/json"
	"time"
	"sync"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"net/url"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
	"os"
	"github.com/layer-x/layerx-commons/lxlog"
)

var DEFAULT_UNIK_STATE_FILE = os.Getenv("HOME")+"state.json"

const (
	remote_unik_state_file = "unik/state.json"
)

type UnikState struct {
	lock          *sync.Mutex
	u             *url.URL
	UnikInstances map[string]*types.UnikInstance `json:"UnikInstances"`
	Unikernels    map[string]*types.Unikernel `json:"Unikernels"`
	Saved         time.Time `json:"Saved"`
}

func NewCleanState(u *url.URL) *UnikState {
	return &UnikState{
		UnikInstances: make(map[string]*types.UnikInstance),
		Unikernels: make(map[string]*types.Unikernel),
		lock: &sync.Mutex{},
		u: u,
	}
}

func NewStateFromVsphere(u *url.URL, logger lxlog.Logger) (*UnikState, error) {
	vsphereClient, err := vsphere_utils.NewVsphereClient(u, logger)
	if err != nil {
		return nil, lxerrors.New("initiating vsphere client connection", err)
	}
	err = vsphereClient.DownloadFile(remote_unik_state_file, DEFAULT_UNIK_STATE_FILE)
	if err != nil {
		return nil, lxerrors.New("failed to download unik state file from vsphere", err)
	}
	stateBytes, err := ioutil.ReadFile(DEFAULT_UNIK_STATE_FILE)
	if err != nil {
		return nil, lxerrors.New("could not read state file " + DEFAULT_UNIK_STATE_FILE, err)
	}
	var unikState UnikState
	err = json.Unmarshal(stateBytes, &unikState)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal state json " + string(stateBytes), err)
	}
	unikState.lock = &sync.Mutex{}
	unikState.u = u
	return &unikState, nil
}

func (state *UnikState) Save(logger lxlog.Logger) error {
	state.lock.Lock()
	defer state.lock.Unlock()
	state.Saved = time.Now()
	data, err := json.Marshal(state)
	if err != nil {
		return lxerrors.New("could not marshal state json", err)
	}
	err = lxfileutils.WriteFile(DEFAULT_UNIK_STATE_FILE, data)
	if err != nil {
		return lxerrors.New("could not write state file " + DEFAULT_UNIK_STATE_FILE, err)
	}
	vsphereClient, err := vsphere_utils.NewVsphereClient(state.u, logger)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}
	err = vsphereClient.UploadFile(DEFAULT_UNIK_STATE_FILE, remote_unik_state_file)
	if err != nil {
		return lxerrors.New("failed to upload unik state file to vsphere", err)
	}
	return nil
}