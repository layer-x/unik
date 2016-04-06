package stagers

import (
	"path/filepath"
	"strings"

	"github.com/layer-x/unik/containers/rumpstager/model"

	"github.com/layer-x/unik/containers/rumpstager/shell"
	"errors"
)

func init() {

	stager := &VMwareVolumeStager{&QEmuVolumeStager{"/dev/sd", ".", false}}
	registerStager("vmware", stager)

}

type VMwareVolumeStager struct {
	qemuStager *QEmuVolumeStager
}

func (s *VMwareVolumeStager) Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error {

	c = addVMwareNet(c)

	err := s.qemuStager.Stage(appName, kernelPath, volumes, c)
	if err != nil {
		return err
	}

	// convert all the img files to vmdk files
	matches, err := filepath.Glob("*.img")
	if err != nil {
		return err
	}

	for _, input := range matches {
		output := strings.Replace(input, ".img", ".vmdk", -1)
		err := shell.RunLogCommand("qemu-img", "convert", "-O", "vmdk", input, output)
		if err != nil {
			return err
		}
	}

	return nil
}

func (s *VMwareVolumeStager) CreateDataVolume(mntPoint, deviceName, localFolder string) error {
	return errors.New("not implemented")
}

func addVMwareNet(c model.RumpConfig) model.RumpConfig {

	c.Net = &model.Net{
		If:     "wm0",
		Type:   "inet",
		Method: model.DHCP,
	}
	return c
}
