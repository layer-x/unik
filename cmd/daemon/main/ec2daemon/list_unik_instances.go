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

func (d *UnikEc2Daemon) listUnikInstances(res http.ResponseWriter) {
	instances, err := getAllUnikInstances()
	if err != nil {
		lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
		lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
		return
	}
	lxlog.Debugf(logrus.Fields{"instances": instances}, "Listing all unik instances")
	lxmartini.Respond(res, instances)
}

func getAllUnikInstances() ([]*types.UnikInstance, error) {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return nil, lxerrors.New("could not start ec2 client session", err)
	}
	describeInstancesOutput, err := ec2Client.DescribeInstances(ec2.DescribeInstancesInput{})
	if err != nil {
		return nil, lxerrors.New("running 'describe instances'", err)
	}

	allUnikInstances := []*types.UnikInstance{}

	for _, reservation := range describeInstancesOutput.Reservations {
		for _, instance := range reservation.Instances {
			unikInstance := unik_ec2_utils.GetUnikInstanceMetadata(instance)
			if unikInstance != nil {
				allUnikInstances = append(allUnikInstances, unikInstance)
			}
		}
	}

	return allUnikInstances, nil
}