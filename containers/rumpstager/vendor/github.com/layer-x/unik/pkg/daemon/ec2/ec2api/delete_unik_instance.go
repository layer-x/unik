package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
"github.com/layer-x/layerx-commons/lxlog"
)

func DeleteUnikInstance(logger *lxlog.LxLogger, unikInstanceId string) error {
	unikInstance, err := GetUnikInstanceByPrefixOrName(logger, unikInstanceId)
	if err != nil {
		return lxerrors.New("failed to retrieve unik instance", err)
	}
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	terminateInstancesInput := &ec2.TerminateInstancesInput{
		InstanceIds: []*string{aws.String(unikInstance.VMID)},
	}
	_, err = ec2Client.TerminateInstances(terminateInstancesInput)
	if err != nil {
		return lxerrors.New("could not terminate unik instance "+unikInstanceId, err)
	}
	return nil
}
