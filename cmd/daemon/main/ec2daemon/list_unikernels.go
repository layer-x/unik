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
	unikernels, err := getAllUnikernels()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
		lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
		return
	}
	lxlog.Debugf(logrus.Fields{"unikernels": unikernels}, "Listing all unikernels")
	lxmartini.Respond(res, unikernels)
}

func getAllUnikernels() (error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	describeInstancesOutput, err := ec2Client.DescribeInstances(ec2.DescribeInstancesInput{})
	if err != nil {
		return lxerrors.New("running 'describe instances'", err)
	}

	allInstances := []*ec2.Instance{}

	for _, reservation := range describeInstancesOutput.Reservations {
		for _, instance := range reservation.Instances {
			allInstances = append(allInstances, instance)
		}
	}

	allUnikernels := []*types.Unikernel{}

	for _, instance := range allInstances {
		unikernel := unik_ec2_utils.GetUnikMetadata(instance)
		if unikernel != nil {
			allUnikernels = append(allUnikernels, unikernel)
		}
	}
	return allUnikernels
}