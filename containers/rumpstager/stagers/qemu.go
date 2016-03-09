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

	stager := &QEmuVolumeStager{DefaultDeviceFilePrefix, ".", true}
	registerStager("single", stager)

	stager = &QEmuVolumeStager{DefaultDeviceFilePrefix, ".", false}
	registerStager("multi", stager)

}

type QEmuVolumeStager struct {
	DeviceFilePrefix string
	buildDir         string
	single           bool
}

func (s *QEmuVolumeStager) Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error {

	if len(volumes) > 0 {
		var err error
		if s.single {
			err = s.CreateVolumesSingle(volumes, &c)
		} else {
			err = s.CreateVolumesMulti(volumes, &c)
		}
		if err != nil {
			return err
		}
	}

	return s.CreateRoot(kernelPath, c)
}

func (s *QEmuVolumeStager) CreateVolumesMulti(volumes map[string]model.Volume, c *model.RumpConfig) error {
	var i int

	for mntPoint, localFolder := range volumes {

		imgFile := path.Join(s.buildDir, fmt.Sprintf("data%02d.img", i))
		err := utils.CreateSingleVolume(imgFile, localFolder)
		if err != nil {
			return err
		}
		i++
		blk := model.Blk{
			Source:     "dev",
			Path:       fmt.Sprintf(s.DeviceFilePrefix+"%da", 1+i),
			FSType:     "blk",
			MountPoint: mntPoint,
		}

		c.Blk = append(c.Blk, blk)
		fmt.Printf("image file %s\n", imgFile)

	}
	return nil
}

func (s *QEmuVolumeStager) CreateVolumesSingle(volumes map[string]model.Volume, c *model.RumpConfig) error {
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

func (s *QEmuVolumeStager) CreateRoot(kernelPath string, c model.RumpConfig) error {

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
