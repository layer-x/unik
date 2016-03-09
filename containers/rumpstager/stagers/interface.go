package stagers

import "github.com/layer-x/unik/containers/rumpstager/model"

type Stager interface {
	Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig)
}
