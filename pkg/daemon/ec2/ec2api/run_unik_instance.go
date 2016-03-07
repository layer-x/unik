package ec2api

import (
	"github.com/Sirupsen/logrus"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/layer-x/unik/pkg/daemon/ec2/unik_ec2_utils"
	"github.com/pborman/uuid"
	"github.com/layer-x/unik/pkg/types"
	"encoding/base64"
	"encoding/json"
)

func RunUnikInstance(unikernelName, instanceName string, instances int64, tags map[string]string, env map[string]string) ([]string, error) {
	unikernels, err := ListUnikernels()
	instanceIds := []string{}
	if err != nil {
		return instanceIds, lxerrors.New("could not retrieve unikernel list", err)
	}
	var unikernelFound bool
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			unikernelFound = true
			ec2Client, err := ec2_metada_client.NewEC2Client()
			if err != nil {
				return instanceIds, lxerrors.New("could not start ec2 client session", err)
			}
			unikInstanceData := types.UnikInstanceData{
				Tags: tags,
				Env:  env,
			}
			data, err := json.Marshal(unikInstanceData)
			if err != nil {
				return instanceIds, lxerrors.New("could not convert unik instance data struct to json", err)
			}
			encodedData := base64.StdEncoding.EncodeToString(data)
			lxlog.Debugf(logrus.Fields{"unikinstancedata": string(data), "encoded_bytes": len(encodedData)}, "metadata for running unikinstance")
			startInstancesInput := &ec2.RunInstancesInput{
				ImageId:  aws.String(unikernel.Id),
				InstanceType: aws.String("m1.small"),
				MaxCount: aws.Int64(instances),
				MinCount: aws.Int64(instances),
				UserData: aws.String(encodedData),
			}
			lxlog.Debugf(logrus.Fields{"input": startInstancesInput}, "starting instance for unikernel "+unikernelName)
			reservation, err := ec2Client.RunInstances(startInstancesInput)
			if err != nil {
				return instanceIds, lxerrors.New("failed to run instance for unikernel "+unikernelName, err)
			}
			lxlog.Debugf(logrus.Fields{"reservation": reservation}, "started instance for unikernel "+unikernelName)
			for _, instance := range reservation.Instances {
				if unikernel.Id == *instance.ImageId {
					instanceId := unikernelName + "_" + uuid.New()
					if instanceName == "" {
						instanceName = instanceId
					}
					createTagsInput := &ec2.CreateTagsInput{
						Resources: aws.StringSlice([]string{*instance.InstanceId}),
						Tags: []*ec2.Tag{
							&ec2.Tag{
								Key:   aws.String("Name"),
								Value: aws.String(instanceName),
							},
							&ec2.Tag{
								Key:   aws.String(unik_ec2_utils.UNIK_INSTANCE_ID),
								Value: aws.String(instanceId),
							},
							&ec2.Tag{
								Key:   aws.String(unik_ec2_utils.UNIKERNEL_ID),
								Value: aws.String(unikernel.Id),
							},
							&ec2.Tag{
								Key:   aws.String(unik_ec2_utils.UNIKERNEL_NAME),
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
	if !unikernelFound {
		return instanceIds, lxerrors.New("could not find a unikernel with name "+unikernelName, nil)
	}
	return instanceIds, nil
}
