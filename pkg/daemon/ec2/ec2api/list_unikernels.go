package ec2api

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/layer-x/unik/pkg/daemon/ec2/unik_ec2_utils"
	"github.com/layer-x/unik/pkg/types"
)

func ListUnikernels(logger *lxlog.LxLogger) ([]*types.Unikernel, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client(logger)
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	logger.WithFields(lxlog.Fields{
		"client": ec2Client,
	}).Debugf("retrieved client")
	describeImagesInput := &ec2.DescribeImagesInput{
		Filters: []*ec2.Filter{
			&ec2.Filter{
				Name:   aws.String("tag-key"),
				Values: []*string{aws.String("UNIKERNEL_APP_NAME")},
			},
		},
	}
	describeImagesOutput, err := ec2Client.DescribeImages(describeImagesInput)
	if err != nil {
		return nil, lxerrors.New("running 'describe images'", err)
	}
	logger.WithFields(lxlog.Fields{
		"images": describeImagesOutput.Images,
	}).Debugf("retrieved images")

	allUnikernels := []*types.Unikernel{}
	for _, image := range describeImagesOutput.Images {
		unikernel := unik_ec2_utils.GetUnikernelMetadata(image)
		if unikernel != nil {
			logger.WithFields(lxlog.Fields{
				"unikernel": unikernel,
			}).Debugf("found unikernel")
			allUnikernels = append(allUnikernels, unikernel)
		}
	}
	return allUnikernels, nil
}
