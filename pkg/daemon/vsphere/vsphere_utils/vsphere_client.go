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
	"os/exec"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
	"path/filepath"
	"strings"
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
	}, nil
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

func (vc *VsphereClient) CreateVm(vmName, annotation string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"vm.create",
		"-k",
		"-u", vc.c.URL().String(),
		"--annotation="+annotation,
		"--force=true",
		"--m=512",
		"--on=false",
		vmName,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc vm.create "+vmName, err)
	}
	return nil
}

//TODO: copy vmdk

func (vc *VsphereClient) DestroyVm(vmName string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"vm.destroy",
		"-k",
		"-u", vc.c.URL().String(),
		"--force=true",
		"--m=512",
		"--on=false",
		vmName,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc vm.create "+vmName, err)
	}
	return nil
}

func (vc *VsphereClient) Mkdir(folder string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"datastore.mkdir",
		"-k",
		"-u", vc.c.URL().String(),
		folder,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc datastore.mkdir "+folder, err)
	}
	return nil
}

func (vc *VsphereClient) Rmdir(folder string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"datastore.rm",
		"-k",
		"-u", vc.c.URL().String(),
		folder,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc datastore.rm "+folder, err)
	}
	return nil
}

func (vc *VsphereClient) ImportVmdk(vmdkPath, folder string) error {
	vmdkFolder := filepath.Dir(vmdkPath)
	cmd := exec.Command("docker", "run", "--rm", "-v", vmdkFolder+":"+vmdkFolder,
		"vsphere-client",
		"govc",
		"import.vmdk",
		"-k",
		"-u", vc.c.URL().String(),
		vmdkPath,
		folder,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc import.vmdk "+folder, err)
	}
	return nil
}

func (vc *VsphereClient) UploadFile(srcFile, dest string) error {
	srcDir := filepath.Dir(srcFile)
	cmd := exec.Command("docker", "run", "--rm", "-v", srcDir +":"+srcDir,
		"vsphere-client",
		"govc",
		"datastore.upload",
		"-k",
		"-u", vc.c.URL().String(),
		srcFile,
		dest,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc datastore.upload", err)
	}
	return nil
}

func (vc *VsphereClient) DownloadFile(remoteFile, localFile string) error {
	localDir := filepath.Dir(localFile)
	cmd := exec.Command("docker", "run", "--rm", "-v", localDir +":"+ localDir,
		"vsphere-client",
		"govc",
		"datastore.upload",
		"-k",
		"-u", vc.c.URL().String(),
		remoteFile,
		localFile,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc datastore.upload", err)
	}
	return nil
}

func (vc *VsphereClient) Copy(src, dest string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"datastore.cp",
		"-k",
		"-u", vc.c.URL().String(),
		src,
		dest,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc datastore.cp "+src+" "+dest, err)
	}
	return nil
}

func (vc *VsphereClient) Ls(dir string) ([]string, error) {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"datastore.ls",
		"-k",
		"-u", vc.c.URL().String(),
		dir,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	out, err := cmd.Output()
	if err != nil {
		return nil, lxerrors.New("failed running govc datastore.ls "+dir, err)
	}
	split := strings.Split(string(out), "\n")
	contents := []string{}
	for _, content := range split {
		if content != "" {
			contents = append(contents, content)
		}
	}
	return contents, nil
}

func (vc *VsphereClient) PowerOnVm(vmName string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"vm.power",
		"--on=true",
		"-k",
		"-u", vc.c.URL().String(),
		vmName,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc vm.power (on)", err)
	}
	return nil
}

func (vc *VsphereClient) PowerOffVm(vmName string) error {
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"govc",
		"vm.power",
		"--off=true",
		"-k",
		"-u", vc.c.URL().String(),
		vmName,
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc vm.power (off)", err)
	}
	return nil
}

func (vc *VsphereClient) AttachVmdk(vmName, vmdkPath string) error {
	password, _ := vc.c.URL().User.Password()
	cmd := exec.Command("docker", "run", "--rm",
		"vsphere-client",
		"java",
		"-jar",
		"/vsphere-client.jar",
		"VmAttachDisk",
		vc.c.URL().String(),
		vc.c.URL().User.Username(),
		password,
		vmName,
		"200", //TODO: is this right?
	)
	lxlog.Debugf(logrus.Fields{"command": cmd.Args}, "running govc command")
	lxlog.LogCommand(cmd, true)
	err := cmd.Run()
	if err != nil {
		return lxerrors.New("failed running govc vm.power (off)", err)
	}
	return nil
}


