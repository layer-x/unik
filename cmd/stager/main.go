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
	"github.com/layer-x/unik/pkg/stager/model"
	"github.com/layer-x/unik/pkg/stager/stagers"
	"github.com/layer-x/unik/pkg/stager/utils"
)

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

type Mode string

func (m Mode) String() string {
	if _, ok := stagers.Stagers[string(m)]; !ok {
		return string(m)
	}

	return ""
}

// The second method is Set(value string) error
func (m *Mode) Set(value string) error {

	if _, ok := stagers.Stagers[value]; !ok {
		return errors.New("not a valid type")
	}

	*m = Mode(value)
	return nil
}

func getModes() string {
	keys := make([]string, 0, len(stagers.Stagers))
	for k := range stagers.Stagers {
		keys = append(keys, k)
	}

	return strings.Join(keys, ", ")

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
	buildcontextdir := flag.String("d", "/unikernel", "build context. relative volume names are relative to that")
	programName := flag.String("p", "program.bin", "unikernel to build to the image")
	appName := flag.String("a", "newapp", "new app name to register (in aws)")
	//	network := flag.String("net", "dhcp", "net type")
	var mode Mode = "single"
	flag.Var(&mode, "mode", getModes())

	dataVolumeMode := flag.Bool("datamode", false, "use this flag to stage a single volume for mounting to an existing unikernel image")
	dataVolumeLocalFolder := flag.String("datadir", "/datadir", "path to local folder where data will be copied from")
	dataVolumeMountPoint := flag.String("datamountpoint", "", "filename the unikernel is expecting this volume to be mounted to")
	dataVolumeDeviceName := flag.String("datadevicename", "", "device name (for mapping between device and mount point)")

	flag.Parse()

	//	DeviceFilePrefix := DefaultDeviceFilePrefix
	// 	DeviceFilePrefix = "/dev/sd"

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

	os.Chdir(*buildcontextdir)

	if stager, ok := stagers.Stagers[string(mode)]; ok {
		if *dataVolumeMode {
			err := stager.CreateDataVolume(*dataVolumeMountPoint, *dataVolumeDeviceName, *dataVolumeLocalFolder)
			if err != nil {
				panic("failed to create data volume: "+err.Error())
			}
		} else {
			stager.Stage(*appName, *programName, conf.Volumes, c)
		}
	} else {
		log.Panic("No stager!!")

	}

}
