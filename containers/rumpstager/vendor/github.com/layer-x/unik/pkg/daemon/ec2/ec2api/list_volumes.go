package ec2api
import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/layer-x/unik/pkg/types"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/ec2/unik_ec2_utils"
)

func ListVolumes(logger lxlog.Logger) ([]*types.Volume, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeVolumesInput := &ec2.DescribeVolumesInput{
		Filters: []*ec2.Filter{
			&ec2.Filter{
				Name:   aws.String("tag-key"),
				Values: []*string{aws.String(UNIK_BLOCK_DEVICE)},
			},
		},
	}
	describeVolumesOutput, err := ec2Client.DescribeVolumes(describeVolumesInput)
	if err != nil {
		return nil, lxerrors.New("running describe volumes", err)
	}
	logger.WithFields(lxlog.Fields{
		"volumes": describeVolumesOutput.Volumes,
	}).Debugf("retrieved volumes")
	volumes := []*types.Volume{}
	for _, awsVol := range describeVolumesOutput.Volumes {
		volume := unik_ec2_utils.GetVolumeMetadata(awsVol)
		if volume != nil {
			volumes = append(volumes, volume)
		}
	}
	return volumes, nil
}
