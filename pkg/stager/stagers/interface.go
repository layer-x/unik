package stagers

import "github.com/layer-x/unik/containers/rumpstager/model"

type Stager interface {
	Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error
	CreateDataVolume(mntPoint, deviceName, localFolder string) error
}

var Stagers = make(map[string]Stager)

func registerStager(name string, stager Stager) {
	Stagers[name] = stager
}
