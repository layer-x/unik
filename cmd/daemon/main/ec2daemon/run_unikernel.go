package ec2daemon
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/unik/cmd/daemon/main/unik_ec2_utils"
	"github.com/pborman/uuid"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
)

func runApp(appName string, instances int64) ([]string, error) {
	unikernels, err := listUnikernels()
	instanceIds := []string{}
	if err != nil {
		return instanceIds, lxerrors.New("could not retrieve unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.AppName == appName {
			ec2Client, err := ec2_metada_client.NewEC2Client()
			if err != nil {
				return instanceIds, lxerrors.New("could not start ec2 client session", err)
			}
			startInstancesInput := &ec2.RunInstancesInput{
				ImageId: aws.String(unikernel.AMI),
				MaxCount: aws.Int64(instances),
				MinCount: aws.Int64(instances),
			}
			lxlog.Debugf(logrus.Fields{"input": startInstancesInput}, "starting instance for app "+appName)
			reservation, err := ec2Client.RunInstances(startInstancesInput)
			if err != nil {
				return instanceIds, lxerrors.New("failed to run instance for app "+appName, err)
			}
			lxlog.Debugf(logrus.Fields{"reservation": reservation}, "started instance for app "+appName)
			for _, instance := range reservation.Instances {
				if unikernel.AMI == *instance.ImageId {
					instanceId := appName + "/" + uuid.New()
					createTagsInput := &ec2.CreateTagsInput{
						Resources: aws.StringSlice([]string{*instance.InstanceId}),
						Tags: []*ec2.Tag{
							&ec2.Tag{
								Key: aws.String("Name"),
								Value: aws.String(instanceId),
							},
							&ec2.Tag{
								Key: aws.String(unik_ec2_utils.UNIK_INSTANCE_ID),
								Value: aws.String(instanceId),
							},
							&ec2.Tag{
								Key: aws.String(unik_ec2_utils.UNIKERNEL_ID),
								Value: aws.String(unikernel.AMI),
							},
						},
					}
					lxlog.Debugf(logrus.Fields{"tags": createTagsInput}, "tagging instance for app "+instanceId)
					createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
					if err != nil {
						return instanceIds, lxerrors.New("failed to tag instance for app "+appName, err)
					}
					lxlog.Debugf(logrus.Fields{"output": createTagsOutput}, "tagged instance for app "+instanceId)
					instanceIds = append(instanceIds, instanceId)
				}
			}
		}
	}
	return instanceIds, nil
}