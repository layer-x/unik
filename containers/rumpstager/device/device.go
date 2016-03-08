package device

import (
	"bufio"
	"bytes"
	"errors"
	"fmt"
	"io/ioutil"
	"math/rand"
	"os"
	"os/exec"
	"strconv"
	"strings"

	log "github.com/Sirupsen/logrus"
	"github.com/layer-x/unik/containers/rumpstager/shell"
)

type DiskSize interface {
	ToPartedFormat() string
	ToBytes() Bytes
}

type Bytes int64

func (s Bytes) ToPartedFormat() string {
	return fmt.Sprintf("%dB", uint64(s))
}

func (s Bytes) ToBytes() Bytes {
	return s
}

type MegaBytes int64

func (s MegaBytes) ToPartedFormat() string {
	return fmt.Sprintf("%dMiB", uint64(s))
}

func (s MegaBytes) ToBytes() Bytes {
	return Bytes(s << 20)
}

type GigaBytes int64

func (s GigaBytes) ToPartedFormat() string {
	return fmt.Sprintf("%dGiB", uint64(s))
}

func (s GigaBytes) ToBytes() Bytes {
	return Bytes(s << 30)
}

type Sectors int64

const SectorSize = 512

func (s Sectors) ToPartedFormat() string {
	return fmt.Sprintf("%ds", uint64(s))
}

func (s Sectors) ToBytes() Bytes {
	return Bytes(s * SectorSize)
}

func ToSectors(b DiskSize) (Sectors, error) {
	inBytes := b.ToBytes()
	if inBytes%SectorSize != 0 {
		return 0, errors.New("can't convert to sectors")
	}
	return Sectors(inBytes / SectorSize), nil
}

type BlockDevice string

func (b BlockDevice) Name() string {
	return string(b)
}

func Mount(device BlockDevice) (mntpoint string, err error) {
	defer func() {
		if err != nil {
			os.Remove(mntpoint)
		}
	}()

	mntpoint, err = ioutil.TempDir("", "stgr")
	if err != nil {
		return
	}
	err = shell.RunLogCommand("mount", device.Name(), mntpoint)
	return
}

func Umount(point string) error {

	err := shell.RunLogCommand("umount", point)
	if err != nil {
		return err
	}
	// ignore errors.
	err = os.Remove(point)
	if err != nil {
		log.WithField("err", err).Warn("umount rmdir failed")
	}

	return nil
}

type Partitioner interface {
	MakeTable() error
	MakePart(partType string, start, size DiskSize) error
}

type Resource interface {
	Acquire() (BlockDevice, error)
	Release() error
}

type Part interface {
	Resource

	Size() DiskSize
	Offset() DiskSize

	Get() BlockDevice
}

func runParted(device string, args ...string) ([]byte, error) {
	log.WithFields(log.Fields{"device": device, "args": args}).Debug("running parted")
	args = append([]string{"--script", "--machine", device}, args...)
	out, err := exec.Command("parted", args...).CombinedOutput()
	if err != nil {
		log.WithFields(log.Fields{"args": args, "err": err, "out": string(out)}).Error("parted failed")
	}
	return out, err
}

type MsDosPartioner struct {
	Device string
}

func (m *MsDosPartioner) MakeTable() error {
	_, err := runParted(m.Device, "mklabel", "msdos")
	return err
}

func (m *MsDosPartioner) MakePart(partType string, start, size DiskSize) error {
	_, err := runParted(m.Device, "mkpart", partType, start.ToPartedFormat(), size.ToPartedFormat())
	return err
}

type DiskLabelPartioner struct {
	Device string
}

func (m *DiskLabelPartioner) MakeTable() error {
	_, err := runParted(m.Device, "mklabel", "bsd")
	return err
}

func (m *DiskLabelPartioner) MakePart(partType string, start, size DiskSize) error {
	_, err := runParted(m.Device, "mkpart", partType, start.ToPartedFormat(), size.ToPartedFormat())
	return err
}

func ListParts(device BlockDevice) ([]Part, error) {
	var parts []Part
	out, err := runParted(device.Name(), "unit B", "print")
	if err != nil {
		return parts, nil
	}
	scanner := bufio.NewScanner(bytes.NewReader(out))
	/* example output

	  BYT;
	  /dev/xvda:42949672960B:xvd:512:512:msdos:Xen Virtual Block Device;
	  1:8225280B:42944186879B:42935961600B:ext4::boot;

	  ================

	  BYT;
	  /home/ubuntu/yuval:1073741824B:file:512:512:bsd:;
	  1:2097152B:99614719B:97517568B:::;
	  2:99614720B:200278015B:100663296B:::;
	  3:200278016B:299892735B:99614720B:::;

	========= basically:
	device:size:
	partnum:start:end:size

	*/

	// skip to the parts..
	for scanner.Scan() {
		line := scanner.Text()
		if strings.Contains(line, device.Name()) {
			break
		}
	}

	for scanner.Scan() {
		line := scanner.Text()
		tokens := strings.Split(line, ":")

		partNum, err := strconv.ParseInt(tokens[0], 0, 0)
		if err != nil {
			return parts, err
		}

		start, err := getByteNumber(tokens[1])
		if err != nil {
			return parts, err
		}

		end, err := getByteNumber(tokens[2])
		if err != nil {
			return parts, err
		}

		size, err := getByteNumber(tokens[3])
		if err != nil {
			return parts, err
		}

		//validate Part is consistent:
		if end-start != size-1 {
			log.WithFields(log.Fields{"start": start, "end": end, "size": size}).Error("Sizes not consistent")
			return parts, errors.New("Sizes are inconsistent. part not continous?")
		}

		var part Part
		partName := getDevicePart(device.Name(), partNum)
		if _, err := os.Stat(partName); os.IsNotExist(err) {
			// device does not exist
			sectorsStart, err := ToSectors(start)
			if err != nil {
				return parts, err
			}
			sectorsSize, err := ToSectors(size)
			if err != nil {
				return parts, err
			}
			part = NewDMPartedPart(sectorsStart, sectorsSize, device, partNum)
		} else {
			// device exists
			var release func(BlockDevice) error = nil

			// prated might have created the mapping for us. unfortunatly it does not remove it...
			if strings.HasPrefix(partName, "/dev/mapper") {
				release = func(d BlockDevice) error {
					return shell.RunLogCommand("dmsetup", "remove", d.Name())
				}
			}

			part = &PartedPart{BlockDevice(partName), start, size, release}
		}
		parts = append(parts, part)
	}

	return parts, nil
}

func getDevicePart(device string, part int64) string {
	return fmt.Sprintf("%s%c", device, '0'+part)
}

func getByteNumber(token string) (Bytes, error) {
	tokenLen := len(token)
	if tokenLen == 0 {
		return 0, errors.New("Not a number")
	}
	// remove the B

	if token[tokenLen-1] != 'B' {

		return 0, errors.New("Unknown unit for number")
	}

	res, err := strconv.ParseInt(token[:tokenLen-1], 0, 0)
	return Bytes(res), err
}

type PartedPartitioner struct {
	Name string
}

func (p *PartedPartitioner) MakeTable() error {
	return nil
}

func (p *PartedPartitioner) MakePart(partType string, start, size DiskSize) error {
	return nil
}

type PartedPart struct {
	Device  BlockDevice
	offset  DiskSize
	size    DiskSize
	release func(BlockDevice) error
}

func (p *PartedPart) Size() DiskSize {
	return p.size
}
func (p *PartedPart) Offset() DiskSize {
	return p.offset
}

func (p *PartedPart) Acquire() (BlockDevice, error) {

	return p.Get(), nil
}

func (p *PartedPart) Release() error {
	if p.release != nil {
		return p.release(p.Device)
	}
	return nil
}

func (p *PartedPart) Get() BlockDevice {
	return p.Device
}

type DeviceMapperDevice struct {
	DeviceName string

	start Sectors
	size  Sectors

	orginalDevice BlockDevice
}

//http://stackoverflow.com/questions/22892120/how-to-generate-a-random-string-of-a-fixed-length-in-golang
const letterBytes = "abcdefghijklmnopqrstuvwxyz"

func RandStringBytes(n int) string {
	b := make([]byte, n)
	for i := range b {
		b[i] = letterBytes[rand.Intn(len(letterBytes))]
	}
	return string(b)
}

func randomDeviceName() string {
	return "dev" + RandStringBytes(4)
}

// Device device name is generated, user can chagne it..
func NewDMPartedPart(start, size Sectors, device BlockDevice, partNum int64) Part {
	name := randomDeviceName()
	newDeviceName := fmt.Sprintf("%s%c", name, '0'+partNum)
	return &DeviceMapperDevice{newDeviceName, start, size, device}
}

func NewDevice(start, size Sectors, origDevice BlockDevice, deivceName string) Resource {
	return &DeviceMapperDevice{deivceName, start, size, origDevice}
}

func (p *DeviceMapperDevice) Size() DiskSize {
	return p.size
}
func (p *DeviceMapperDevice) Offset() DiskSize {
	return p.start
}

func (p *DeviceMapperDevice) Acquire() (BlockDevice, error) {
	// dmsetup create partition${PARTI} --table "0 $SIZE linear $DEVICE $SECTOR"
	table := fmt.Sprintf("0 %d linear %s %d", p.size, p.orginalDevice, p.start)

	err := shell.RunLogCommand("dmsetup", "create", p.DeviceName, "--table", table)

	if err == nil && !IsExists(p.Get().Name()) {
		err = shell.RunLogCommand("dmsetup", "mknodes", p.DeviceName)
	}

	return p.Get(), err
}

func (p *DeviceMapperDevice) Release() error {
	err := shell.RunLogCommand("dmsetup", "remove", p.DeviceName)
	if err == nil && IsExists(p.Get().Name()) {
		err = os.Remove(p.Get().Name())
	}
	return err

}

func (p *DeviceMapperDevice) Get() BlockDevice {
	newDevice := "/dev/mapper/" + p.DeviceName
	return BlockDevice(newDevice)
}

type LoDevice struct {
	device        string
	createdDevice BlockDevice
}

func NewLoDevice(device string) Resource {
	return &LoDevice{device, BlockDevice("")}
}

func (p *LoDevice) Acquire() (BlockDevice, error) {
	// dmsetup create partition${PARTI} --table "0 $SIZE linear $DEVICE $SECTOR"
	log.WithFields(log.Fields{"cmd": "losetup", "device": p.device}).Debug("running losetup -f")

	out, err := exec.Command("losetup", "-f", "--show", p.device).CombinedOutput()

	if err != nil {
		log.WithFields(log.Fields{"cmd": "losetup", "out": string(out), "device": p.device}).Debug("losetup -f failed")

		return BlockDevice(""), err
	}
	outString := strings.TrimSpace(string(out))
	p.createdDevice = BlockDevice(outString)
	return p.createdDevice, nil
}

func (p *LoDevice) Release() error {
	return shell.RunLogCommand("losetup", "-d", p.createdDevice.Name())
}

func IsExists(f string) bool {
	_, err := os.Stat(f)
	return !os.IsNotExist(err)
}
