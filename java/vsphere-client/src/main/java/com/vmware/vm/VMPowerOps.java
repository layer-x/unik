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
 * VMPowerOps
 *
 * Demonstrates VirtualMachine Power operations on multiple Virtual Machines. 
 * Works with groups of Virtual Machines all at one time.
 *
 * <b>Parameters:</b>
 * url              [required] : url of the web service
 * username         [required] : username for the authentication
 * password         [required] : password for the authentication
 * operation        [required] : type of the operation
 *                               [poweron | poweroff | reset | suspend |
 *                                reboot | shutdown | standby]
 * datacentername   [optional] : name of the datacenter
 * guestid          [optional] : guest id of the vm
 * hostname         [optional] : name of the host
 * vmname           [optional] : name of the virtual machine, use this option to send the power operation to only one virtual machine
 * all              [optional] : perform power operations on ALL virtual machines under our control. defaults to false. [true|false]
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.vm.VMPowerOps --url [URLString] --username [User] --password [Password]
 * --operation [Operation] --datacenter [DatacenterName]--guestid [GuestId] --hostname [HostName]
 * --vmname [VMName] --all [true|false]
 * </pre>
 */
@Sample(
        name = "vm-power-ops",
        description = "Demonstrates VirtualMachine Power operations on multiple Virtual Machines. "
            + "Works with groups of Virtual Machines all at one time. "
            + "You must specify one of --vmname or --datacentername or --hostname or --all "
            + "all Virtual Machines that match these criteria will have the power operation issued to them. "
            + "For example to power off all the virtual machines visible use the options "
            + " --operation poweroff --all true "
            + "together and all virtual machines will be turned off."
)
public class VMPowerOps extends ConnectedVimServiceBase {

    String vmName = null;
    String operation = null;
    String datacenter = null;
    String guestId = null;
    String host = null;
    Boolean all = false;

    @Option(
            name = "operation",
            description = "type of the operation\n" +
                    "[poweron | poweroff | reset | suspend |" +
                    " reboot | shutdown | standby]"
    )
    public void setOperation(String operation) {
        this.operation = operation;
    }


    @Option(name = "datacentername", required = false, description = "name of the datacenter. use this option to send power operations to all the virtual machines in an entire data center.")
    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    @Option(name = "guestid", required = false, description = "guest id of the vm. use this option to send power operations to a single guest.")
    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    @Option(name = "hostname", required = false, description = "name of the host. use this option to send power operations to all the virtual machines on a single host.")
    public void setHost(String host) {
        this.host = host;
    }

    @Option(name = "vmname", required = false, description = "name of the virtual machine. Use this option to send power operations to only this virtual machine.")
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    @Option(name = "all", required = false, description = "set to true to send power operation to all virtual machines that can be found. overrides all other options.")
    public void setAll(final Boolean flag) {
        this.all = flag;
    }

    void validate() throws IllegalArgumentException {
        if( all && (vmName != null || datacenter != null || guestId != null || host != null) ) {
            System.out.println("Did you really mean all? " + "Use '--all true' by itself " +
            "not with --vmname or --datacentername or --guestid or --hostname");
            throw new IllegalArgumentException("--all true occurred in conjunction with other options");
        }
        if (!(operation.equalsIgnoreCase("poweron"))
                && !(operation.equalsIgnoreCase("poweroff"))
                && !(operation.equalsIgnoreCase("reset"))
                && !(operation.equalsIgnoreCase("standby"))
                && !(operation.equalsIgnoreCase("shutdown"))
                && !(operation.equalsIgnoreCase("reboot"))
                && !(operation.equalsIgnoreCase("suspend"))) {

            System.out.println("Invalid Operation name ' " + operation
                    + "' valid operations are poweron, standby,"
                    + " poweroff, standby, reboot, shutdown, suspend");
            throw new IllegalArgumentException("Invalid Operation Type Or Name");
        }
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
     * This could be a list of every Virtual Machine in an entire vCenter's control, or
     * you can use --vmname to limit the list to a single virtual machine. Or, you could use
     * the --datacentername option to perform power operations on every virtual machine in
     * a data cater... or --hostname to perform power operations on every virtual machine on an ESX host!
     */
    Map<String, ManagedObjectReference> getVms() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        Map<String, ManagedObjectReference> vmList =
                new HashMap<String, ManagedObjectReference>();

        // Start from the root folder
        ManagedObjectReference container = serviceContent.getRootFolder();
        if (datacenter != null) {
            Map<String,ManagedObjectReference> datacenters =
                    getMOREFs.inContainerByType(container, "Datacenter");
            System.out.println("Number of datacenters found: " + datacenters.size());
            ManagedObjectReference dcMoref = datacenters.get(datacenter);
            if (dcMoref == null) {
                System.out.println("No datacenter by the name " + datacenter
                        + " found!");
            }
            container = dcMoref;
        }

        if (host != null) {
            ManagedObjectReference hostMoref =
                    getMOREFs.inContainerByType(container, "HostSystem").get(host);
            if (hostMoref == null) {
                System.out.println("No host by the name " + host + " found!");
                return vmList;
            }
            container = hostMoref;
        }

        Map<String, ManagedObjectReference> vms =
                getMOREFs.inContainerByType(container,"VirtualMachine");

        if (vmName != null) {
            if (vms.containsKey(vmName)) {
                vmList.put(vmName, vms.get(vmName));
            }
            else {
                throw new IllegalStateException("No VM by the name of '" + vmName + "' found!");
            }
            return vmList;
        }

        if (guestId != null) {
            Map<ManagedObjectReference, Map<String, Object>> vmListProp =
                    getMOREFs.entityProps(
                            new ArrayList<ManagedObjectReference>(vms.values()),
                            new String[]{"summary.config.guestId", "name"});
            for (ManagedObjectReference vmRef : vmListProp.keySet()) {
                if (guestId.equalsIgnoreCase((String) vmListProp.get(vmRef).get(
                        "summary.config.guestId"))) {
                    vmList.put((String) vmListProp.get(vmRef).get("name"), vmRef);
                }
            }
            return vmList;
        }

        // If no filters are there then just the container based containment is used.
        vmList = vms;

        return vmList;
    }

    void runOperation() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        Map<String, ManagedObjectReference> vmMap = getVms();
        if (vmMap == null || vmMap.isEmpty()) {
            System.out.println("No Virtual Machine found matching "
                    + "the specified criteria");
            return;
        } else {
            if (operation.equalsIgnoreCase("poweron")) {
                powerOnVM(vmMap);
            } else if (operation.equalsIgnoreCase("poweroff")) {
                powerOffVM(vmMap);
            } else if (operation.equalsIgnoreCase("reset")) {
                resetVM(vmMap);
            } else if (operation.equalsIgnoreCase("suspend")) {
                suspendVM(vmMap);
            } else if (operation.equalsIgnoreCase("reboot")) {
                rebootVM(vmMap);
            } else if (operation.equalsIgnoreCase("shutdown")) {
                shutdownVM(vmMap);
            } else if (operation.equalsIgnoreCase("standby")) {
                standbyVM(vmMap);
            }
        }
    }

    void powerOnVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Powering on virtual machine : " + vmname + "["
                        + vmMor.getValue() + "]");
                ManagedObjectReference taskmor = vimPort.powerOnVMTask(vmMor, null);
                if (getTaskResultAfterDone(taskmor)) {
                    System.out.println(vmname + "[" + vmMor.getValue()
                            + "] powered on successfully");
                }
            } catch (Exception e) {
                System.out.println("Unable to poweron vm : " + vmname + "["
                        + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void powerOffVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Powering off virtual machine : " + vmname + "["
                        + vmMor.getValue() + "]");
                ManagedObjectReference taskmor = vimPort.powerOffVMTask(vmMor);
                if (getTaskResultAfterDone(taskmor)) {
                    System.out.println(vmname + "[" + vmMor.getValue()
                            + "] powered off successfully");
                }
            } catch (Exception e) {
                System.out.println("Unable to poweroff vm : " + vmname + "["
                        + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void resetVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Reseting virtual machine : " + vmname + "["
                        + vmMor.getValue() + "]");
                ManagedObjectReference taskmor = vimPort.resetVMTask(vmMor);
                if (getTaskResultAfterDone(taskmor)) {
                    System.out.println(vmname + "[" + vmMor.getValue()
                            + "] reset successfully");
                }
            } catch (Exception e) {
                System.out.println("Unable to reset vm : " + vmname + "["
                        + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void suspendVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Suspending virtual machine : " + vmname + "["
                        + vmMor.getValue() + "]");
                ManagedObjectReference taskmor = vimPort.suspendVMTask(vmMor);
                if (getTaskResultAfterDone(taskmor)) {
                    System.out.println(vmname + "[" + vmMor.getValue()
                            + "] suspended successfully");
                }
            } catch (Exception e) {
                System.out.println("Unable to suspend vm : " + vmname + "["
                        + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void rebootVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Rebooting guest os in virtual machine : "
                        + vmname + "[" + vmMor.getValue() + "]");
                vimPort.rebootGuest(vmMor);
                System.out.println("Guest os in vm : " + vmname + "["
                        + vmMor.getValue() + "]" + " rebooted");
            } catch (Exception e) {
                System.out.println("Unable to reboot guest os in vm : " + vmname
                        + "[" + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void shutdownVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Shutting down guest os in virtual machine : "
                        + vmname + "[" + vmMor.getValue() + "]");
                vimPort.shutdownGuest(vmMor);
                System.out.println("Guest os in vm : " + vmname + "["
                        + vmMor.getValue() + "]" + " shutdown");
            } catch (Exception e) {
                System.out.println("Unable to shutdown guest os in vm : " + vmname
                        + "[" + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    void standbyVM(Map<String, ManagedObjectReference> vmMap) {
        for (String vmname : vmMap.keySet()) {
            ManagedObjectReference vmMor = vmMap.get(vmname);
            try {
                System.out.println("Putting the guest os in virtual machine : "
                        + vmname + "[" + vmMor.getValue() + "] in standby mode");
                vimPort.standbyGuest(vmMor);
                System.out.println("Guest os in vm : " + vmname + "["
                        + vmMor.getValue() + "]" + " in standby mode");
            } catch (Exception e) {
                System.out.println("Unable to put the guest os in vm : " + vmname
                        + "[" + vmMor.getValue() + "] to standby mode");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    @Action
    public void run() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        validate();
        if( checkOptions() )
            runOperation();
    }

    /**
     * The user must specify one of vmName or datacenter or host ...
     * We add this check here to help prevent programmers from power cycling all the virtual
     * machines on their entire vCenter server on accident.
     */
    public boolean checkOptions() {
        boolean run = false;

        if(all) {
            // force operations to broadcast to ALL virtual machines.
            vmName = null;
            datacenter = null;
            host = null;
            System.out.println("Power operations will be broadcast to ALL virtual machines.");
            run = true;
        }
        else if( vmName == null
            && datacenter == null
            && guestId == null
            && host == null
            && System.console() != null )
        {
            throw new IllegalStateException("You must specify one of --vmname or --datacentername or --hostname or --all");
        }
        else {
            run = true;
        }

        return run;
    }
}
