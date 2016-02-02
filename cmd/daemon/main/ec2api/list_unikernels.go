package ec2api
import (
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/daemon/main/unik_ec2_utils"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
"github.com/aws/aws-sdk-go/aws"
)

func ListUnikernels() ([]*types.Unikernel, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	lxlog.Debugf(logrus.Fields{"client": ec2Client}, "retrieved client")
	describeImagesInput := &ec2.DescribeImagesInput{
		Filters: []*ec2.Filter{
			&ec2.Filter{
				Name: aws.String("tag-key"),
				Values: []*string{aws.String("UNIKERNEL_APP_NAME")},
			},
		},
	}
	describeImagesOutput, err := ec2Client.DescribeImages(describeImagesInput)
	if err != nil {
		return nil, lxerrors.New("running 'describe images'", err)
	}
	lxlog.Debugf(logrus.Fields{"images": describeImagesOutput.Images}, "retrieved images")

	allUnikernels := []*types.Unikernel{}
	for _, image := range describeImagesOutput.Images {
		unikernel := unik_ec2_utils.GetUnikernelMetadata(image)
		if unikernel != nil {
			lxlog.Debugf(logrus.Fields{"unikernel": unikernel}, "found unikernel")
			allUnikernels = append(allUnikernels, unikernel)
		}
	}
	return allUnikernels, nil
}