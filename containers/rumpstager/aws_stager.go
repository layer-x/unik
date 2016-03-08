package main

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
)

const SizeInGigs = 1

func stage_aws(appName, kernelPath string, volumes map[string]Volume, c model.RumpConfig) {

	deviceToSnapId := make(map[string]ec2.EbsBlockDevice)

	var wg sync.WaitGroup
	allDevices := []string{"/dev/xvdf", "/dev/xvdg"}
	availableDevices := make(chan string, len(allDevices))
	results := make(chan map[string]ec2.Snapshot)
	for _, d := range allDevices {
		availableDevices <- d
	}

	wg.Add(1)
	go func() {
		defer func() {
			if r := recover(); r != nil {
				results <- map[string]ec2.Snapshot{}
				log.WithField("recovered", r).Error("Failed creating root volume")
			}
		}()
		defer wg.Done()
		imgFile := <-availableDevices
		defer func() { availableDevices <- imgFile }()

		snapshot := workOnVolume(imgFile, func(imgFile string) error {
			return createBootImageOnBlockDevice(device.BlockDevice(imgFile), device.GigaBytes(SizeInGigs), kernelPath, toRumpJson(addAwsNet(c)))
		})

		results <- map[string]ec2.Snapshot{"/": snapshot}

	}()

	mountToDevice := make(map[string]string)
	mountToDevice["/"] = "/dev/sda1"
	var volIndex int
	for mntPoint, localFolder := range volumes {

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

		if localFolder.Path != "" {

			wg.Add(1)
			go func(mntPoint string, localFolder Volume) {
				defer func() {
					if r := recover(); r != nil {
						results <- map[string]ec2.Snapshot{}
						log.WithField("recovered", r).Error("Failed creating a volume")
					}
				}()
				defer wg.Done()
				dev := <-availableDevices
				defer func() { availableDevices <- dev }()

				snap := copyToAws(dev, localFolder)
				results <- map[string]ec2.Snapshot{mntPoint: snap}

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
	registerImage(appName, deviceToSnapId)

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

func workOnVolume(deviceFile string, workFunc func(string) error) ec2.Snapshot {

	vol := getAwsVolume()
	func() {
		err := attachVol(vol, deviceFile)
		checkErr(err)

		defer dettachVol(vol)

		err = workFunc(deviceFile)
		checkErr(err)

	}()

	snapInput := &ec2.CreateSnapshotInput{
		VolumeId: vol.VolumeId,
	}
	snapshot, err := ec2svc.CreateSnapshot(snapInput)
	checkErr(err)

	snapDesc := &ec2.DescribeSnapshotsInput{
		SnapshotIds: []*string{snapshot.SnapshotId},
	}
	err = ec2svc.WaitUntilSnapshotCompleted(snapDesc)
	checkErr(err)

	return *snapshot
}

func copyToAws(imgFile string, localFolder Volume) ec2.Snapshot {

	vol := getAwsVolume()
	func() {
		err := attachVol(vol, imgFile)
		checkErr(err)

		defer dettachVol(vol)

		err = copyToImgFile(localFolder.Path, imgFile)
		checkErr(err)

	}()

	snapInput := &ec2.CreateSnapshotInput{
		VolumeId: vol.VolumeId,
	}
	snapshot, err := ec2svc.CreateSnapshot(snapInput)
	checkErr(err)

	snapDesc := &ec2.DescribeSnapshotsInput{
		SnapshotIds: []*string{snapshot.SnapshotId},
	}
	err = ec2svc.WaitUntilSnapshotCompleted(snapDesc)
	checkErr(err)

	return *snapshot
}

var awsSession = session.New()
var meta = ec2metadata.New(awsSession)
var ec2svc *ec2.EC2

func init() {
	region, err := meta.Region()
	if err == nil {
		ec2svc = ec2.New(awsSession, &aws.Config{Region: aws.String(region)})
	} else {
		log.Debug("No AWS")
	}

}

func getAwsVolume() ec2.Volume {
	az, err := meta.GetMetadata("placement/availability-zone")
	checkErr(err)

	volume, err := ec2svc.CreateVolume(&ec2.CreateVolumeInput{AvailabilityZone: aws.String(az),
		Size: aws.Int64(SizeInGigs),
	})

	checkErr(err)

	volIn := &ec2.DescribeVolumesInput{VolumeIds: []*string{volume.VolumeId}}
	err = ec2svc.WaitUntilVolumeAvailable(volIn)
	log.WithField("vol", *volume.VolumeId).Debug("Volume created")
	return *volume
}

func attachVol(vol ec2.Volume, file string) error {

	instid, err := meta.GetMetadata("instance-id")
	checkErr(err)

	params := &ec2.AttachVolumeInput{
		Device:     aws.String(file),
		InstanceId: aws.String(instid),
		VolumeId:   vol.VolumeId,
	}
	_, err = ec2svc.AttachVolume(params)
	checkErr(err)

	volIn := &ec2.DescribeVolumesInput{VolumeIds: []*string{vol.VolumeId}}
	err = ec2svc.WaitUntilVolumeInUse(volIn)
	checkErr(err)

	isFile := waitForFile(file, 3*time.Minute)
	if !isFile {
		dettachVol(vol)
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

func dettachVol(vol ec2.Volume) error {

	params := &ec2.DetachVolumeInput{
		VolumeId: vol.VolumeId,
	}
	log.WithFields(log.Fields{"vol": *vol.VolumeId}).Debug("dettaching Volume")

	_, err := ec2svc.DetachVolume(params)
	checkErr(err)
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

func registerImage(appName string, snapmapping map[string]ec2.EbsBlockDevice) {

	region, err := meta.Region()
	checkErr(err)

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

	imageout, err := ec2svc.RegisterImage(params)
	checkErr(err)

	fmt.Println("registered image! ", *imageout.ImageId)

	for _, v := range snapmapping {
		if v.SnapshotId != nil {
			err = addTag(*v.SnapshotId, "UNIKERNEL_ID", *imageout.ImageId)
			checkErr(err)
		}
	}

	err = addTag(*imageout.ImageId, "UNIKERNEL_APP_NAME", appName)
	checkErr(err)
	log.Debug("Created tags")

}

func addTag(id, key, value string) error {

	tagInput := &ec2.CreateTagsInput{
		Resources: []*string{aws.String(id)},
		Tags: []*ec2.Tag{
			&ec2.Tag{
				Key:   aws.String(key),
				Value: aws.String(value),
			},
		},
	}
	_, err := ec2svc.CreateTags(tagInput)
	return err
}
