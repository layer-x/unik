package ec2_metada_client

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"os/exec"
	"strings"
	"time"
	"github.com/aws/aws-sdk-go/aws/request"
	"github.com/layer-x/layerx-commons/lxlog"
)

const MAX_RETRIES = 5

var (
	ec2ClientSingleton *ec2.EC2
	availabilityZone string
)

type UnikEc2Client struct {
	ec2Client *ec2.EC2
	AvailabilityZone string
}

func GetRegion() (string, error) {
	curlCommand := exec.Command("curl", "http://169.254.169.254/latest/meta-data/placement/availability-zone")
	azBytes, err := curlCommand.Output()
	if err != nil {
		return "", lxerrors.New("could not run \"curl http://169.254.169.254/latest/meta-data/placement/availability-zone\"", err)
	}
	region := string(azBytes)
	for _, r := range "abcde" {
		region = strings.TrimSuffix(region, string(r))
	}
	return region, nil
}

func getAZ() (string, error) {
	curlCommand := exec.Command("curl", "http://169.254.169.254/latest/meta-data/placement/availability-zone")
	azBytes, err := curlCommand.Output()
	if err != nil {
		return "", lxerrors.New("could not run \"curl http://169.254.169.254/latest/meta-data/placement/availability-zone\"", err)
	}
	az := string(azBytes)
	return az, nil
}

func NewEC2Client(logger *lxlog.LxLogger) (*UnikEc2Client, error) {
	if ec2ClientSingleton == nil {
		var err error
		availabilityZone, err = getAZ()
		if err != nil {
			return nil, lxerrors.New("getting region from ec2 metadata server", err)
		}
		region := availabilityZone
		for _, r := range "abcde" {
			region = strings.TrimSuffix(region, string(r))
		}
		session := session.New()
		session.Config.WithMaxRetries(MAX_RETRIES)
		session.Handlers.Send.PushFront(func(r *request.Request) {
			logger.WithFields(lxlog.Fields{"request": r}).Debugf("request sent to aws")
		})
		ec2ClientSingleton = ec2.New(session, &aws.Config{
			Region: aws.String(region),
		})
	}
	return &UnikEc2Client{
		ec2Client: ec2ClientSingleton,
		AvailabilityZone: availabilityZone,
	}, nil
}


func (c *UnikEc2Client) TerminateInstances(input *ec2.TerminateInstancesInput) (*ec2.TerminateInstancesOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.TerminateInstances(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DeregisterImage(input *ec2.DeregisterImageInput) (*ec2.DeregisterImageOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DeregisterImage(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DescribeSnapshots(input *ec2.DescribeSnapshotsInput) (*ec2.DescribeSnapshotsOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DescribeSnapshots(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DeleteSnapshot(input *ec2.DeleteSnapshotInput) (*ec2.DeleteSnapshotOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DeleteSnapshot(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DeleteVolume(input *ec2.DeleteVolumeInput) (*ec2.DeleteVolumeOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DeleteVolume(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) GetConsoleOutput(input *ec2.GetConsoleOutputInput) (*ec2.GetConsoleOutputOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.GetConsoleOutput(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DescribeInstances(input *ec2.DescribeInstancesInput) (*ec2.DescribeInstancesOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DescribeInstances(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DescribeImages(input *ec2.DescribeImagesInput) (*ec2.DescribeImagesOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DescribeImages(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) RunInstances(input *ec2.RunInstancesInput) (*ec2.Reservation, error) {
	var retries uint
	for {
		output, err := c.ec2Client.RunInstances(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) CreateTags(input *ec2.CreateTagsInput) (*ec2.CreateTagsOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.CreateTags(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DescribeInstanceAttribute(input *ec2.DescribeInstanceAttributeInput) (*ec2.DescribeInstanceAttributeOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DescribeInstanceAttribute(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) CreateVolume(input *ec2.CreateVolumeInput) (*ec2.Volume, error) {
	var retries uint
	for {
		output, err := c.ec2Client.CreateVolume(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DescribeVolumes(input *ec2.DescribeVolumesInput) (*ec2.DescribeVolumesOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DescribeVolumes(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) AttachVolume(input *ec2.AttachVolumeInput) (*ec2.VolumeAttachment, error) {
	var retries uint
	for {
		output, err := c.ec2Client.AttachVolume(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) DetachVolume(input *ec2.DetachVolumeInput) (*ec2.VolumeAttachment, error) {
	var retries uint
	for {
		output, err := c.ec2Client.DetachVolume(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) WaitUntilVolumeAvailable(input *ec2.DescribeVolumesInput) error {
	var retries uint
	for {
		err := c.ec2Client.WaitUntilVolumeAvailable(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return err
		}
	}
}

func (c *UnikEc2Client) WaitUntilVolumeInUse(input *ec2.DescribeVolumesInput) error {
	var retries uint
	for {
		err := c.ec2Client.WaitUntilVolumeInUse(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return err
		}
	}
}

func (c *UnikEc2Client) CopyImage(input *ec2.CopyImageInput) (*ec2.CopyImageOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.CopyImage(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) ModifyImageAttribute(input *ec2.ModifyImageAttributeInput) (*ec2.ModifyImageAttributeOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.ModifyImageAttribute(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}

func (c *UnikEc2Client) ImportVolume(input *ec2.ImportVolumeInput) (*ec2.ImportVolumeOutput, error) {
	var retries uint
	for {
		output, err := c.ec2Client.ImportVolume(input)
		if err == nil || !strings.Contains(err.Error(), "RequestLimitExceeded") {
			return output, err
		}
		time.Sleep((1 << retries) * time.Second)
		retries++
		if retries > MAX_RETRIES {
			return nil, err
		}
	}
}