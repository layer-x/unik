package stagers

import (
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

func (s *AWSStager) Stage(appName, kernelPath string, volumes map[string]model.Volume, c model.RumpConfig) error {

	deviceToSnapId := make(map[string]ec2.EbsBlockDevice)

	var wg sync.WaitGroup
	allDevices := []string{"/dev/xvdf", "/dev/xvdg"}
	availableDevices := make(chan string, len(allDevices))
	results := make(chan map[string]ec2.Snapshot)
	for _, d := range allDevices {
		availableDevices <- d
	}

	c, mountToDevice := updateConfig(volumes, c)
	mountToDevice["/"] = "/dev/sda1"

	jsonString, err := utils.ToRumpJson(addAwsNet(c))
	if err != nil {
		return err
	}

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

		results <- map[string]ec2.Snapshot{"/": *snapshot}

	}()

	for _, blk := range c.Blk {
		mntPoint := blk.MountPoint
		localFolder := volumes[mntPoint]

		if localFolder.Path != "" {
			wg.Add(1)
			go func(mntPoint string, localFolder model.Volume) {

				defer wg.Done()
				dev := <-availableDevices
				defer func() { availableDevices <- dev }()

				snap, err := s.copyToAws(dev, localFolder)
				if err != nil {
					log.WithField("err", err).Error("Failed creating a volume")
				} else {
					results <- map[string]ec2.Snapshot{mntPoint: *snap}
				}

			}(mntPoint, localFolder)
		} else {
			deviceToSnapId[mountToDevice[mntPoint]] = ec2.EbsBlockDevice{VolumeSize: aws.Int64(localFolder.Size)}
		}

	}

	go func() {
		wg.Wait()
		close(results)
	}()

	for res := range results {
		for mntPoint, snap := range res {
			log.WithFields(log.Fields{"snap": snap.SnapshotId, "mntPoint": mntPoint, "dev": mountToDevice[mntPoint]}).Debug("Adding result to map")

			deviceToSnapId[mountToDevice[mntPoint]] = ec2.EbsBlockDevice{SnapshotId: snap.SnapshotId}
		}
	}

	// +! for root volume
	if len(deviceToSnapId) != (len(mountToDevice)) {
		log.WithFields(log.Fields{"deviceToSnapId": deviceToSnapId, "mountToDevice": mountToDevice}).Panic("Not all volumes created")
	}

	/// all should be ready for aws
	s.registerImage(appName, deviceToSnapId)
	return nil
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
		return err
	}
	params := &ec2.AttachVolumeInput{
		Device:     aws.String(file),
		InstanceId: aws.String(instid),
		VolumeId:   vol.VolumeId,
	}
	_, err = s.ec2svc.AttachVolume(params)
	if err != nil {
		return err
	}

	volIn := &ec2.DescribeVolumesInput{VolumeIds: []*string{vol.VolumeId}}
	err = s.ec2svc.WaitUntilVolumeInUse(volIn)
	if err != nil {
		return err
	}

	isFile := waitForFile(file, 3*time.Minute)
	if !isFile {
		s.dettachVol(vol)
		log.WithFields(log.Fields{"file": file}).Panic("Can't attach volume!")
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
		RootDeviceName:      aws.String("/dev/sda1"),
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
