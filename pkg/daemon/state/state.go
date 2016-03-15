package state

import (
	"github.com/layer-x/unik/pkg/types"
	"io/ioutil"
	"github.com/layer-x/layerx-commons/lxerrors"
	"encoding/json"
	"time"
	"sync"
"github.com/layer-x/layerx-commons/lxfileutils"
	"os"
)

var (
	DEFAULT_UNIK_STATE_FOLDER = os.Getenv("HOME") + ".unikd/"
	DEFAULT_UNIK_STATE_FILE = DEFAULT_UNIK_STATE_FOLDER + "state.json"
)

type UnikState struct {
	lock *sync.Mutex
	UnikInstances map[string]*types.UnikInstance `json:"UnikInstances"`
	Unikernels    map[string]*types.Unikernel `json:"Unikernels"`
	Saved	time.Time `json:"Saved"`
}

func NewCleanState() *UnikState {
	return &UnikState{
		UnikInstances: make(map[string]*types.UnikInstance),
		Unikernels: make(map[string]*types.Unikernel),
		lock: &sync.Mutex{},
	}
}

func NewStateFromFile(fileName string) (*UnikState, error) {
	stateBytes, err := ioutil.ReadFile(fileName)
	if err != nil {
		return nil, lxerrors.New("could not read state file " + fileName, err)
	}
	var unikState UnikState
	err = json.Unmarshal(stateBytes, &unikState)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal state json " + string(stateBytes), err)
	}
	unikState.lock = &sync.Mutex{}
	return &unikState, nil
}

func (state *UnikState) Save(fileName string) error {
	state.lock.Lock()
	defer state.lock.Unlock()
	state.Saved = time.Now()
	data, err := json.Marshal(state)
	if err != nil {
		return lxerrors.New("could not marshal state json", err)
	}
	err = lxfileutils.WriteFile(fileName, data)
	if err != nil {
		return lxerrors.New("could not write state file " + fileName, err)
	}
	return nil
}