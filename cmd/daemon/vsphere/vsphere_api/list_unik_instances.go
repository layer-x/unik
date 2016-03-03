package vsphere_api
import (
	"github.com/layer-x/unik/types"
	"github.com/layer-x/unik/cmd/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/layerx-commons/lxerrors"
)

func ListUnikInstances(creds Creds) ([]*types.Unikernel, error) {
	client, err := vsphere_utils.NewVsphereClient(creds.url)
	if err != nil {
		return nil, lxerrors.New("creating new vsphere client ", err.Error())
	}
	vms, err := client.Vms()
	if err != nil {
		return nil, lxerrors.New("retrieving list of vsphere vms", nil)
	}
}