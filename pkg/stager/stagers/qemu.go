package stagers

import (
	"fmt"
	"path"

	log "github.com/Sirupsen/logrus"
	"github.com/layer-x/unik/pkg/stager/device"
	"github.com/layer-x/unik/pkg/stager/model"
	"github.com/layer-x/unik/pkg/stager/utils"
	"io/ioutil"
	"errors"
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

func (s *QEmuVolumeStager) CreateDataVolume(mntPoint, deviceName, localFolder string) error {
	return errors.New("not implemented")
}

func (s *QEmuVolumeStager) CreateVolumesMulti(volumes map[string]model.Volume, c *model.RumpConfig) error {
	var i int

	for mntPoint, localFolder := range volumes {
		diskFile := fmt.Sprintf("data%02d.img", i)
		imgFile := path.Join(s.buildDir, diskFile)
		err := utils.CreateSingleVolume(imgFile, localFolder)
		if err != nil {
			return err
		}
		i++
		blk := model.Blk{
			Source:     "dev",
			Path:       fmt.Sprintf(s.DeviceFilePrefix+"%da", i),
			FSType:     "blk",
			MountPoint: mntPoint,
			DiskFile: diskFile,
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

	log.WithFields(log.Fields{"iface": c.Net.If, "args": c.Cmdline}).Debug("create boot image")

	jsonString, err := utils.ToRumpJson(c)
	if err != nil {
		return err
	}
	log.WithFields(log.Fields{"rumpconfig": c, "jsonConfig": jsonString}).Debug("about to create boot image")

	err = utils.CreateBootImageWithSize(imgFile, kernelPath, jsonString, device.MegaBytes(100))
	if err != nil {
		return err
	}
	log.WithFields(log.Fields{"rumpconfig": c, "path": s.buildDir +"rumpconfig.json"}).Debug("writing rump config json to build dir")

	return s.WriteRumpConfig(c)
}

func (s *QEmuVolumeStager) WriteRumpConfig(c model.RumpConfig) error {
	outFile := path.Join(s.buildDir, "rumpconfig.json")

	jsonString, err := utils.ToRumpJson(c)
	if err != nil {
		return err
	}
	return ioutil.WriteFile(outFile, []byte(jsonString), 0777)
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
