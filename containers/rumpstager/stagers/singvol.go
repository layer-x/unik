package stagers

import (
	"fmt"
	"path"

	"github.com/layer-x/unik/containers/rumpstager/device"
	"github.com/layer-x/unik/containers/rumpstager/model"
	"github.com/layer-x/unik/containers/rumpstager/utils"
)

const DefaultDeviceFilePrefix = "/dev/ld"

func init() {

	awsStager := &SingleVolumeStager{DefaultDeviceFilePrefix, "."}
	registerStager("aws", awsStager)

}

type SingleVolumeStager struct {
	DeviceFilePrefix string
	buildDir         string
}

func (s *SingleVolumeStager) Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error {

	err := s.createVolumes(volumes, &c)
	if err != nil {
		return err
	}
	return s.createRoot(kernelPath, c)
}

func (s *SingleVolumeStager) createVolumes(volumes map[string]model.Volume, c *model.RumpConfig) error {
	imgFile := path.Join(s.buildDir, "data.img")
	orderedMntPoints, err := utils.CreatePartitionedVolumes(imgFile, volumes)
	if err != nil {
		return err
	}

	fmt.Printf("image file %s\n", imgFile)

	// add mntpoints by order
	for i, mntPoint := range orderedMntPoints {

		blk := model.Blk{
			Source:     "dev",
			Path:       fmt.Sprintf(s.DeviceFilePrefix+"1%c", 'a'+i),
			FSType:     "blk",
			MountPoint: mntPoint,
		}

		c.Blk = append(c.Blk, blk)

	}
	return nil
}

func (s *SingleVolumeStager) createRoot(kernelPath string, c model.RumpConfig) error {

	imgFile := path.Join(s.buildDir, "root.img")

	if c.Net == nil || (c.Net != nil && c.Net.If == "") {
		if c.Net != nil && c.Net.Method == model.DHCP {
			c = addDhcpNet(c)
		} else {
			c = addStaticNet(c)
		}
	}

	jsonString, err := utils.ToRumpJson(c)
	if err != nil {
		return err
	}
	err = utils.CreateBootImageWithSize(imgFile, kernelPath, jsonString, device.MegaBytes(100))
	if err != nil {
		return err
	}

	return nil
}

func addDhcpNet(c model.RumpConfig) model.RumpConfig {

	c.Net = &model.Net{
		If:     "vioif0",
		Type:   "inet",
		Method: model.DHCP,
	}
	return c
}
func addStaticNet(c model.RumpConfig) model.RumpConfig {

	c.Net = &model.Net{
		If:     "vioif0",
		Type:   "inet",
		Method: "static",
		Addr:   "10.0.1.101",
		Mask:   "8",
	}

	return c
}
