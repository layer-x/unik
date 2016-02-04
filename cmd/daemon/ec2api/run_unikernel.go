package ec2api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/ec2_metada_client"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/unik/cmd/daemon/unik_ec2_utils"
	"github.com/pborman/uuid"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
)

func RunApp(unikernelName, instanceName string, instances int64) ([]string, error) {
	unikernels, err := ListUnikernels()
	instanceIds := []string{}
	if err != nil {
		return instanceIds, lxerrors.New("could not retrieve unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			ec2Client, err := ec2_metada_client.NewEC2Client()
			if err != nil {
				return instanceIds, lxerrors.New("could not start ec2 client session", err)
			}
			startInstancesInput := &ec2.RunInstancesInput{
				ImageId: aws.String(unikernel.AMI),
				MaxCount: aws.Int64(instances),
				MinCount: aws.Int64(instances),
			}
			lxlog.Debugf(logrus.Fields{"input": startInstancesInput}, "starting instance for unikernel "+unikernelName)
			reservation, err := ec2Client.RunInstances(startInstancesInput)
			if err != nil {
				return instanceIds, lxerrors.New("failed to run instance for unikernel "+unikernelName, err)
			}
			lxlog.Debugf(logrus.Fields{"reservation": reservation}, "started instance for unikernel "+unikernelName)
			for _, instance := range reservation.Instances {
				if unikernel.AMI == *instance.ImageId {
					instanceId := unikernelName + "_" + uuid.New()
					if instanceName == "" {
						instanceName = instanceId
					}
					createTagsInput := &ec2.CreateTagsInput{
						Resources: aws.StringSlice([]string{*instance.InstanceId}),
						Tags: []*ec2.Tag{
							&ec2.Tag{
								Key: aws.String("Name"),
								Value: aws.String(instanceName),
							},
							&ec2.Tag{
								Key: aws.String(unik_ec2_utils.UNIK_INSTANCE_ID),
								Value: aws.String(instanceId),
							},
							&ec2.Tag{
								Key: aws.String(unik_ec2_utils.UNIKERNEL_ID),
								Value: aws.String(unikernel.AMI),
							},
							&ec2.Tag{
								Key: aws.String(unik_ec2_utils.UNIKERNEL_APP_NAME),
								Value: aws.String(unikernelName),
							},
						},
					}
					lxlog.Debugf(logrus.Fields{"tags": createTagsInput}, "tagging instance for unikernel "+instanceId)
					createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
					if err != nil {
						return instanceIds, lxerrors.New("failed to tag instance for unikernel "+unikernelName, err)
					}
					lxlog.Debugf(logrus.Fields{"output": createTagsOutput}, "tagged instance for unikernel "+instanceId)
					instanceIds = append(instanceIds, instanceId)
				}
			}
		}
	}
	return instanceIds, nil
}