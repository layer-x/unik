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

package com.vmware.storage;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import javax.xml.soap.SOAPException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * This sample demonstrates how to Add/List/Modify/Delete the rules for an
 *  existing SDRS cluster.
 * Parameters:
 * url                       [required]: url of the web service.
 * username                  [required]: username for the authentication
 * Password                  [required]: password for the authentication
 * podname                   [required]: StoragePod name.
 * option                    [required]:  valid option are "addVmAntiAffinity",
 *                           "addVmdkAntiAffinity", "list", "modifyVmAntiAffinity",
 *                           "modifyVmdkAntiAffinity", "deleteVmAntiAffinity" and
 *                           "deleteVmdkAntiAffinity".
 * rulename                  Rule name.
 * vmlist                    Comma separated, list of VM name. It is required while
 *                           adding VmAntiAffinity Rule.
 * newrulename               New name for rule while modifying
 * enable                    Flag to indicate whether or not the rule is enabled.
 * vmname                    virtual machine name.
 *
 * <b>Sample usage:</b>
 * addVmAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 * [Password] --option addVmAntiAffinity --podname [podname] --rulename [rulename]
 *  --enable [enable] --vmlist [vmlist]
 *
 * addVmdkAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option addVmdkAntiAffinity --podname [podname] --rulename [rulename]
 *   --enable [enable] --vmname [vmname]
 *
 * List Rules:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option list --podname [podname]
 *
 * modifyVmAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option modifyVmAntiAffinity  --podname [podname]  --rulename [rulename]
 *  --enable [enable] --vmname [vmname] --newrulename [newrulename]
 *
 * modifyVmdkAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option modifyVmdkAntiAffinity --podname [podname] --rulename [rulename]
 *  --enable [enable] --newrulename [newrulename]
 *
 * deleteVmAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option deleteVmAntiAffinity --podname [podname] --rulename [rulename]
 *
 * deleteVmdkAntiAffinity Rule:
 * run.bat com.vmware.storage.SDRSRules --url [URLString] --username [User] --password
 *  [Password] --option deleteVmdkAntiAffinity  --podname [podname] --rulename [rulename]
 *
 * NOTE: All the virtual disks will be added while adding Vmdk AntiAffinity Rule.
 * </pre>
 */
@Sample(name = "sdrs-rules", description = "This sample demonstrates " +
        "how to Add/List/Modify/Delete the rules for an\n" +
        "existing SDRS cluster.")
public class SDRSRules extends ConnectedVimServiceBase {
    static final String[] validOptions = {"addVmAntiAffinity", "addVmdkAntiAffinity", "list", "modifyVmAntiAffinity",
            "modifyVmdkAntiAffinity", "deleteVmAntiAffinity", "deleteVmdkAntiAffinity"};

    private ManagedObjectReference propCollectorRef;

    String option = null;
    String storagePodName = null;
    String ruleName = null;
    String newRuleName = null;
    Boolean enabled = null;
    String vmName = null;
    List<String> vm = new ArrayList<String>();

    @Option(name = "podname", description = "StoragePod name.")
    public void setPodName(String podName) {
        this.storagePodName = podName;
    }

    @Option(
            name = "option",
            description = "valid option are: \"addVmAntiAffinity\",\n" +
                    "\"addVmdkAntiAffinity\", \"list\", \"modifyVmAntiAffinity\",\n" +
                    "\"modifyVmdkAntiAffinity\", \"deleteVmAntiAffinity\" and\n" +
                    "\"deleteVmdkAntiAffinity\"."
    )
    public void setOption(String opt) {
        this.option = opt;
    }

    @Option(name = "rulename", required = false, description = "Rule name.")
    public void setRuleName(String name) {
        this.ruleName = name;
    }

    @Option(
            name = "vmlist",
            required = false,
            description =
                    "Comma separated,\n" +
                            "list of VM name. It is required while\n" +
                            "adding VmAntiAffinity Rule."
    )
    public void setVmList(String list) {
        String[] names = list.split(",");
        for (String name : names) {
            vm.add(name);
        }
    }

    @Option(name = "newrulename", required = false, description = "New name for rule while modifying")
    public void setNewRuleName(String newName) {
        this.newRuleName = newName;
    }

    @Option(
            name = "enable",
            required = false,
            description = "Flag to indicate whether or not the rule is enabled."
    )
    public void setEnabled(String enable) {
        // NOTE: documentation says "enable" while code elsewhere says "true" this supports both
        this.enabled = new Boolean(
                "true".equalsIgnoreCase(enable) || "enable".equalsIgnoreCase(enable)
        );
    }

    @Option(name = "vmname", required = false, description = "virtual machine name.")
    public void setVmName(String name) {
        this.vmName = name;
    }


    void validate() {
        if (!isValidOption(option)) {
            throw new IllegalArgumentException("the option '--option " + option + "' is not a valid value");
        }
    }

    boolean isValidOption(String option) {
        boolean found = false;

        for (String it : validOptions) {
            if (it.equals(option)) {
                found = true;
                break;
            }
        }

        return found;
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
     * Add VmAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be added.
     * @param enabled        Flag to indicate whether or not the rule is enabled.
     * @param vm             list of VMs that needs to be added in the Rule
     * @throws RemoteException
     */
    void addVmAntiAffinityRule(String storagePodName,
                               String ruleName, boolean enabled, List<String> vm)
            throws RemoteException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> results = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = results.get(storagePodName);
        if (storagePod != null) {
            ManagedObjectReference vmMoref = null;
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsPodConfigSpec podConfigSpec = new StorageDrsPodConfigSpec();
            ClusterAntiAffinityRuleSpec vmAntiAffinityRuleSpec =
                    new ClusterAntiAffinityRuleSpec();
            ClusterRuleSpec ruleSpec = new ClusterRuleSpec();
            vmAntiAffinityRuleSpec.setName(ruleName);
            if (enabled) {
                vmAntiAffinityRuleSpec.setEnabled(true);
            } else {
                vmAntiAffinityRuleSpec.setEnabled(false);
            }
            for (String vmname : vm) {
                vmMoref = getMOREFs.vmByVMname(vmName, propCollectorRef);
                if (vmMoref != null) {
                    vmAntiAffinityRuleSpec.getVm().add(vmMoref);
                } else {
                    String msg =
                            "\nFailure: Virtual Machine " + vmname + " not found.";
                    throw new RuntimeException(msg);
                }
            }
            vmAntiAffinityRuleSpec.setUserCreated(true);
            vmAntiAffinityRuleSpec.setMandatory(false);
            ruleSpec.setInfo(vmAntiAffinityRuleSpec);
            ruleSpec.setOperation(ArrayUpdateOperation.ADD);
            podConfigSpec.getRule().add(ruleSpec);
            sdrsConfigSpec.setPodConfigSpec(podConfigSpec);
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Adding VmAntiAffinity Rule.");
            } else {
                String msg = "\nFailure: Adding VmAntiAffinity Rule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * Add VmdkAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be added.
     * @param enabled        Flag to indicate whether or not the rule is enabled.
     * @param vmName         VM for which the rule needs to be added.
     * @throws RemoteException
     */
    void addVmdkAntiAffinityRule(String storagePodName,
                                 String ruleName, boolean enabled, String vmName)
            throws RemoteException, SOAPException, RuntimeFaultFaultMsg, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            ManagedObjectReference vmMoref = null;
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsVmConfigSpec drsVmConfigSpec = new StorageDrsVmConfigSpec();
            StorageDrsVmConfigInfo drsVmConfigInfo = new StorageDrsVmConfigInfo();
            VirtualDiskAntiAffinityRuleSpec vmdkAntiAffinityRuleSpec =
                    new VirtualDiskAntiAffinityRuleSpec();
            vmdkAntiAffinityRuleSpec.setName(ruleName);
            if (enabled) {
                vmdkAntiAffinityRuleSpec.setEnabled(true);
            } else {
                vmdkAntiAffinityRuleSpec.setEnabled(false);
            }
            vmMoref = getMOREFs.vmByVMname(vmName, propCollectorRef);
            if (vmMoref != null) {
                VirtualMachineConfigInfo vmConfigInfo = (VirtualMachineConfigInfo) getMOREFs
                        .entityProps(vmMoref, new String[] { "config" }).get("config");
                List<VirtualDevice> vDevice =
                        vmConfigInfo.getHardware().getDevice();
                List<VirtualDevice> virtualDisk = new ArrayList<VirtualDevice>();
                for (VirtualDevice device : vDevice) {
                    if (device.getClass().getSimpleName()
                            .equalsIgnoreCase("VirtualDisk")) {
                        virtualDisk.add(device);
                        vmdkAntiAffinityRuleSpec.getDiskId().add(device.getKey());
                    }
                }
                if (virtualDisk.size() < 2) {
                    throw new SOAPException(
                            "VM should have minimum of 2 virtual disks"
                                    + " while adding VMDK AntiAffinity Rule.");
                }
                System.out.println("Adding below list of virtual disk to rule "
                        + ruleName + " :");
                for (VirtualDevice device : virtualDisk) {
                    System.out.println("Virtual Disk : "
                            + device.getDeviceInfo().getLabel() + ", Key : "
                            + device.getKey());
                }
                vmdkAntiAffinityRuleSpec.setUserCreated(true);
                vmdkAntiAffinityRuleSpec.setMandatory(false);
                drsVmConfigInfo.setIntraVmAntiAffinity(vmdkAntiAffinityRuleSpec);
                drsVmConfigInfo.setVm(vmMoref);
            } else {
                String msg = "\nFailure: Virtual Machine " + vmName + " not found.";
                throw new RuntimeException(msg);
            }
            drsVmConfigSpec.setInfo(drsVmConfigInfo);
            drsVmConfigSpec.setOperation(ArrayUpdateOperation.EDIT);
            sdrsConfigSpec.getVmConfigSpec().add(drsVmConfigSpec);
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Adding VmdkAntiAffinity Rule.");
            } else {
                String msg = "\nFailure: Adding VmdkAntiAffinity Rule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * List Rules for a StoragePod.
     *
     * @param storagePodName StoragePod name.
     * @throws InvalidPropertyFaultMsg
     * @throws RuntimeFaultFaultMsg
     */
    void listRules(String storagePodName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            PodStorageDrsEntry podSDrsEntry = (PodStorageDrsEntry) getMOREFs.entityProps(
                    storagePod, new String[] { "podStorageDrsEntry" }).get("podStorageDrsEntry");
            System.out.println("\n List of VM anti-affinity rules: ");
            List<ClusterRuleInfo> vmRuleSpec =
                    podSDrsEntry.getStorageDrsConfig().getPodConfig().getRule();
            for (ClusterRuleInfo vmRule : vmRuleSpec) {
                System.out.println(vmRule.getName());
            }
            System.out.println("\n List of VMDK anti-affinity rules: ");
            List<StorageDrsVmConfigInfo> vmConfig =
                    podSDrsEntry.getStorageDrsConfig().getVmConfig();
            for (StorageDrsVmConfigInfo sdrsVmConfig : vmConfig) {
                if (sdrsVmConfig.getIntraVmAntiAffinity() != null) {
                    System.out.println(sdrsVmConfig.getIntraVmAntiAffinity()
                            .getName());
                }
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * Modify VmAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be modified.
     * @param newRuleName    new name for the rule.
     * @param enabled        Flag to indicate whether or not the rule is enabled.
     * @param vmName         VM to be added to the list of VMs in the Rule.
     * @throws RemoteException
     * @throws Exception
     */
    void modifyVmAntiAffinityRule(String storagePodName,
                                  String ruleName, String newRuleName, Boolean enabled, String vmName)
            throws RemoteException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            PodStorageDrsEntry podSDrsEntry = (PodStorageDrsEntry) getMOREFs.entityProps(
                    storagePod, new String[] { "podStorageDrsEntry" }).get("podStorageDrsEntry");
            ClusterRuleSpec ruleSpec = new ClusterRuleSpec();
            List<ClusterRuleInfo> vmRuleInfo =
                    podSDrsEntry.getStorageDrsConfig().getPodConfig().getRule();
            ManagedObjectReference vmMoref = null;
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsPodConfigSpec podConfigSpec = new StorageDrsPodConfigSpec();
            ClusterAntiAffinityRuleSpec vmAntiAffinityRuleSpec = null;
            for (ClusterRuleInfo vmRule : vmRuleInfo) {
                if (vmRule.getName().equalsIgnoreCase(ruleName)) {
                    vmAntiAffinityRuleSpec = (ClusterAntiAffinityRuleSpec) vmRule;
                }
            }
            if (vmAntiAffinityRuleSpec != null) {
                if (newRuleName != null) {
                    vmAntiAffinityRuleSpec.setName(newRuleName);
                }
                if (enabled != null) {
                    if (enabled) {
                        vmAntiAffinityRuleSpec.setEnabled(true);
                    } else {
                        vmAntiAffinityRuleSpec.setEnabled(false);
                    }
                }
                if (vmName != null) {
                    vmMoref = getMOREFs.vmByVMname(vmName, propCollectorRef);
                    if (vmMoref != null) {
                        vmAntiAffinityRuleSpec.getVm().add(vmMoref);
                    } else {
                        String msg =
                                "\nFailure: Virtual Machine " + vmName + " not found.";
                        throw new RuntimeException(msg);
                    }
                }
                ruleSpec.setInfo(vmAntiAffinityRuleSpec);
                ruleSpec.setOperation(ArrayUpdateOperation.EDIT);
                podConfigSpec.getRule().add(ruleSpec);
                sdrsConfigSpec.setPodConfigSpec(podConfigSpec);
            } else {
                String msg = "\nFailure: Rule " + ruleName + " not found.";
                throw new RuntimeException(msg);
            }
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Modifying VmAntiAffinityRule.");
            } else {
                String msg = "\nFailure: Modifying VmAntiAffinityRule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * Modify VmdkAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be modified.
     * @param newRuleName    new name for the rule.
     * @param enabled        Flag to indicate whether or not the rule is enabled.
     * @throws RemoteException
     */
    void modifyVmdkAntiAffinityRule(String storagePodName,
                                    String ruleName, String newRuleName, Boolean enabled)
            throws RemoteException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            PodStorageDrsEntry podSDrsEntry = (PodStorageDrsEntry) getMOREFs.entityProps(
                    storagePod, new String[] { "podStorageDrsEntry" }).get("podStorageDrsEntry");
            StorageDrsVmConfigInfo drsVmConfigInfo = null;
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsVmConfigSpec drsVmConfigSpec = new StorageDrsVmConfigSpec();
            List<StorageDrsVmConfigInfo> sdrsVmConfig =
                    podSDrsEntry.getStorageDrsConfig().getVmConfig();
            for (StorageDrsVmConfigInfo vmConfig : sdrsVmConfig) {
                if (vmConfig.getIntraVmAntiAffinity() != null) {
                    if (vmConfig.getIntraVmAntiAffinity().getName()
                            .equalsIgnoreCase(ruleName)) {
                        drsVmConfigInfo = vmConfig;
                    }
                }
            }
            if (drsVmConfigInfo != null) {
                if (newRuleName != null) {
                    drsVmConfigInfo.getIntraVmAntiAffinity().setName(newRuleName);
                }
                if (enabled != null) {
                    if (enabled) {
                        drsVmConfigInfo.getIntraVmAntiAffinity().setEnabled(true);
                    } else {
                        drsVmConfigInfo.getIntraVmAntiAffinity().setEnabled(false);
                    }
                }
                drsVmConfigSpec.setInfo(drsVmConfigInfo);
                drsVmConfigSpec.setOperation(ArrayUpdateOperation.EDIT);
                sdrsConfigSpec.getVmConfigSpec().add(drsVmConfigSpec);
            } else {
                String msg = "\nFailure: Rule " + ruleName + " not found.";
                throw new RuntimeException(msg);
            }
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Modifying VmdkAntiAffinityRule.");
            } else {
                String msg = "\nFailure: Modifying VmdkAntiAffinityRule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * Delete VmAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be deleted.
     * @throws RemoteException
     */
    void deleteVmAntiAffinityRule(String storagePodName,
                                  String ruleName) throws RemoteException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            PodStorageDrsEntry podSDrsEntry = (PodStorageDrsEntry) getMOREFs.entityProps(
                    storagePod, new String[] { "podStorageDrsEntry" }).get("podStorageDrsEntry");
            ClusterRuleSpec ruleSpec = new ClusterRuleSpec();
            List<ClusterRuleInfo> vmRuleInfo =
                    podSDrsEntry.getStorageDrsConfig().getPodConfig().getRule();
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsPodConfigSpec podConfigSpec = new StorageDrsPodConfigSpec();
            ClusterAntiAffinityRuleSpec vmAntiAffinityRuleSpec = null;
            for (ClusterRuleInfo vmRule : vmRuleInfo) {
                if (vmRule.getName().equalsIgnoreCase(ruleName)) {
                    vmAntiAffinityRuleSpec = (ClusterAntiAffinityRuleSpec) vmRule;
                }
            }
            if (vmAntiAffinityRuleSpec != null) {
                ruleSpec.setInfo(vmAntiAffinityRuleSpec);
                ruleSpec.setOperation(ArrayUpdateOperation.REMOVE);
                ruleSpec.setRemoveKey(vmAntiAffinityRuleSpec.getKey());
                podConfigSpec.getRule().add(ruleSpec);
                sdrsConfigSpec.setPodConfigSpec(podConfigSpec);
            } else {
                String msg = "\nFailure: Rule " + ruleName + " not found.";
                throw new RuntimeException(msg);
            }
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Deleting VmAntiAffinity Rule.");
            } else {
                String msg = "\nFailure: Deleting VmAntiAffinity Rule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    /**
     * Delete VmdkAntiAffinity Rule.
     *
     * @param storagePodName StoragePod name.
     * @param ruleName       Name of the rule to be deleted.
     * @throws RemoteException
     */
    void deleteVmdkAntiAffinityRule(String storagePodName,
                                    String ruleName) throws RemoteException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference storageResourceManager =
                serviceContent.getStorageResourceManager();
        Map<String, ManagedObjectReference> result = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "StoragePod", new RetrieveOptions());
        ManagedObjectReference storagePod = result.get(storagePodName);
        if (storagePod != null) {
            PodStorageDrsEntry podSDrsEntry = (PodStorageDrsEntry) getMOREFs.entityProps(
                    storagePod, new String[] { "podStorageDrsEntry" }).get("podStorageDrsEntry");
            StorageDrsVmConfigInfo drsVmConfigInfo = null;
            StorageDrsConfigSpec sdrsConfigSpec = new StorageDrsConfigSpec();
            StorageDrsVmConfigSpec drsVmConfigSpec = new StorageDrsVmConfigSpec();
            List<StorageDrsVmConfigInfo> sdrsVmConfig =
                    podSDrsEntry.getStorageDrsConfig().getVmConfig();
            for (StorageDrsVmConfigInfo vmConfig : sdrsVmConfig) {
                if (vmConfig.getIntraVmAntiAffinity() != null) {
                    if (vmConfig.getIntraVmAntiAffinity().getName()
                            .equalsIgnoreCase(ruleName)) {
                        drsVmConfigInfo = vmConfig;
                    }
                }
            }
            if (drsVmConfigInfo != null) {
                drsVmConfigInfo.setIntraVmAntiAffinity(null);
                drsVmConfigSpec.setInfo(drsVmConfigInfo);
                drsVmConfigSpec.setOperation(ArrayUpdateOperation.EDIT);
                sdrsConfigSpec.getVmConfigSpec().add(drsVmConfigSpec);
            } else {
                String msg = "\nFailure: Rule " + ruleName + " not found.";
                throw new RuntimeException(msg);
            }
            ManagedObjectReference taskmor =
                    vimPort.configureStorageDrsForPodTask(storageResourceManager,
                            storagePod, sdrsConfigSpec, true);
            if (getTaskResultAfterDone(taskmor)) {
                System.out.printf("\nSuccess: Deleting VmdkAntiAffinity Rule.");
            } else {
                String msg = "\nFailure: Deleting VmdkAntiAffinity Rule.";
                throw new RuntimeException(msg);
            }
        } else {
            String msg = "\nFailure: StoragePod " + storagePodName + " not found.";
            throw new RuntimeException(msg);
        }
    }

    @Action
    public void run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg, RemoteException, SOAPException {
        validate();
        propCollectorRef = serviceContent.getPropertyCollector();

        if (option.equalsIgnoreCase("list")) {
            listRules(storagePodName);
        } else if (option.equalsIgnoreCase("addVmAntiAffinity")) {
            addVmAntiAffinityRule(storagePodName, ruleName, enabled, vm);
        } else if (option.equalsIgnoreCase("addVmdkAntiAffinity")) {
            addVmdkAntiAffinityRule(storagePodName, ruleName, enabled, vmName);
        } else if (option.equalsIgnoreCase("modifyVmAntiAffinity")) {
            modifyVmAntiAffinityRule(storagePodName, ruleName, newRuleName,
                    enabled, vmName);
        } else if (option.equalsIgnoreCase("modifyVmdkAntiAffinity")) {
            modifyVmdkAntiAffinityRule(storagePodName, ruleName, newRuleName,
                    enabled);
        } else if (option.equalsIgnoreCase("deleteVmAntiAffinity")) {
            deleteVmAntiAffinityRule(storagePodName, ruleName);
        } else if (option.equalsIgnoreCase("deleteVmdkAntiAffinity")) {
            deleteVmdkAntiAffinityRule(storagePodName, ruleName);
        }
    }
}
