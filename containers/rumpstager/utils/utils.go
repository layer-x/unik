package utils

import (
	"encoding/json"
	"errors"
	"fmt"
	"os"
	"path"
	"text/template"

	log "github.com/Sirupsen/logrus"
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

func createSparseFile(filename string, size device.DiskSize) error {
	fd, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer fd.Close()

	_, err = fd.Seek(int64(size.ToBytes())-1, 0)
	if err != nil {
		return err
	}
	_, err = fd.Write([]byte{0})
	if err != nil {
		return err
	}
	return nil
}

func CreateBootImageWithSize(rootFile, progPath, jsonConfig string, size device.DiskSize) error {
	err := createSparseFile(rootFile, size)
	if err != nil {
		return err
	}

	return CreateBootImageOnFile(rootFile, size, progPath, jsonConfig)
}

func CreateBootImageOnFile(rootFile string, sizeOfFile device.DiskSize, progPath, jsonConfig string) error {

	sizeInSectors, err := device.ToSectors(sizeOfFile)
	if err != nil {
		return err
	}

	/*	sectorsSize, err := device.ToSectors(size)
		if err != nil {
			return
		}
	*/
	rootLo := device.NewLoDevice(rootFile)
	rootLodName, err := rootLo.Acquire()
	if err != nil {
		return err
	}
	defer rootLo.Release()

	grubDiskName := "hda"
	rootBlkDev := device.NewDevice(0, sizeInSectors, rootLodName, grubDiskName)
	rootDevice, err := rootBlkDev.Acquire()
	if err != nil {
		return err
	}
	defer rootBlkDev.Release()

	p := &device.MsDosPartioner{rootDevice.Name()}
	p.MakeTable()
	p.MakePartTillEnd("primary", device.MegaBytes(2))
	parts, err := device.ListParts(rootDevice)
	if err != nil {
		return err
	}

	if len(parts) < 1 {
		return errors.New("No parts created")
	}

	part := parts[0]
	if dmPart, ok := part.(*device.DeviceMapperDevice); ok {
		dmPart.DeviceName = grubDiskName + "1"
	}

	// get the block device
	bootDevice, err := part.Acquire()
	if err != nil {
		return err
	}
	defer part.Release()
	bootLabel := "boot"
	// format the device and mount and copy
	err = shell.RunLogCommand("mkfs", "-L", bootLabel, "-I", "128", "-t", "ext2", bootDevice.Name())
	if err != nil {
		return err
	}

	mntPoint, err := device.Mount(bootDevice)
	if err != nil {
		return err
	}
	defer device.Umount(mntPoint)

	grubPath := path.Join(mntPoint, "boot", "grub")
	os.MkdirAll(grubPath, 0777)

	// copy program.bin.. skip that for now
	kernelDst := path.Join(mntPoint, "boot", ProgramName)
	log.WithFields(log.Fields{"src": progPath, "dst": kernelDst}).Debug("copying file")
	err = shell.CopyFile(progPath, kernelDst)
	if err != nil {
		return err
	}
	err = writeBootTemplate(path.Join(grubPath, "menu.lst"), "(hd0,0)", jsonConfig)
	if err != nil {
		return err
	}

	err = writeBootTemplate(path.Join(grubPath, "grub.conf"), "(hd0,0)", jsonConfig)
	if err != nil {
		return err
	}

	err = writeDeviceMap(path.Join(grubPath, "device.map"), rootDevice.Name())
	if err != nil {
		return err
	}

	err = shell.RunLogCommand("grub-install", "--no-floppy", "--root-directory="+mntPoint, rootDevice.Name())
	if err != nil {
		return err
	}
	return nil
}

func writeDeviceMap(fname, rootDevice string) error {
	f, err := os.Create(fname)
	if err != nil {
		return err
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

	log.WithFields(log.Fields{"fname": fname, "rootDrive": rootDrive, "jsonConfig": jsonConfig}).Debug("writing boot template")

	f, err := os.Create(fname)
	if err != nil {
		return err
	}
	defer f.Close()

	t := template.Must(template.New("grub").Parse(GrubTemplate))

	t.Execute(f, struct {
		RootDrive  string
		JsonConfig string
	}{rootDrive, jsonConfig})

	return nil

}

func formatDeviceAndCopyContents(folder string, dev device.BlockDevice) error {
	err := shell.RunLogCommand("mkfs", "-I", "128", "-t", "ext2", dev.Name())
	if err != nil {
		return err
	}

	mntPoint, err := device.Mount(dev)
	if err != nil {
		return err
	}
	defer device.Umount(mntPoint)

	shell.CopyDir(folder, mntPoint)
	return nil
}

func CreateSingleVolume(rootFile string, folder model.Volume) error {
	ext2Overhead := device.MegaBytes(2).ToBytes()
	size, err := shell.GetDirSize(folder.Path)
	if err != nil {
		return err
	}
	// take a spare sizde and down to sector size
	size = (device.SectorSize + size + size/10 + int64(ext2Overhead))
	size &^= (device.SectorSize - 1)
	// 10% buffer.. aligned to 512
	sizeVolume := device.Bytes(size)
	_, err = device.ToSectors(device.Bytes(size))
	if err != nil {
		return err
	}
	err = createSparseFile(rootFile, sizeVolume)
	if err != nil {
		return err
	}

	return CopyToImgFile(folder.Path, rootFile)
}

func CopyToImgFile(folder, imgfile string) error {

	imgLo := device.NewLoDevice(imgfile)
	imgLodName, err := imgLo.Acquire()
	if err != nil {
		return err
	}
	defer imgLo.Release()

	return formatDeviceAndCopyContents(folder, imgLodName)

}

func copyToPart(folder string, part device.Part) error {

	imgLodName, err := part.Acquire()
	if err != nil {
		return err
	}
	defer part.Release()
	return formatDeviceAndCopyContents(folder, imgLodName)

}

func CreatePartitionedVolumes(imgFile string, volums map[string]model.Volume) ([]string, error) {
	sizes := make(map[string]device.Bytes)
	var orderedKeys []string
	var totalSize device.Bytes

	ext2Overhead := device.MegaBytes(2).ToBytes()
	firstPartFffest := device.MegaBytes(2).ToBytes()

	for mntPoint, localDir := range volums {
		cursize, err := shell.GetDirSize(localDir.Path)
		if err != nil {
			return nil, err
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
		return nil, err
	}

	imgLo := device.NewLoDevice(imgFile)
	imgLodName, err := imgLo.Acquire()
	if err != nil {
		return nil, err
	}
	defer imgLo.Release()

	p := &device.DiskLabelPartioner{imgLodName.Name()}

	p.MakeTable()
	var start device.Bytes = firstPartFffest
	for _, mntPoint := range orderedKeys {
		end := start + sizes[mntPoint]
		log.WithFields(log.Fields{"start": start, "end": end}).Debug("Creating partition")
		err := p.MakePart("ext2", start, end)
		if err != nil {
			return nil, err
		}
		curParts, err := device.ListParts(imgLodName)
		if err != nil {
			return nil, err
		}
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

func ToRumpJson(c model.RumpConfig) (string, error) {

	blk := c.Blk
	c.Blk = nil

	jsonConfig, err := json.Marshal(c)
	if err != nil {
		return "", err
	}

	blks := ""
	for _, b := range blk {

		blkjson, err := json.Marshal(b)
		if err != nil {
			return "", err
		}
		blks += fmt.Sprintf("\"blk\": %s,", string(blkjson))
	}
	var jsonString string
	if len(blks) > 0 {

		jsonString = string(jsonConfig[:len(jsonConfig)-1]) + "," + blks[:len(blks)-1] + "}"

	} else {
		jsonString = string(jsonConfig)
	}

	return jsonString, nil

}
