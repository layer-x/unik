/*
Copyright (c) 2015 VMware, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

/*
This example program shows how the `finder` and `property` packages can
be used to navigate a vSphere inventory structure using govmomi.
*/

package main

import (
	"flag"
	"fmt"
	"net/url"
	"os"
	"strings"
	"text/tabwriter"

	"github.com/vmware/govmomi"
	"github.com/vmware/govmomi/find"
	"github.com/vmware/govmomi/property"
	"github.com/vmware/govmomi/units"
	"github.com/vmware/govmomi/vim25/mo"
	"github.com/vmware/govmomi/vim25/types"
	"golang.org/x/net/context"
	"github.com/vmware/govmomi/vim25/soap"
	"github.com/vmware/govmomi/vim25"
	"github.com/vmware/govmomi/session"
	"io/ioutil"
	"github.com/vmware/govmomi/govc/importx"
	"github.com/vmware/govmomi/ovf"
	"github.com/vmware/govmomi/object"
	"errors"
	"golang.org/x/net/webdav/internal/xml"
)

// GetEnvString returns string from environment variable.
func GetEnvString(v string, def string) string {
	r := os.Getenv(v)
	if r == "" {
		return def
	}

	return r
}

// GetEnvBool returns boolean from environment variable.
func GetEnvBool(v string, def bool) bool {
	r := os.Getenv(v)
	if r == "" {
		return def
	}

	switch strings.ToLower(r[0:1]) {
	case "t", "y", "1":
		return true
	}

	return false
}

const (
	envURL = "GOVMOMI_URL"
	envUserName = "GOVMOMI_USERNAME"
	envPassword = "GOVMOMI_PASSWORD"
	envInsecure = "GOVMOMI_INSECURE"
)

var urlDescription = fmt.Sprintf("ESX or vCenter URL [%s]", envURL)
var urlFlag = flag.String("url", GetEnvString(envURL, "https://username:password@host/sdk"), urlDescription)

var insecureDescription = fmt.Sprintf("Don't verify the server's certificate chain [%s]", envInsecure)
var insecureFlag = flag.Bool("insecure", GetEnvBool(envInsecure, false), insecureDescription)

var pathFlag = flag.String("path", "", "path for vm")
var nameFlag = flag.String("name", "osv-automated", "name to create new vm")
var vmdkAbsolutePathFlag = flag.String("vmdk", "", "path to vmdk on datastore")
var controllerKeyFlag = flag.Int("controller", 1, "controllerKeyFlag?")

func processOverride(u *url.URL) {
	envUsername := os.Getenv(envUserName)
	envPassword := os.Getenv(envPassword)

	// Override username if provided
	if envUsername != "" {
		var password string
		var ok bool

		if u.User != nil {
			password, ok = u.User.Password()
		}

		if ok {
			u.User = url.UserPassword(envUsername, password)
		} else {
			u.User = url.User(envUsername)
		}
	}

	// Override password if provided
	if envPassword != "" {
		var username string

		if u.User != nil {
			username = u.User.Username()
		}

		u.User = url.UserPassword(username, envPassword)
	}
}

func exit(err error) {
	fmt.Fprintf(os.Stderr, "Error: %s\n", err)
	os.Exit(1)
}

func main() {
	flag.Parse()

	// Parse URL from string
	u, err := url.Parse(*urlFlag)
	if err != nil {
		exit(err)
	}

	// Override username and/or password as required
	processOverride(u)

	// Connect and log in to ESX or vCenter
	c, err := govmomi.NewClient(context.TODO(), u, *insecureFlag)
	if err != nil {
		exit(err)
	}

	f := find.NewFinder(c.Client, true)

	// Find one and only datacenter
	dc, err := f.DefaultDatacenter(context.TODO())
	if err != nil {
		exit(err)
	}

	// Make future calls local to this datacenter
	f.SetDatacenter(dc)

	// Find datastores in datacenter
	dss, err := f.DatastoreList(context.TODO(), "*")
	if err != nil {
		exit(err)
	}

	pc := property.DefaultCollector(c.Client)

	// Convert datastores into list of references
	var refs []types.ManagedObjectReference
	for _, ds := range dss {
		refs = append(refs, ds.Reference())
	}

	// Retrieve summary property for all datastores
	var dst []mo.Datastore
	err = pc.Retrieve(context.TODO(), refs, []string{"summary"}, &dst)
	if err != nil {
		exit(err)
	}

	// Print summary per datastore
	tw := tabwriter.NewWriter(os.Stdout, 2, 0, 2, ' ', 0)
	fmt.Fprintf(tw, "Name:\tType:\tCapacity:\tFree:\n")
	for _, ds := range dst {
		fmt.Fprintf(tw, "%s\t", ds.Summary.Name)
		fmt.Fprintf(tw, "%s\t", ds.Summary.Type)
		fmt.Fprintf(tw, "%s\t", units.ByteSize(ds.Summary.Capacity))
		fmt.Fprintf(tw, "%s\t", units.ByteSize(ds.Summary.FreeSpace))
		fmt.Fprintf(tw, "\n")
	}
	tw.Flush()

	fmt.Printf("\n")

	//get vms '*' for path
	vms, err := f.VirtualMachineList(context.TODO(), *pathFlag)
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	for _, vm := range vms {
		name, err := vm.Name(context.TODO())
		if err != nil {
			fmt.Printf("something went wrong: %s\n", err.Error())
			os.Exit(-1)
		}
		fmt.Printf("\t %s\n\n", name)
		managedVms := []mo.VirtualMachine{}
		pc := property.DefaultCollector(vm.Client())
		refs := make([]types.ManagedObjectReference, 0, len(vms))
		refs = append(refs, vm.Reference())
		err = pc.Retrieve(context.TODO(), refs, nil, &managedVms)
		for _, managedVm := range managedVms {
			fmt.Printf("\nmanaged vm for vm: %v\n\n", managedVm)
//			fmt.Printf("Config for vm: %v\n", managedVm.Config)
//			fmt.Printf("ConfigStatus for vm: %v\n", managedVm.ConfigStatus)
//			fmt.Printf("Guest for vm: %v\n", managedVm.Guest)
//			fmt.Printf("OverallStatus for vm: %v\n", managedVm.OverallStatus)
			fmt.Printf("IpAddress for vm: %v\n\n", managedVm.Summary.Guest.IpAddress)
			fmt.Printf("OverallStatus for vm: %v\n\n", managedVm.Summary.OverallStatus)
			fmt.Printf("PowerState for vm: %v\n\n", managedVm.Summary.Runtime.PowerState)
			x, err := xml.Marshal(managedVm.Summary)
			if err != nil {
				fmt.Printf("xml summary for vm: %s\n\n", string(x))
			}
		}
	}

	fmt.Printf("\n")
	fmt.Printf("\n")
	fmt.Printf("\n")
	fmt.Printf("\n")

	vimClient, err := newClient(u)
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}

	datacenter, err := f.DefaultDatacenter(context.TODO())
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	fmt.Printf("using datacenter %v...\n", datacenter)

	dcFolders, err := datacenter.Folders(context.TODO())
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	fmt.Printf("using datacenter folders %v...\n", dcFolders)

	datastore, err := f.DefaultDatastore(context.TODO())
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	fmt.Printf("using default datastore %v...\n", datastore)

	resourcePool, err := f.DefaultResourcePool(context.TODO())
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	fmt.Printf("using default resource pool %v...\n", resourcePool)

	host, err := f.DefaultHostSystem(context.TODO())
	if err != nil {
		fmt.Printf("something went wrong: %s\n", err.Error())
		os.Exit(-1)
	}
	fmt.Printf("using default host %v...\n", host)

	fmt.Printf("finding unik subfolder in %v...\n", dcFolders.DatastoreFolder)
	unikFolder, err := findSubFolder(f, dcFolders.DatastoreFolder, "unik")
	if err != nil {
		fmt.Printf("unik folder not found (error: %s), creating...\n", err.Error())

		m := object.NewFileManager(vimClient)
		fmt.Printf("creating unik folder...\n")
		err = m.MakeDirectory(context.TODO(), "["+datastore.Name()+"] unik", dc, true)
		if err != nil {
			fmt.Printf("failed to find OR create unik folder: %s\n", err.Error())
			os.Exit(-1)
		}
	}
	fmt.Printf("using unik folder %v...\n", unikFolder)

//	fmt.Printf("finding unik folder...\n")
//	mos, err := f.ManagedObjectListChildren(context.TODO(), "/ha-datacenter/datastore/datastore1")
//	if err != nil {
//		fmt.Printf("something went wrong getting mo list: %s\n", err.Error())
//		os.Exit(-1)
//	}
//	for _, mo := range mos {
//		fmt.Printf("discovered managed object: %v\n", mo)
//	}

//	unikFolder, err := f.Folder(context.TODO(), "")
//	if err != nil {
//		fmt.Printf("something went wrong: %s\n", err.Error())
//		os.Exit(-1)
//	}

	fmt.Printf("using unik folder %v...\n", unikFolder)

	//	falseRef := false
	//	fmt.Printf("ControllerKey: %v\n", *controllerKeyFlag)
	//	virtualDeviceConfigSpec := types.VirtualDeviceConfigSpec{
	//		Operation: types.VirtualDeviceConfigSpecOperationAdd,
	//		Device:  &types.VirtualDisk{
	//			VirtualDevice: types.VirtualDevice{
	//				Key: 0,
	//				UnitNumber: 1,
	//				//				ControllerKey: *controllerKeyFlag,
	//				Backing: &types.VirtualDiskFlatVer2BackingInfo{
	//					VirtualDeviceFileBackingInfo: types.VirtualDeviceFileBackingInfo{
	//						FileName: *vmdkAbsolutePathFlag,
	//					},
	//					DiskMode: string(types.VirtualDiskModeNonpersistent),
	//					Split: &falseRef,
	//					WriteThrough: &falseRef,
	//					ThinProvisioned: &falseRef,
	//					EagerlyScrub: &falseRef,
	//				},
	//			},
	//		},
	//	}
	//
	//	virtualMachineConfigSpec := types.VirtualMachineConfigSpec{
	//		Name: *nameFlag,
	//		DeviceChange: []types.BaseVirtualDeviceConfigSpec{
	//			&virtualDeviceConfigSpec,
	//		},
	//		Files: &types.VirtualMachineFileInfo{
	//			VmPathName: fmt.Sprintf("[datastore1]/osv/osv.vmx"),
	//		},
	//	}
	//
	//	fmt.Printf("creating vm with spec %v...\n", virtualMachineConfigSpec)
	//	createVmTask, err := dcFolders.VmFolder.CreateVM(context.TODO(), virtualMachineConfigSpec, resourcePool, host)
	//	if err != nil {
	//		fmt.Printf("something went wrong: %s\n", err.Error())
	//		os.Exit(-1)
	//	}
	//	fmt.Printf("started create vm task %v...\n", createVmTask)
	//	err = createVmTask.Wait(context.TODO())
	//	if err != nil {
	//		fmt.Printf("something went wrong: %s\n", err.Error())
	//		os.Exit(-1)
	//	}

	fmt.Printf("finished!!! wtf??\n")

	//	ovfManager := object.NewOvfManager(vimClient)
	//
	//	archive := &importx.FileArchive{fpath}
	//
	//	cisp := types.OvfCreateImportSpecParams{
	//		DiskProvisioning:   "thin",
	//		EntityName:         *nameFlag,
	//		IpAllocationPolicy: "dhcpPolicy",
	//		IpProtocol:         "IPv4",
	//		OvfManagerCommonParams: types.OvfManagerCommonParams{
	//			DeploymentOption: "small",
	//			Locale:           "US"},
	//		PropertyMapping:.Map(.Options.PropertyMapping),
	//	}
	//
	//	ovfManager.CreateDescriptor()

}


func newClient(u *url.URL) (*vim25.Client, error) {
	sc := soap.NewClient(u, true)
	isTunnel := false

	// Add retry functionality before making any calls
	rt := attachRetries(sc)
	c, err := vim25.NewClient(context.TODO(), rt)
	if err != nil {
		return nil, err
	}

	// Set client, since we didn't pass it in the constructor
	c.Client = sc

	m := session.NewManager(c)
	user := u.User
	if isTunnel {
		err = m.LoginExtensionByCertificate(context.TODO(), user.Username(), "")
		if err != nil {
			return nil, err
		}
	} else {
		err = m.Login(context.TODO(), user)
		if err != nil {
			return nil, err
		}
	}

	return c, nil
}

// Retry twice when a temporary I/O error occurs.
// This means a maximum of 3 attempts.
func attachRetries(rt soap.RoundTripper) soap.RoundTripper {
	return vim25.Retry(rt, vim25.TemporaryNetworkError(3))
}

func ReadOvf(archive *importx.FileArchive, fpath string) ([]byte, error) {
	r, _, err := archive.Open(fpath)
	if err != nil {
		return nil, err
	}
	defer r.Close()

	return ioutil.ReadAll(r)
}

func ReadEnvelope(archive *importx.FileArchive, fpath string) (*ovf.Envelope, error) {
	if fpath == "" {
		return nil, nil
	}

	r, _, err := archive.Open(fpath)
	if err != nil {
		return nil, err
	}
	defer r.Close()

	e, err := ovf.Unmarshal(r)
	if err != nil {
		return nil, fmt.Errorf("failed to parse ovf: %s", err.Error())
	}

	return e, nil
}

func findSubFolder(f *find.Finder ,rootFolder *object.Folder, name string) (*object.Folder, error) {
	fmt.Printf("Searching folder %s for %s ...\n", rootFolder.String(), name)
	if strings.Contains(rootFolder.String(), name) {
		fmt.Printf("Found folder %s\n", rootFolder.String())
		return rootFolder, nil
	}
	children, err := rootFolder.Children(context.TODO())
	if err != nil {
		return nil, err
	}
	for _, child := range children {
		fmt.Printf("child %s:%s...\n", child.Reference().Type, child.Reference().Value)
		if subFolder, ok := child.(*object.Folder); ok {
			fmt.Printf("Subfolder %s ...\n", subFolder.String())
			subSubFolder, err := findSubFolder(f, subFolder, name)
			if err == nil {
				return subSubFolder, nil
			}
		}
		if datastore, ok := child.(*object.Datastore); ok {
			fmt.Printf("datastore %s ...\n", datastore.String())
			b, err := datastore.Browser(context.TODO())
			if err != nil {
				return nil, err
			}
			spec := types.HostDatastoreBrowserSearchSpec{
				MatchPattern: []string{"*"},
			}
			fmt.Printf("searching datastore...\n")
			searchResults, err := ListPath(b, datastore, spec)
			if err != nil {
				return nil, err
			}
			fmt.Printf("search results %v...\n", searchResults)
			for _, file := range searchResults.File {
				filePath := file.GetFileInfo().Path
				fmt.Printf("investigating file %v with path %s (root folder has path %s)...\n", file.GetFileInfo(), file.GetFileInfo().Path, rootFolder.InventoryPath)
				subFolder, err := f.FolderRecursive(context.TODO(), ".")
				if err != nil {
					fmt.Printf("file %s is not a folder (%s), moving on...\n", filePath, err)
				} else {
					subSubFolder, err := findSubFolder(f, subFolder, name)
					if err == nil {
						return subSubFolder, nil
					}
				}
			}
		}
	}
	return nil, errors.New("folder " + name + " not found")
}

func ListPath(b *object.HostDatastoreBrowser, datastore *object.Datastore, spec types.HostDatastoreBrowserSearchSpec) (types.HostDatastoreBrowserSearchResults, error) {
	var res types.HostDatastoreBrowserSearchResults

	path := "[datastore1]"

	fmt.Printf("listing path " + path + "\n")

	task, err := b.SearchDatastore(context.TODO(), path, &spec)
	if err != nil {
		return res, err
	}

	info, err := task.WaitForResult(context.TODO(), nil)
	if err != nil {
		return res, err
	}

	res = info.Result.(types.HostDatastoreBrowserSearchResults)
	return res, nil
}
