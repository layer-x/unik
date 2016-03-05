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

package com.vmware.vm;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import java.util.*;

/**
 * <pre>
 * VMPromoteDisks
 *
 * Used to consolidate a linked clone by using promote API.
 *
 * <b>Parameters:</b>
 * url              [required] : url of the web service
 * username         [required] : username for the authentication
 * password         [required] : password for the authentication
 * vmname           [required] : name of the virtual machine
 * unlink           [required] : True|False to unlink
 * devicenames      [optional] : disk name to unlink
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.vm.VMPromoteDisks --url [URLString] --username [User] --password [Password]
 * --vmname [VMName] --unlink [True|False] --devicenames [dname1:dname2...]
 * </pre>
 */
@Sample(
        name = "vm-promote-disks",
        description = "Used to consolidate a linked clone by using promote API."
)
public class VMPromoteDisks extends ConnectedVimServiceBase {

    String vmName = null;
    Boolean unLink = null;
    String diskNames = null;

    @Option(name = "vmname", description = "name of the virtual machine")
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    @Option(name = "unlink", description = "True|False to unlink")
    public void setUnLink(String unLink) {
        this.unLink = Boolean.valueOf(unLink);
    }

    @Option(name = "devicenames", required = false, description = "disk name to unlink")
    public void setDiskNames(String diskNames) {
        this.diskNames = diskNames;
    }

    /**
     * This method returns a boolean value specifying whether the Task is
     * succeeded or failed.
     *
     * @param task ManagedObjectReference representing the Task.
     * @return boolean value representing the Task result.
     * @throws InvalidCollectorVersionFaultMsg
     *
     * @throws RuntimeFaultFaultMsg
     * @throws InvalidPropertyFaultMsg
     */
    boolean getTaskResultAfterDone(ManagedObjectReference task)
            throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg,
            InvalidCollectorVersionFaultMsg {

        boolean retVal = false;

        // info has a property - state for state of the task
        Object[] result =
                waitForValues.wait(task, new String[]{"info.state", "info.error"},
                        new String[]{"state"}, new Object[][]{new Object[]{
                        TaskInfoState.SUCCESS, TaskInfoState.ERROR}});

        if (result[0].equals(TaskInfoState.SUCCESS)) {
            retVal = true;
        }
        if (result[1] instanceof LocalizedMethodFault) {
            throw new RuntimeException(
                    ((LocalizedMethodFault) result[1]).getLocalizedMessage());
        }
        return retVal;
    }

    /**
     * Returns all the MOREFs of the specified type that are present under the
     * container
     *
     * @param folder    {@link ManagedObjectReference} of the container to begin the
     *                  search from
     * @param morefType Type of the managed entity that needs to be searched
     * @return Map of name and MOREF of the managed objects present. If none
     *         exist then empty Map is returned
     * @throws InvalidPropertyFaultMsg
     * @throws RuntimeFaultFaultMsg
     */
    Map<String, ManagedObjectReference> getMOREFsInContainerByType(
            ManagedObjectReference folder, String morefType)
            throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        String PROP_ME_NAME = "name";
        ManagedObjectReference viewManager = serviceContent.getViewManager();
        ManagedObjectReference containerView =
                vimPort.createContainerView(viewManager, folder,
                        Arrays.asList(morefType), true);

        Map<String, ManagedObjectReference> tgtMoref =
                new HashMap<String, ManagedObjectReference>();

        // Create Property Spec
        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setAll(Boolean.FALSE);
        propertySpec.setType(morefType);
        propertySpec.getPathSet().add(PROP_ME_NAME);

        TraversalSpec ts = new TraversalSpec();
        ts.setName("view");
        ts.setPath("view");
        ts.setSkip(false);
        ts.setType("ContainerView");

        // Now create Object Spec
        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(containerView);
        objectSpec.setSkip(Boolean.TRUE);
        objectSpec.getSelectSet().add(ts);

        // Create PropertyFilterSpec using the PropertySpec and ObjectPec
        // created above.
        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getPropSet().add(propertySpec);
        propertyFilterSpec.getObjectSet().add(objectSpec);

        List<PropertyFilterSpec> propertyFilterSpecs =
                new ArrayList<PropertyFilterSpec>();
        propertyFilterSpecs.add(propertyFilterSpec);

        RetrieveResult rslts =
                vimPort.retrievePropertiesEx(serviceContent.getPropertyCollector(),
                        propertyFilterSpecs, new RetrieveOptions());
        List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();
        if (rslts != null && rslts.getObjects() != null
                && !rslts.getObjects().isEmpty()) {
            listobjcontent.addAll(rslts.getObjects());
        }
        String token = null;
        if (rslts != null && rslts.getToken() != null) {
            token = rslts.getToken();
        }
        while (token != null && !token.isEmpty()) {
            rslts =
                    vimPort.continueRetrievePropertiesEx(
                            serviceContent.getPropertyCollector(), token);
            token = null;
            if (rslts != null) {
                token = rslts.getToken();
                if (rslts.getObjects() != null && !rslts.getObjects().isEmpty()) {
                    listobjcontent.addAll(rslts.getObjects());
                }
            }
        }
        for (ObjectContent oc : listobjcontent) {
            ManagedObjectReference mr = oc.getObj();
            String entityNm = null;
            List<DynamicProperty> dps = oc.getPropSet();
            if (dps != null) {
                for (DynamicProperty dp : dps) {
                    entityNm = (String) dp.getVal();
                }
            }
            tgtMoref.put(entityNm, mr);
        }
        return tgtMoref;
    }

    void promoteDeltaDisk() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, TaskInProgressFaultMsg, InvalidStateFaultMsg, InvalidPowerStateFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference vmRef =
                getMOREFsInContainerByType(serviceContent.getRootFolder(),
                        "VirtualMachine").get(vmName);

        boolean unlink = Boolean.valueOf(unLink);
        List<VirtualDisk> vDiskList = new ArrayList<VirtualDisk>();
        if (vmRef != null) {
            if (diskNames != null) {
                String disknames = diskNames;
                String[] diskArr = disknames.split(":");
                Map<String, String> disks = new HashMap<String, String>();
                for (String disk : diskArr) {
                    disks.put(disk, null);
                }
                List<VirtualDevice> devices =
                        ((ArrayOfVirtualDevice) getMOREFs.entityProps(vmRef,
                                new String[]{"config.hardware.device"}).get(
                                "config.hardware.device")).getVirtualDevice();
                for (VirtualDevice device : devices) {
                    if (device instanceof VirtualDisk) {
                        if (disks.containsKey(device.getDeviceInfo().getLabel())) {
                            vDiskList.add((VirtualDisk) device);
                        }
                    }
                }
            }
            ManagedObjectReference taskMOR =
                    vimPort.promoteDisksTask(vmRef, unlink, vDiskList);
            if (getTaskResultAfterDone(taskMOR)) {
                System.out.println("Virtual Disks Promoted successfully.");
            } else {
                System.out.println("Failure -: Virtual Disks "
                        + "cannot be promoted");
            }
        } else {
            System.out.println("Virtual Machine " + vmName + " doesn't exist");
        }
    }

    @Action
    public void run() throws RuntimeFaultFaultMsg, TaskInProgressFaultMsg, InvalidPropertyFaultMsg, InvalidStateFaultMsg, InvalidCollectorVersionFaultMsg, InvalidPowerStateFaultMsg {
        promoteDeltaDisk();
    }
}
