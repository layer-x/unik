package main

import (
	"bufio"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"os"
	"path"
	"strconv"
	"strings"
	"text/template"

	log "github.com/Sirupsen/logrus"
	"github.com/andrew-d/go-termutil"
	"github.com/layer-x/unik/containers/rumpstager/device"
	"github.com/layer-x/unik/containers/rumpstager/model"
	"github.com/layer-x/unik/containers/rumpstager/shell"
)

const GrubTemplate = `default=0
fallback=1
timeout=1
hiddenmenu

title Unik
root {{.RootDrive}}
kernel /boot/program.bin {{.JsonConfig}}
`

const DeviceMapFile = `(hd0) {{.GrubDevice}}
`
const ProgramName = "program.bin"

func checkErr(err error) {
	if err != nil {

		if termutil.Isatty(os.Stdin.Fd()) {
			fmt.Println("Error has happened. please examine. press enter to release resources")
			bufio.NewReader(os.Stdin).ReadBytes('\n')
		}
		log.WithError(err).Panic("Failed in script!")
	}
}

func createSparseFile(filename string, size device.DiskSize) error {
	fd, err := os.Create(filename)
	if err != nil {
		checkErr(err)
	}
	defer fd.Close()

	_, err = fd.Seek(int64(size.ToBytes())-1, 0)
	if err != nil {
		checkErr(err)
	}
	_, err = fd.Write([]byte{0})
	if err != nil {
		checkErr(err)
	}
	return nil
}

func createBootImage(rootFile, progPath, jsonConfig string) error {

	return createBootImageWithSize(rootFile, device.GigaBytes(1), progPath, jsonConfig)
}

func createBootImageWithSize(rootFile string, size device.DiskSize, progPath, jsonConfig string) error {
	// add 10 mb for boot related stuff and align wit secotrs.

	finalSize := size.ToBytes() + device.MegaBytes(10).ToBytes()

	finalSize = finalSize - (finalSize % device.SectorSize)

	err := createSparseFile(rootFile, finalSize)
	if err != nil {
		checkErr(err)
	}
	return createBootImageOnFile(rootFile, device.Bytes(finalSize), progPath, jsonConfig)
}

func createBootImageOnFile(imgFile string, size device.DiskSize, progPath, jsonConfig string) error {

	rootLo := device.NewLoDevice(imgFile)
	rootLodName, err := rootLo.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer rootLo.Release()

	return createBootImageOnBlockDevice(rootLodName, size, progPath, jsonConfig)
}

func createBootImageOnBlockDevice(deviceName device.BlockDevice, size device.DiskSize, progPath, jsonConfig string) error {

	sizeInSectors, err := device.ToSectors(size)
	if err != nil {
		checkErr(err)
	}

	grubDiskName := "hda"
	rootBlkDev := device.NewDevice(0, sizeInSectors, deviceName, grubDiskName)
	rootDevice, err := rootBlkDev.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer rootBlkDev.Release()

	p := &device.MsDosPartioner{rootDevice.Name()}
	p.MakeTable()
	p.MakePart("primary", device.MegaBytes(2), device.MegaBytes(100))
	parts, err := device.ListParts(rootDevice)

	if err != nil {
		checkErr(err)
	}

	if len(parts) < 1 {
		log.Panic("No parts created")
	}

	part := parts[0]
	if dmPart, ok := part.(*device.DeviceMapperDevice); ok {
		dmPart.DeviceName = grubDiskName + "1"
	}

	// get the block device
	bootDevice, err := part.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer part.Release()
	bootLabel := "boot"
	// format the device and mount and copy
	err = shell.RunLogCommand("mkfs", "-L", bootLabel, "-I", "128", "-t", "ext2", bootDevice.Name())
	if err != nil {
		checkErr(err)
	}

	mntPoint, err := device.Mount(bootDevice)
	if err != nil {
		checkErr(err)
	}
	defer device.Umount(mntPoint)

	grubPath := path.Join(mntPoint, "boot", "grub")
	os.MkdirAll(grubPath, 0777)

	// copy program.bin.. skip that for now
	kernelDst := path.Join(mntPoint, "boot", ProgramName)
	log.WithFields(log.Fields{"src": progPath, "dst": kernelDst}).Debug("copying file")
	err = shell.CopyFile(progPath, kernelDst)
	if err != nil {
		checkErr(err)
	}

	err = writeBootTemplate(path.Join(grubPath, "menu.lst"), "(hd0,0)", jsonConfig)
	if err != nil {
		checkErr(err)
	}

	err = writeBootTemplate(path.Join(grubPath, "grub.conf"), "(hd0,0)", jsonConfig)
	if err != nil {
		checkErr(err)
	}

	err = writeDeviceMap(path.Join(grubPath, "device.map"), rootDevice.Name())
	if err != nil {
		checkErr(err)
	}

	err = shell.RunLogCommand("grub-install", "--no-floppy", "--root-directory="+mntPoint, rootDevice.Name())
	if err != nil {
		checkErr(err)
	}
	return nil
}

func writeDeviceMap(fname, rootDevice string) error {
	f, err := os.Create(fname)
	if err != nil {
		checkErr(err)
	}
	defer f.Close()

	t := template.Must(template.New("devicemap").Parse(DeviceMapFile))

	log.WithFields(log.Fields{"device": rootDevice, "file": fname}).Debug("Writing device map")
	t.Execute(f, struct {
		GrubDevice string
	}{rootDevice})

	return nil
}
func writeBootTemplate(fname, rootDrive, jsonConfig string) error {
	f, err := os.Create(fname)
	if err != nil {
		checkErr(err)
	}
	defer f.Close()

	t := template.Must(template.New("grub").Parse(GrubTemplate))

	t.Execute(f, struct {
		RootDrive  string
		JsonConfig string
	}{rootDrive, jsonConfig})

	return nil

}

type volumemap map[string]Volume

func (m volumemap) String() string {

	return fmt.Sprintf("%v", (map[string]Volume)(m))
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
	m[mntpoint] = Volume{values[0], size, name}

	return nil
}

func formatDeviceAndCopyContents(folder string, dev device.BlockDevice) {
	err := shell.RunLogCommand("mkfs", "-I", "128", "-t", "ext2", dev.Name())
	if err != nil {
		checkErr(err)
	}

	mntPoint, err := device.Mount(dev)
	if err != nil {
		checkErr(err)
	}
	defer device.Umount(mntPoint)

	shell.CopyDir(folder, mntPoint)

}

func getTempImageFile() string {
	return "/tmp/root"
}

func createSingleVolume(rootFile string, folder Volume) error {
	ext2Overhead := device.MegaBytes(2).ToBytes()
	size, err := shell.GetDirSize(folder.Path)
	checkErr(err)

	// take a spare sizde and down to sector size
	size = (device.SectorSize + size + size/10 + int64(ext2Overhead))
	size &^= (device.SectorSize - 1)
	// 10% buffer.. aligned to 512
	sizeVolume := device.Bytes(size)
	_, err = device.ToSectors(device.Bytes(size))
	if err != nil {
		checkErr(err)
	}
	err = createSparseFile(rootFile, sizeVolume)
	if err != nil {
		checkErr(err)
	}

	return copyToImgFile(folder.Path, rootFile)
}

func copyToImgFile(folder, imgfile string) error {

	imgLo := device.NewLoDevice(imgfile)
	imgLodName, err := imgLo.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer imgLo.Release()

	formatDeviceAndCopyContents(folder, imgLodName)

	return nil
}

func copyToPart(folder string, part device.Part) error {

	imgLodName, err := part.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer part.Release()
	formatDeviceAndCopyContents(folder, imgLodName)

	return nil
}

func createPartitionedVolumes(imgFile string, volums map[string]Volume) ([]string, error) {
	sizes := make(map[string]device.Bytes)
	var orderedKeys []string
	var totalSize device.Bytes

	ext2Overhead := device.MegaBytes(2).ToBytes()
	firstPartFffest := device.MegaBytes(2).ToBytes()

	for mntPoint, localDir := range volums {
		cursize, err := shell.GetDirSize(localDir.Path)
		if err != nil {
			checkErr(err)
		}
		sizes[mntPoint] = device.Bytes(cursize) + ext2Overhead
		totalSize += sizes[mntPoint]
		orderedKeys = append(orderedKeys, mntPoint)
	}
	sizeVolume := device.Bytes((device.SectorSize + totalSize + totalSize/10) &^ (device.SectorSize - 1))
	sizeVolume += device.MegaBytes(4).ToBytes()

	log.WithFields(log.Fields{"imgFile": imgFile, "size": sizeVolume.ToPartedFormat()}).Debug("Creating image file")
	err := createSparseFile(imgFile, sizeVolume)
	if err != nil {
		checkErr(err)
	}

	imgLo := device.NewLoDevice(imgFile)
	imgLodName, err := imgLo.Acquire()
	if err != nil {
		checkErr(err)
	}
	defer imgLo.Release()

	p := &device.DiskLabelPartioner{imgLodName.Name()}

	p.MakeTable()
	var start device.Bytes = firstPartFffest
	for _, mntPoint := range orderedKeys {
		end := start + sizes[mntPoint]
		log.WithFields(log.Fields{"start": start, "end": end}).Debug("Creating partition")
		err := p.MakePart("ext2", start, end)
		checkErr(err)
		curParts, err := device.ListParts(imgLodName)
		checkErr(err)
		start = curParts[len(curParts)-1].Offset().ToBytes() + curParts[len(curParts)-1].Size().ToBytes()
	}

	parts, err := device.ListParts(imgLodName)

	log.WithFields(log.Fields{"parts": parts, "volsize": sizes}).Debug("Creating volumes")
	for i, mntPoint := range orderedKeys {
		localDir := volums[mntPoint].Path

		copyToPart(localDir, parts[i])
	}

	return orderedKeys, nil
}

func toRumpJson(c model.RumpConfig) string {

	blk := c.Blk
	c.Blk = nil

	jsonConfig, err := json.Marshal(c)
	checkErr(err)

	blks := ""
	for _, b := range blk {

		blkjson, err := json.Marshal(b)
		checkErr(err)
		blks += fmt.Sprintf("\"blk\": %s,", string(blkjson))
	}
	var jsonString string
	if len(blks) > 0 {

		jsonString = string(jsonConfig[:len(jsonConfig)-1]) + "," + blks[:len(blks)-1] + "}"

	} else {
		jsonString = string(jsonConfig)
	}

	return jsonString

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

type Volume struct {
	Path string
	Size int64
	Name string
}

// while this looks like a go program
// it is actually a sophisticated bash script
func main() {

	log.SetLevel(log.DebugLevel)

	var conf struct {
		Volumes map[string]Volume
		Cmdline string
	}

	conf.Volumes = make(map[string]Volume)
	flag.Var(volumemap(conf.Volumes), "v", "volumes localdir:remotedir")
	flag.StringVar(&conf.Cmdline, "args", "", "arguments for kernel")
	dryrun := flag.Bool("n", false, "dry run - dont do anything")
	buildcontextdir := flag.String("d", "/unikernel", "build context. relative volume names are relative to that")
	programName := flag.String("p", "program.bin", "unikernel to build to the image")
	appName := flag.String("a", "newapp", "new app name to register (in aws)")
	var mode Mode = Single
	flag.Var(&mode, "m", "mode: single,multi,aws")

	flag.Parse()

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
		c.Cmdline = ProgramName
	} else {
		c.Cmdline = ProgramName + " " + c.Cmdline
	}

	var orderedMntPoints []string
	if !*dryrun {
		switch mode {
		case Single:
			imgFile := path.Join(*buildcontextdir, "data.img")
			orderedMntPoints, _ = createPartitionedVolumes(imgFile, conf.Volumes)
			fmt.Printf("image file %s\n", imgFile)

			// add mntpoints by order
			for i, mntPoint := range orderedMntPoints {

				blk := model.Blk{
					Source:     "dev",
					Path:       fmt.Sprintf("/dev/ld1%c", 'a'+i),
					FSType:     "blk",
					MountPoint: mntPoint,
				}

				c.Blk = append(c.Blk, blk)

			}
		case Multi:
			var i int

			for mntPoint, localFolder := range conf.Volumes {

				imgFile := path.Join(*buildcontextdir, fmt.Sprintf("data%02d.img", i))
				err := createSingleVolume(imgFile, localFolder)
				checkErr(err)

				i++
				blk := model.Blk{
					Source:     "dev",
					Path:       fmt.Sprintf("/dev/ld%da", 1+i),
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

			size, err := shell.GetDirSize(imgFile)
			checkErr(err)

			err = createBootImageWithSize(imgFile, device.Bytes(size), *programName, toRumpJson(addStaticNet(c)))
			if err != nil {
				log.Fatal(err)
			}
			fmt.Printf("image file %s\n", imgFile)
		}
	}

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
