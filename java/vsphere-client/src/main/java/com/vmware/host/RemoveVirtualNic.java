/*
 * ******************************************************
 * Copyright VMware, Inc. 2010-2012.  All Rights Reserved.
 * ******************************************************
 *
 * DISCLAIMER. THIS PROGRAM IS PROVIDED TO YOU "AS IS" WITHOUT
 * WARRANTIES OR CONDITIONS # OF ANY KIND, WHETHER ORAL OR WRITTEN,
 * EXPRESS OR IMPLIED. THE AUTHOR SPECIFICALLY # DISCLAIMS ANY IMPLIED
 * WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY # QUALITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.vmware.host;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * RemoveVirtualNic
 *
 * This sample removes a Virtual Nic from a PortGroup on a vSwitch
 *
 * <b>Parameters:</b>
 * url              [required] : url of the web service
 * username         [required] : username for the authentication
 * password         [required] : password for the authentication
 * portgroupname    [required] : Name of port group to remove Virtual Nic from
 * hostname         [required] : Name of host
 * datacentername   [optional] : Name of datacenter
 *
 * <b>Command Line:</b>
 * Remove a VirtualNic from a PortGroup
 * run.bat com.vmware.host.RemoveVirtualNic --url [webserviceurl]
 * --username [username] --password  [password] --datacentername [mydatacenter]
 * --portgroupname [myportgroup] --hostname [hostname]
 *
 * Remove a VirtualNic from a PortGroup without hostname
 * run.bat com.vmware.host.RemoveVirtualNic --url [webserviceurl]
 * --username [username] --password  [password] --datacentername [mydatacenter]
 * --portgroupname [myportgroup]
 *
 * Remove a VirtualNic from a PortGroup without datacentername
 * run.bat com.vmware.host.RemoveVirtualNic --url [webserviceurl]
 * --username [username] --password  [password]
 * --portgroupname [myportgroup] --hostname [name of the host]
 * </pre>
 */
@Sample(
        name = "remove-virtual-nic",
        description = "removes a Virtual Nic from a PortGroup on a vSwitch"
)
public class RemoveVirtualNic extends ConnectedVimServiceBase {
    ManagedObjectReference propCollectorRef = null;
    ManagedObjectReference rootFolder;

    String datacenter;
    String host;
    String portgroupname;

    @Option(name = "datacentername", required = false, description = "Name of datacenter")
    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    @Option(name = "hostname", description = "name of host")
    public void setHost(String host) {
        this.host = host;
    }

    @Option(name = "portgroupname", description = "Name of port group to remove Virtual Nic from")
    public void setPortgroupname(String portgroupname) {
        this.portgroupname = portgroupname;
    }

    void init() {
        propCollectorRef = serviceContent.getPropertyCollector();
        rootFolder = serviceContent.getRootFolder();
    }

    @SuppressWarnings("unchecked")
    void removeVirtualNic() {
        ManagedObjectReference dcmor;
        ManagedObjectReference hostfoldermor;
        ManagedObjectReference hostmor = null;

         try {
            if (((datacenter != null) && (host != null))
                    || ((datacenter != null) && (host == null))) {
                Map<String, ManagedObjectReference> dcResults = getMOREFs.inFolderByType(
                        serviceContent.getRootFolder(), "Datacenter", new RetrieveOptions());
                dcmor = dcResults.get(datacenter);
                if (dcmor == null) {
                    System.out.println("Datacenter not found");
                    return;
                }
                hostfoldermor = (ManagedObjectReference) getMOREFs.entityProps(dcmor,
                        new String[] { "hostFolder" }).get("hostFolder");
                Map<String, ManagedObjectReference> hostResults = getMOREFs.inFolderByType(
                        hostfoldermor, "HostSystem", new RetrieveOptions());
                hostmor = hostResults.get(host);
            } else if ((datacenter == null) && (host != null)) {
                Map<String, ManagedObjectReference> hostResults = getMOREFs.inFolderByType(
                        serviceContent.getRootFolder(), "HostSystem", new RetrieveOptions());
                hostmor = hostResults.get(host);
            }

            if (hostmor != null) {
                HostConfigManager configMgr = (HostConfigManager) getMOREFs.entityProps(hostmor,
                        new String[] { "configManager" }).get("configManager");
                ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
                ArrayOfHostVirtualNic arrayHostVirtualNic = (ArrayOfHostVirtualNic) getMOREFs
                        .entityProps(nwSystem, new String[] { "networkInfo.vnic" }).get(
                                "networkInfo.vnic");
                List<HostVirtualNic> hvncArr = arrayHostVirtualNic.getHostVirtualNic();
                boolean foundOne = false;
                for (HostVirtualNic nic : hvncArr) {
                    String portGroup = nic.getPortgroup();
                    if (portGroup.equals(portgroupname)) {
                        vimPort.removeVirtualNic(nwSystem, nic.getDevice());
                        foundOne = true;
                    }
                }
                if (foundOne) {
                    System.out
                            .println("Successfully removed virtual nic from portgroup : "
                                    + portgroupname);
                } else {
                    System.out.println("No virtual nic found on portgroup : "
                            + portgroupname);
                }
            } else {
                System.out.println("Host not found");
            }
        } catch (HostConfigFaultFaultMsg ex) {
            System.out.println("Failed : Configuration falilures. ");
        } catch (NotFoundFaultMsg ex) {
            System.out.println("Failed : " + ex);
        } catch (RuntimeFaultFaultMsg ex) {
            System.out.println("Failed : " + ex);
        } catch (Exception e) {
            System.out.println("Failed : " + e);
        }

    }

    @Action
    public void run() {
        init();
        removeVirtualNic();
    }

}
