package ec2daemon
import (
	"net/http"
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/daemon/main/unik_ec2_utils"
)

func (d *UnikEc2Daemon) listUnikernels(res http.ResponseWriter) {
	unikernels, err := getAllUnikernals()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
		lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
		return
	}
	lxlog.Debugf(logrus.Fields{"unikernels": unikernels}, "Listing all unikernels")
	lxmartini.Respond(res, unikernels)
}

func getAllUnikernals() ([]*types.Unikernel, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeImagesOutput, err := ec2Client.DescribeImages(ec2.DescribeImagesInput{})
	if err != nil {
		return nil, lxerrors.New("running 'describe images'", err)
	}

	allUnikernels := []*types.Unikernel{}
	for _, image := range describeImagesOutput.Images {
		unikernel := unik_ec2_utils.GetUnikernelMetadata(image)
		if unikernel != nil {
			allUnikernels = append(allUnikernels, unikernel)
		}
	}

	return allUnikernels, nil
}