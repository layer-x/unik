package vsphere_utils
import (
	"golang.org/x/net/context"
	"github.com/vmware/govmomi"
	"github.com/layer-x/layerx-commons/lxerrors"
	"net/url"
	"github.com/vmware/govmomi/find"
	"github.com/vmware/govmomi/vim25/mo"
	"github.com/vmware/govmomi/property"
 vspheretypes "github.com/vmware/govmomi/vim25/types"
)

type VsphereClient struct {
	c *govmomi.Client
	f *find.Finder
}

func NewVsphereClient(u *url.URL) (*VsphereClient, error) {
	c, err := govmomi.NewClient(context.TODO(), u, true)
	if err != nil {
		return nil, lxerrors.New("creating new govmovi client", err)
	}

	f := find.NewFinder(c.Client, true)
	return &VsphereClient{
		c: c,
		f: f,
	}
}

func (vc *VsphereClient) Vms() ([]mo.VirtualMachine, error) {
	vms, err := vc.f.VirtualMachineList(context.TODO(), "*")
	if err != nil {
		return nil, lxerrors.New("retrieving virtual machine list from finder", err)
	}
	vmList := []mo.VirtualMachine{}
	for _, vm := range vms {
		managedVms := []mo.VirtualMachine{}
		pc := property.DefaultCollector(vm.Client())
		refs := make([]vspheretypes.ManagedObjectReference, 0, len(vms))
		refs = append(refs, vm.Reference())
		err = pc.Retrieve(context.TODO(), refs, nil, &managedVms)
		if err != nil {
			return nil, lxerrors.New("retrieving managed vms property of vm "+vm.String(), err)
		}
		if len(managedVms) < 1 {
			return nil, lxerrors.New("0 managed vms found for vm "+vm.String(), nil)
		}
		vmList = append(vmList, managedVms[0])
	}
	return vmList, nil
}