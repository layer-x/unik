package ec2api

import (
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

func RunUnikInstance(logger lxlog.Logger, unikernelName, instanceName string, instances int64, persistVolumes bool, mntSnapshotMap map[string]string, tags map[string]string, env map[string]string) ([]string, error) {
	unikernels, err := ListUnikernels(logger)
	instanceIds := []string{}
	if err != nil {
		return instanceIds, lxerrors.New("could not retrieve unikernel list", err)
	}
	var unikernelFound bool
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			unikernelFound = true
			ec2Client, err := ec2_metada_client.NewEC2Client(logger)
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
			logger.WithFields(lxlog.Fields{
				"unikinstancedata": string(data), 
				"encoded_bytes": len(encodedData),
			}).Debugf("metadata for running unikinstance")

			blockDeviceMappings := []*ec2.BlockDeviceMapping{}
			for _, device := range unikernel.Devices {
				snapshotId, ok := mntSnapshotMap[device.MountPoint]
				if !ok {
					snapshotId = device.DefaultSnapshotId
				}
				blockDeviceMappings = append(blockDeviceMappings, &ec2.BlockDeviceMapping{
					DeviceName: aws.String(device.DeviceName),
					Ebs: &ec2.EbsBlockDevice{
						DeleteOnTermination: aws.Bool(!persistVolumes),
						SnapshotId: aws.String(snapshotId),
					},
				})
			}

			startInstancesInput := &ec2.RunInstancesInput{
				ImageId:  aws.String(unikernel.Id),
				InstanceType: aws.String("m1.small"),
				MaxCount: aws.Int64(instances),
				MinCount: aws.Int64(instances),
				UserData: aws.String(encodedData),
				BlockDeviceMappings: blockDeviceMappings,
			}
			logger.WithFields(lxlog.Fields{
				"input": startInstancesInput,
			}).Debugf("starting instance for unikernel "+unikernelName)
			reservation, err := ec2Client.RunInstances(startInstancesInput)
			if err != nil {
				return instanceIds, lxerrors.New("failed to run instance for unikernel "+unikernelName, err)
			}
			logger.WithFields(lxlog.Fields{
				"reservation": reservation,
			}).Debugf("started instance for unikernel "+unikernelName)
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
					logger.WithFields(lxlog.Fields{
						"tags": createTagsInput,
					}).Debugf("tagging instance for unikernel "+instanceId)
					createTagsOutput, err := ec2Client.CreateTags(createTagsInput)
					if err != nil {
						return instanceIds, lxerrors.New("failed to tag instance for unikernel "+unikernelName, err)
					}
					logger.WithFields(lxlog.Fields{
						"output": createTagsOutput,
					}).Debugf("tagged instance for unikernel "+instanceId)
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
