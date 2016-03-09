package stagers

import (
	"errors"
	"fmt"
	"sync"
	"time"

	log "github.com/Sirupsen/logrus"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/ec2metadata"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/containers/rumpstager/device"
	"github.com/layer-x/unik/containers/rumpstager/model"
	"github.com/layer-x/unik/containers/rumpstager/utils"
)

func init() {
	var awsSession = session.New()
	var meta = ec2metadata.New(awsSession)
	var ec2svc *ec2.EC2

	region, err := meta.Region()
	if err == nil {
		ec2svc = ec2.New(awsSession, &aws.Config{Region: aws.String(region)})

		awsStager := &AWSStager{awsSession, meta, ec2svc}
		registerStager("aws", awsStager)
	} else {
		log.Debug("No AWS")
	}

}

type AWSStager struct {
	AWSSession *session.Session
	meta       *ec2metadata.EC2Metadata
	ec2svc     *ec2.EC2
}

const SizeInGigs = 1

func updateConfig(volumes map[string]model.Volume, c model.RumpConfig) (model.RumpConfig, map[string]string) {
	var volIndex int
	mountToDevice := make(map[string]string)

	for mntPoint := range volumes {
		// start from sdb; sda is for root.
		volIndex++
		deviceMapped := fmt.Sprintf("sd%c1", 'a'+volIndex)
		blk := model.Blk{
			Source:     "etfs",
			Path:       deviceMapped,
			FSType:     "blk",
			MountPoint: mntPoint,
		}
		mountToDevice[mntPoint] = "/dev/" + deviceMapped

		c.Blk = append(c.Blk, blk)
	}
	return c, mountToDevice

}

type VolToDevice struct {
	MntPoint    string
	BlockDevice ec2.EbsBlockDevice
}

const AWDRootDevice = "/dev/sda1"

func (s *AWSStager) Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error {

	// to avoid uploads that might take time we create the image by attaching aws volumes

	deviceToSnapId := make(map[string]ec2.EbsBlockDevice)

	// update config with the voumes. this is also assigns aws block devices
	c, mountToDevice := updateConfig(volumes, c)
	// add the root to the device map
	mountToDevice["/"] = AWDRootDevice
	c = addAwsNet(c)

	results, err := s.createVolumes(kernelPath, volumes, c)
	if err != nil {
		return err
	}
	// convert the point points to the block devices created during configuration
	for res := range results {
		log.WithFields(log.Fields{"snap": res.BlockDevice.SnapshotId, "mntPoint": res.MntPoint, "dev": mountToDevice[res.MntPoint]}).Debug("Adding result to map")
		deviceToSnapId[mountToDevice[res.MntPoint]] = res.BlockDevice
	}

	if len(deviceToSnapId) != (len(mountToDevice)) {
		log.WithFields(log.Fields{"deviceToSnapId": deviceToSnapId, "mountToDevice": mountToDevice}).Error("Not all volumes created")
		return errors.New("Not all volumes created for AWS")
	}

	/// all should be ready for aws
	s.registerImage(appName, deviceToSnapId)
	return nil
}

func (s *AWSStager) createVolumes(kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) (<-chan VolToDevice, error) {

	jsonString, err := utils.ToRumpJson(c)
	if err != nil {
		return nil, err
	}

	results := make(chan VolToDevice)

	// going to build in parallel. these are the devices we can use:
	// we could potentially add more (according to aws documentation) if two is not fast enough
	allDevices := []string{"/dev/xvdf", "/dev/xvdg"}

	// add them all the to the available channel, as no one uses them now.
	availableDevices := make(chan string, len(allDevices))
	for _, d := range allDevices {
		availableDevices <- d
	}

	// wait group for all the volume creation workers.
	var wg sync.WaitGroup
	wg.Add(1)
	go func() {
		defer wg.Done()
		imgFile := <-availableDevices
		defer func() { availableDevices <- imgFile }()

		snapshot, err := s.workOnVolume(imgFile, func(imgFile string) error {
			return utils.CreateBootImageOnFile(imgFile, device.GigaBytes(SizeInGigs), kernelPath, jsonString)
		})

		if err != nil {
			log.WithField("err", err).Error("Failed  CreateBootImageOnFile")
			return
		}
		results <- VolToDevice{"/", ec2.EbsBlockDevice{SnapshotId: snapshot.SnapshotId}}
	}()

	// go over the volumes and created them.
	for mntPoint, localFolder := range volumes {

		if localFolder.Path != "" {
			wg.Add(1)
			go func(mntPoint string, localFolder model.Volume) {

				defer wg.Done()
				dev := <-availableDevices
				defer func() { availableDevices <- dev }()

				snap, err := s.copyToAws(dev, localFolder)
				if err != nil {
					log.WithField("err", err).Error("Failed creating a volume")
					return
				}
				results <- VolToDevice{mntPoint, ec2.EbsBlockDevice{SnapshotId: snap.SnapshotId}}

			}(mntPoint, localFolder)
		} else {
			go func() {
				results <- VolToDevice{mntPoint, ec2.EbsBlockDevice{VolumeSize: aws.Int64(localFolder.Size)}}
			}()
		}

	}

	// after work is done, close the results channel.
	go func() {
		wg.Wait()
		close(results)
	}()

	return results, nil
}

func addAwsNet(c model.RumpConfig) model.RumpConfig {

	c.Net = &model.Net{
		If:     "xenif0",
		Cloner: "true",
		Type:   "inet",
		Method: model.DHCP,
	}

	return c
}

func (s *AWSStager) workOnVolume(deviceFile string, workFunc func(string) error) (*ec2.Snapshot, error) {

	vol, err := s.getAwsVolume()
	if err != nil {
		return nil, err
	}

	err = func() error {
		err := s.attachVol(*vol, deviceFile)
		if err != nil {
			return err
		}
		defer s.dettachVol(*vol)

		return workFunc(deviceFile)
	}()

	if err != nil {
		return nil, err
	}

	snapInput := &ec2.CreateSnapshotInput{
		VolumeId: vol.VolumeId,
	}

	snapshot, err := s.ec2svc.CreateSnapshot(snapInput)
	if err != nil {
		log.WithField("err", err).Error("CreateSnapshot errored")
		return nil, err
	}
	snapDesc := &ec2.DescribeSnapshotsInput{
		SnapshotIds: []*string{snapshot.SnapshotId},
	}
	err = s.ec2svc.WaitUntilSnapshotCompleted(snapDesc)
	if err != nil {
		log.WithField("err", err).Error("WaitUntilSnapshotCompleted errored")
		return nil, err
	}
	return snapshot, nil
}

func (s *AWSStager) copyToAws(imgFile string, localFolder model.Volume) (*ec2.Snapshot, error) {

	return s.workOnVolume(imgFile, func(deviceFile string) error {
		return utils.CopyToImgFile(localFolder.Path, deviceFile)
	})

}

func (s *AWSStager) getAwsVolume() (*ec2.Volume, error) {
	az, err := s.meta.GetMetadata("placement/availability-zone")
	if err != nil {
		log.WithField("err", err).Error("GetMetada AZ failed")

		return nil, err
	}
	volume, err := s.ec2svc.CreateVolume(&ec2.CreateVolumeInput{AvailabilityZone: aws.String(az),
		Size: aws.Int64(SizeInGigs),
	})

	if err != nil {
		return nil, err
	}
	volIn := &ec2.DescribeVolumesInput{VolumeIds: []*string{volume.VolumeId}}
	err = s.ec2svc.WaitUntilVolumeAvailable(volIn)
	log.WithField("vol", *volume.VolumeId).Debug("Volume created")
	return volume, nil
}

func (s *AWSStager) attachVol(vol ec2.Volume, file string) error {

	instid, err := s.meta.GetMetadata("instance-id")
	if err != nil {
		log.WithField("err", err).Error("Get metadata instance-id")
		return err
	}
	params := &ec2.AttachVolumeInput{
		Device:     aws.String(file),
		InstanceId: aws.String(instid),
		VolumeId:   vol.VolumeId,
	}
	_, err = s.ec2svc.AttachVolume(params)
	if err != nil {
		log.WithField("err", err).Error("Failed attaching a volume")
		return err
	}

	volIn := &ec2.DescribeVolumesInput{VolumeIds: []*string{vol.VolumeId}}
	err = s.ec2svc.WaitUntilVolumeInUse(volIn)
	if err != nil {
		log.WithField("err", err).Error("Failed waiting for a volume")
		return err
	}

	isFile := waitForFile(file, 3*time.Minute)
	if !isFile {
		log.WithFields(log.Fields{"file": file}).Error("Can't attach volume!")
		err := s.dettachVol(vol)
		if err != nil {
			log.WithField("err", err).Error("Error detaching volume")
		}
		return errors.New("File was not created")
	}

	log.WithFields(log.Fields{"vol": *vol.VolumeId, "dev": file}).Debug("Volume attached")
	return err
}

func waitForFile(file string, dur time.Duration) bool {
	log.WithFields(log.Fields{"file": file}).Debug("Waiting for file")
	timeout := time.Now().Add(dur)
	for (!device.IsExists(file)) && time.Now().Before(timeout) {
		time.Sleep(time.Second)
	}
	res := device.IsExists(file)
	log.WithFields(log.Fields{"file": file, "res": res}).Debug("Done for file")
	return res
}

func (s *AWSStager) dettachVol(vol ec2.Volume) error {

	params := &ec2.DetachVolumeInput{
		VolumeId: vol.VolumeId,
	}
	log.WithFields(log.Fields{"vol": *vol.VolumeId}).Debug("dettaching Volume")

	_, err := s.ec2svc.DetachVolume(params)
	if err != nil {
		return err
	}
	log.WithFields(log.Fields{"vol": *vol.VolumeId}).Debug("Volume detached")
	return err
}

var kernelIdMap = map[string]string{
	"ap-northeast-1": "aki-176bf516",
	"ap-southeast-1": "aki-503e7402",
	"ap-southeast-2": "aki-c362fff9",
	"eu-central-1":   "aki-184c7a05",
	"eu-west-1":      "aki-52a34525",
	"sa-east-1":      "aki-5553f448",
	"us-east-1":      "aki-919dcaf8",
	"us-gov-west-1":  "aki-1de98d3e",
	"us-west-1":      "aki-880531cd",
	"us-west-2":      "aki-fc8f11cc",
}

func getBlockDeviceMapping(snapmapping map[string]ec2.EbsBlockDevice) []*ec2.BlockDeviceMapping {
	var mapping []*ec2.BlockDeviceMapping
	for dev, ebsblock := range snapmapping {
		mapping = append(mapping, &ec2.BlockDeviceMapping{
			DeviceName: aws.String(dev),
			Ebs:        &ebsblock,
		})
	}
	return mapping
}

func (s *AWSStager) registerImage(appName string, snapmapping map[string]ec2.EbsBlockDevice) error {

	region, err := s.meta.Region()
	if err != nil {
		return err
	}

	const AWSTIME = "Monday 02-Jan-06 15-04-05 MST"

	params := &ec2.RegisterImageInput{
		Name:                aws.String(fmt.Sprintf("Unikly unik kernel %s", time.Now().Format(AWSTIME))), // Required
		Architecture:        aws.String("x86_64"),
		BlockDeviceMappings: getBlockDeviceMapping(snapmapping),
		Description:         aws.String("Unik"),
		KernelId:            aws.String(kernelIdMap[region]),
		RootDeviceName:      aws.String(AWDRootDevice),
		VirtualizationType:  aws.String("paravirtual"),
	}

	blkmap := func() map[string]string {
		m := map[string]string{}
		for k, v := range snapmapping {
			if v.SnapshotId != nil {
				m[k] = *v.SnapshotId
			} else {
				m[k] = "nil"
			}
		}
		return m
	}
	logparams := log.Fields{"name": *params.Name, "arch": *params.Architecture, "blkmap": blkmap(), "kernel": *params.KernelId}

	log.WithFields(logparams).Debug("Registering image")

	imageout, err := s.ec2svc.RegisterImage(params)
	if err != nil {
		return err
	}
	fmt.Println("registered image! ", *imageout.ImageId)

	for _, v := range snapmapping {
		if v.SnapshotId != nil {
			err = s.addTag(*v.SnapshotId, "UNIKERNEL_ID", *imageout.ImageId)
			if err != nil {
				return err
			}
		}
	}

	err = s.addTag(*imageout.ImageId, "UNIKERNEL_APP_NAME", appName)
	if err != nil {
		return err
	}
	log.Debug("Created tags")
	return nil
}

func (s *AWSStager) addTag(id, key, value string) error {

	tagInput := &ec2.CreateTagsInput{
		Resources: []*string{aws.String(id)},
		Tags: []*ec2.Tag{
			&ec2.Tag{
				Key:   aws.String(key),
				Value: aws.String(value),
			},
		},
	}
	_, err := s.ec2svc.CreateTags(tagInput)
	return err
}
