package main

import (
	"bufio"
	"errors"
	"flag"
	"fmt"
	"os"
	"path"
	"strconv"
	"strings"

	log "github.com/Sirupsen/logrus"
	"github.com/andrew-d/go-termutil"
	"github.com/layer-x/unik/containers/rumpstager/device"
	"github.com/layer-x/unik/containers/rumpstager/model"
	_ "github.com/layer-x/unik/containers/rumpstager/stagers"
	"github.com/layer-x/unik/containers/rumpstager/utils"
)

const DefaultDeviceFilePrefix = "/dev/ld"

func checkErr(err error) {
	if err != nil {

		if termutil.Isatty(os.Stdin.Fd()) {
			fmt.Println("Error has happened. please examine. press enter to release resources")
			bufio.NewReader(os.Stdin).ReadBytes('\n')
		}
		log.WithError(err).Panic("Failed in script!")
	}
}

type volumemap map[string]model.Volume

func (m volumemap) String() string {

	return fmt.Sprintf("%v", (map[string]model.Volume)(m))
}

// The second method is Set(value string) error
func (m volumemap) Set(value string) error {
	values := strings.Split(value, ":")
	if len(values) != 2 {
		return errors.New("Bad volume syntax")
	}

	if _, ok := m[values[1]]; ok {
		return errors.New("Can't define two volums on the same mountpount")
	}

	mntpoint := values[1]
	var size int64
	name := ""

	volparts := strings.Split(values[1], ",")

	if len(volparts) >= 1 {
		mntpoint = volparts[0]
	}
	if len(volparts) >= 2 {
		size, _ = strconv.ParseInt(volparts[1], 0, 64)
	}
	if len(volparts) >= 3 {
		name = volparts[2]
	}
	m[mntpoint] = model.Volume{values[0], size, name}

	return nil
}

type Mode int

const (
	Single Mode = iota
	Multi
	AWS
)

func (m Mode) String() string {
	switch m {
	case Single:
		return "single"
	case Multi:
		return "multi"
	case AWS:
		return "aws"
	}
	return "nil"
}

// The second method is Set(value string) error
func (m *Mode) Set(value string) error {
	switch value {
	case Single.String():
		*m = Single
		return nil
	case Multi.String():
		*m = Multi
		return nil
	case AWS.String():
		*m = AWS
		return nil
	}

	return errors.New("not a valid type")
}

// while this looks like a go program
// it is actually a sophisticated bash script
func main() {

	log.SetLevel(log.DebugLevel)

	var conf struct {
		Volumes map[string]model.Volume
		Cmdline string
	}

	conf.Volumes = make(map[string]model.Volume)
	flag.Var(volumemap(conf.Volumes), "v", "volumes localdir:remotedir")
	flag.StringVar(&conf.Cmdline, "args", "", "arguments for kernel")
	dryrun := flag.Bool("n", false, "dry run - dont do anything")
	buildcontextdir := flag.String("d", "/unikernel", "build context. relative volume names are relative to that")
	programName := flag.String("p", "program.bin", "unikernel to build to the image")
	appName := flag.String("a", "newapp", "new app name to register (in aws)")
	network := flag.String("net", "dhcp", "net type")
	var mode Mode = Single
	flag.Var(&mode, "m", "mode: single,multi,aws")

	flag.Parse()

	DeviceFilePrefix := DefaultDeviceFilePrefix
	DeviceFilePrefix = "/dev/sd"
	// fix relative names
	if !path.IsAbs(*programName) {
		*programName = path.Join(*buildcontextdir, *programName)
	}

	for mntPoint, volumeDir := range conf.Volumes {
		if !path.IsAbs(volumeDir.Path) {
			volumeDir.Path = path.Join(*buildcontextdir, volumeDir.Path)
			conf.Volumes[mntPoint] = volumeDir
		}
		if !path.IsAbs(mntPoint) {
			log.Fatal(mntPoint + " must be absolute path")
		}
	}

	var c model.RumpConfig
	c.Cmdline = conf.Cmdline
	if c.Cmdline == "" {
		c.Cmdline = utils.ProgramName
	} else {
		c.Cmdline = utils.ProgramName + " " + c.Cmdline
	}

	var orderedMntPoints []string
	if !*dryrun {
		switch mode {
		case Single:
			imgFile := path.Join(*buildcontextdir, "data.img")
			orderedMntPoints, _ = utils.CreatePartitionedVolumes(imgFile, conf.Volumes)
			fmt.Printf("image file %s\n", imgFile)

			// add mntpoints by order
			for i, mntPoint := range orderedMntPoints {

				blk := model.Blk{
					Source:     "dev",
					Path:       fmt.Sprintf(DeviceFilePrefix+"1%c", 'a'+i),
					FSType:     "blk",
					MountPoint: mntPoint,
				}

				c.Blk = append(c.Blk, blk)

			}
		case Multi:
			var i int

			for mntPoint, localFolder := range conf.Volumes {

				imgFile := path.Join(*buildcontextdir, fmt.Sprintf("data%02d.img", i))
				err := utils.CreateSingleVolume(imgFile, localFolder)
				checkErr(err)

				i++
				blk := model.Blk{
					Source:     "dev",
					Path:       fmt.Sprintf(DeviceFilePrefix+"%da", 1+i),
					FSType:     "blk",
					MountPoint: mntPoint,
				}

				c.Blk = append(c.Blk, blk)
				fmt.Printf("image file %s\n", imgFile)

			}

		case AWS:

			if ec2svc == nil {
				log.Fatal("No AWS!")
			}
			stage_aws(*appName, *programName, conf.Volumes, c)
		}
	} else {

		for mntPoint := range conf.Volumes {
			orderedMntPoints = append(orderedMntPoints, mntPoint)
		}
	}

	fmt.Printf("volums %v\njson config: %s\n", conf.Volumes, toRumpJson(c))

	if !*dryrun {
		if mode != AWS {

			imgFile := path.Join(*buildcontextdir, "root.img")
			var addNet func(model.RumpConfig) model.RumpConfig

			if *network == "dhcp" {
				addNet = addVmwareNet
			} else {
				addNet = addStaticNet
			}

			err := utils.CreateBootImageWithSize(imgFile, *programName, toRumpJson(addNet(c)), device.MegaBytes(100))
			if err != nil {
				log.Fatal(err)
			}
			fmt.Printf("image file %s\n", imgFile)
		}
	}

}

func addVmwareNet(c model.RumpConfig) model.RumpConfig {

	// vmware uses e1000 crards handled by wm driver.

	c.Net = &model.Net{
		If:     "wm0",
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
