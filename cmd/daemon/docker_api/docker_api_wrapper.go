package docker_api
import (
	"github.com/go-martini/martini"
	"net/http"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/cmd/daemon/main/ec2api"
)


func AddDockerApi(m *martini.ClassicMartini) *martini.ClassicMartini {
	m.Get("/v1.20/containers/json", func(res http.ResponseWriter, req *http.Request) {
		instances, err := ec2api.ListUnikInstances()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unik instance list")
			lxmartini.Respond(res, lxerrors.New("could not get unik instance list", err))
			return
		}
		dockerInstances := []*DockerUnikInstance{}
		for _, instance := range instances {
			dockerInstance := covertUnikInstance(instance)
			dockerInstances = append(dockerInstances, dockerInstance)
		}
		lxlog.Debugf(logrus.Fields{"dockerInstances": dockerInstances}, "Listing all unik instances for docker")
		lxmartini.Respond(res, dockerInstances)
	})
	m.Get("/v1.20/images/json", func(res http.ResponseWriter, req *http.Request) {
		unikernels, err := ec2api.ListUnikernels()
		if err != nil {
			lxlog.Errorf(logrus.Fields{"err": err}, "could not get unikernel list")
			lxmartini.Respond(res, lxerrors.New("could not get unikernel list", err))
			return
		}
		dockerUnikernels := []*DockerUnikernel{}
		for _, unikernel := range unikernels {
			dockerInstance := convertUnikernel(unikernel)
			dockerUnikernels = append(dockerUnikernels, dockerInstance)
		}
		lxlog.Debugf(logrus.Fields{"dockerUnikernels": dockerUnikernels}, "Listing all unikernels for docker")
		lxmartini.Respond(res, dockerUnikernels)
	})
	return m
}