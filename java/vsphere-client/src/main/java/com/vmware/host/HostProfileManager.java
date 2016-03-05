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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * This sample demonstrates HostProfileManager and ProfileComplainceManager
 *
 * <b>Parameters:</b>
 * url              [required] : url of the web service
 * username         [required] : username for the authentication
 * password         [required] : password for the authentication
 * sourcehostname   [required] : Name of the host
 * entityname       [required] : Attached Entity Name
 * entitytype       [required] : Attached Entity Type
 *
 * <b>Command Line:</b>
 * Create hostprofile given profileSourceHost (host system)
 * profileAttachEntity (host system), profileAttachEntityType (host system)
 * Applies config after attaching hostprofile to host system and check for compliance");
 * run.bat com.vmware.host.HostProfileManager --url [webserviceurl]");
 * --username [username] --password [password] --sourcehostname [host name]
 * --entityname [host name] --entitytype HostSystem
 *
 * Create hostprofile given profileSourceHost (host system),
 * profileAttachEntity (cluster computer resource), profileAttachEntityType
 * (cluster compute resource)
 * Attaches hostprofile to all hosts in cluster and checks for compliance
 * run.bat com.vmware.host.HostProfileManager --url [webserviceurl]
 * --username [username] --password  [password] --sourcehostname [host name]
 * --entityname [Cluster] --entitytype ClusterComputeResource
 * </pre>
 */
@Sample(
        name = "host-profile-manager",
        description = "demonstrates HostProfileManager and ProfileComplainceManager "
                + "\n\n"
                + "NOTE: this command may place a host into maintenance mode which will require VMs on the host to be suspended."
                + "\n\n"
                + "Command:\n\n"
                + "Create hostprofile given profileSourceHost (host system), "
                + " profileAttachEntity (host system), profileAttachEntityType (host system)\n\n"
                + "Applies config after attaching hostprofile to "
                + " host system and check for compliance "
                + "run.bat com.vmware.host.HostProfileManager --url [webserviceurl] "
                + "--username [username] --password [password] --sourcehostname [host name] "
                + "--entityname [host name] --entitytype HostSystem "
                + "Create hostprofile given profileSourceHost (host system), "
                + " profileAttachEntity (cluster computer resource), profileAttachEntityType "
                + "(cluster compute resource)\n\n"
                + "Attaches hostprofile to all hosts in "
                + " cluster and checks for compliance \n\n"
                + "run.bat com.vmware.host.HostProfileManager --url [webserviceurl]"
                + "--username [username] --password  [password] "
                + " --sourcehostname [host name] \n\n"
                + "--entityname [Cluster] --entitytype ClusterComputeResource"

)
public class HostProfileManager extends ConnectedVimServiceBase {

    private ManagedObjectReference hostprofileManager;
    private ManagedObjectReference profilecomplianceManager;

    private String createHostEntityName;
    private String attachEntityName;
    private String attachEntityType;
    private List<ManagedObjectReference> suspendedVMList;

    @Option(name = "sourcehostname", description = "Name of the host")
    public void setCreateHostEntityName(String createHostEntityName) {
        this.createHostEntityName = createHostEntityName;
    }

    @Option(name = "entityname", description = "Attached Entity Name")
    public void setAttachEntityName(String attachEntityName) {
        this.attachEntityName = attachEntityName;
    }

    @Option(name = "entitytype", description = "Attached Entity Type, example: HostSystem or ClusterComputeResource")
    public void setAttachEntityType(String attachEntityType) {
        this.attachEntityType = attachEntityType;
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
     * Create a profile from the specified CreateSpec.
     * HostProfileHostBasedConfigSpec is created from the hostEntitymoref
     * (create_host_entity_name) reference. Using this spec a hostProfile is
     * created.
     *
     * @param hostEntitymoref
     * @return
     * @throws DuplicateName
     * @throws RuntimeFault
     * @throws RemoteException
     */
    ManagedObjectReference createHostProfile(
            ManagedObjectReference hostEntitymoref) throws DuplicateNameFaultMsg,
            RuntimeFaultFaultMsg {
        HostProfileHostBasedConfigSpec hostProfileHostBasedConfigSpec =
                new HostProfileHostBasedConfigSpec();
        hostProfileHostBasedConfigSpec.setHost(hostEntitymoref);

        hostProfileHostBasedConfigSpec.setAnnotation("SDK Sample Host Profile");
        hostProfileHostBasedConfigSpec.setEnabled(true);
        hostProfileHostBasedConfigSpec.setName("SDK Profile " + createHostEntityName + " " + new java.util.Date().getTime());
        hostProfileHostBasedConfigSpec.setUseHostProfileEngine(true);

        System.out.println("--------------------");
        System.out.println("* Creating Host Profile");
        System.out.println("--------------------");
        System.out.println("Host : " + hostEntitymoref.getValue());
        ManagedObjectReference hostProfile =
                vimPort.createProfile(hostprofileManager,
                        hostProfileHostBasedConfigSpec);
        // Changed from get_value to getValue
        System.out.println("Profile : " + hostProfile.getValue());
        return hostProfile;
    }

    /**
     * Associate a profile with a managed entity. The created hostProfile is
     * attached to a hostEntityMoref (ATTACH_HOST_ENTITY_NAME). We attach only
     * one host to the host profile
     *
     * @param hostProfile
     * @param attachEntitymorefs
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void attachProfileWithManagedEntity(
            ManagedObjectReference hostProfile,
            List<ManagedObjectReference> attachEntitymorefs)
            throws RuntimeFaultFaultMsg {
        System.out.println("------------------------");
        System.out.println("* Associating Host Profile");
        System.out.println("------------------------");
        vimPort.associateProfile(hostProfile, attachEntitymorefs);
        System.out.println("Associated " + hostProfile.getValue() + " with "
                + attachEntitymorefs.get(0).getValue());
    }

    /**
     * Get the profile(s) to which this entity is associated. The list of
     * profiles will only include profiles known to this profileManager.
     *
     * @param attachMoref
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void printProfilesAssociatedWithEntity(
            ManagedObjectReference attachMoref) throws RuntimeFaultFaultMsg {
        System.out.println("------------------------------------");
        System.out.println("* Finding Associated Profiles with Host");
        System.out.println("------------------------------------");
        System.out.println("Profiles");
        for (ManagedObjectReference profile : vimPort.findAssociatedProfile(
                hostprofileManager, attachMoref)) {
            System.out.println("    " + profile.getValue());
        }
    }

    /**
     * Update the reference host in use by the HostProfile.
     *
     * @param hostProfile
     * @param attachHostMoref
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void updateReferenceHost(ManagedObjectReference hostProfile,
                             ManagedObjectReference attachHostMoref) throws RuntimeFaultFaultMsg {
        System.out.println("--------------------------------------");
        System.out.println("* Updating Reference Host for the Profile");
        System.out.println("--------------------------------------");
        vimPort.updateReferenceHost(hostProfile, attachHostMoref);
        System.out.println("Updated Host Profile : " + hostProfile.getValue()
                + " Reference to " + attachHostMoref.getValue());
    }

    /**
     * Execute the Profile Engine to calculate the list of configuration changes
     * needed for the host.
     *
     * @param hostProfile
     * @param attachHostMoref
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    HostConfigSpec executeHostProfile(
            ManagedObjectReference hostProfile,
            ManagedObjectReference attachHostMoref) throws RuntimeFaultFaultMsg {

        System.out.println("------------------------------");
        System.out.println("* Executing Profile Against Host");
        System.out.println("------------------------------");
        ProfileExecuteResult profileExecuteResult =
                vimPort.executeHostProfile(hostProfile, attachHostMoref, null);
        System.out.println("Status : " + profileExecuteResult.getStatus());
        if (profileExecuteResult.getStatus().equals("success")) {
            System.out.println("Valid HostConfigSpec representing "
                    + "Configuration changes to be made on host");
            return profileExecuteResult.getConfigSpec();
        }
        if (profileExecuteResult.getStatus().equals("error")) {
            System.out.println("List of Errors");
            for (ProfileExecuteError profileExecuteError : profileExecuteResult
                    .getError()) {
                System.out.println("    "
                        + profileExecuteError.getMessage().getMessage());
            }
            return null;
        }
        return null;
    }

    /**
     * Generate a list of configuration tasks that will be performed on the host
     * during HostProfile application.
     *
     * @param hostConfigSpec
     * @param attachHostMoref
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void configurationTasksToBeAppliedOnHost(HostConfigSpec hostConfigSpec,
            ManagedObjectReference attachHostMoref) throws RuntimeFaultFaultMsg,
            InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {

        System.out.println("-------------------------------------------------------");
        System.out.println("* Config Tasks on the Host during HostProfile Application");
        System.out.println("-------------------------------------------------------");

        ManagedObjectReference task = vimPort.generateHostProfileTaskListTask(hostprofileManager,
                hostConfigSpec, attachHostMoref);
        if (getTaskResultAfterDone(task)) {
            System.out.println("Done....");

            Map<String, Object> taskprops = getMOREFs.entityProps(task,
                    new String[] { "info.result" });

            HostProfileManagerConfigTaskList hostProfileManagerConfigTaskList = (HostProfileManagerConfigTaskList) taskprops
                    .get("info.result");
            List<LocalizableMessage> taskMessages = hostProfileManagerConfigTaskList
                    .getTaskDescription();
            if (taskMessages != null && !(taskMessages.isEmpty())) {
                for (LocalizableMessage taskMessage : taskMessages) {
                    System.out.println("Message : " + taskMessage.getMessage());
                }
            } else
                System.out.println("There are no configuration changes to be made");

        } else {
            System.out.println("Operation Failed");
        }
    }

    /**
     * Checking for the compliance status and results. If compliance is
     * "nonCompliant", it lists all the compliance failures.
     *
     * @param result
     * @return
     */
    boolean complianceStatusAndResults(Object result) {
        List<ComplianceResult> complianceResults =
                ((ArrayOfComplianceResult) result).getComplianceResult();
        for (ComplianceResult complianceResult : complianceResults) {
            System.out
                    .println("Host : " + complianceResult.getEntity().getValue());
            System.out.println("Profile : "
                    + complianceResult.getProfile().getValue());
            System.out.println("Compliance Status : "
                    + complianceResult.getComplianceStatus());
            if (complianceResult.getComplianceStatus().equals("nonCompliant")) {
                System.out.println("Compliance Failure Reason");
                for (ComplianceFailure complianceFailure : complianceResult
                        .getFailure()) {
                    System.out.println(" "
                            + complianceFailure.getMessage().getMessage());
                }
                return false;
            } else {
                return true;
            }
        }
        return false;

    }

    /**
     * Check compliance of an entity against a Profile.
     *
     * @param profiles
     * @param entities
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    boolean checkProfileCompliance(
            ManagedObjectReference profiles,
            ManagedObjectReference entities) throws RemoteException, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        System.out.println("---------------------------------------------");
        System.out.println("* Checking Complaince of Entity against Profile");
        System.out.println("---------------------------------------------");
        List<String> opts = new ArrayList<String>();
        opts.add("info.state");
        opts.add("info.error");
        List<String> opt = new ArrayList<String>();
        opt.add("state");
        List<ManagedObjectReference> profileList = new ArrayList<ManagedObjectReference>();
        List<ManagedObjectReference> entityList = new ArrayList<ManagedObjectReference>();
        profileList.add(profiles);
        entityList.add(entities);
        ManagedObjectReference cpctask =
                vimPort.checkComplianceTask(profilecomplianceManager, profileList,
                        entityList);
        if (getTaskResultAfterDone(cpctask)) {
            System.out.printf("Entity is Compliance against Profile.");
        } else {
            throw new RuntimeException(
                    "Could not check the compliance of the profile with the given entity");
        }
        Object result =
                getMOREFs.entityProps(cpctask, new String[]{"info.result"}).get(
                        "info.result");
        return complianceStatusAndResults(result);
    }

    /**
     * Setting the host to maintenance mode and apply the configuration to the
     * host.
     *
     * @param attachHostMoref
     * @param hostConfigSpec
     * @throws HostConfigFailed
     * @throws InvalidState
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void applyConfigurationToHost(
            ManagedObjectReference attachHostMoref, HostConfigSpec hostConfigSpec)
            throws RemoteException, RuntimeFaultFaultMsg, InvalidStateFaultMsg, TimedoutFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg, HostConfigFailedFaultMsg {
        System.out
                .println("----------------------------------------------------");
        System.out
                .println("* Applying Configuration changes or HostProfile to Host");
        System.out
                .println("----------------------------------------------------");
        System.out.println("Putting Host in Maintenance Mode");
        List<String> opts = new ArrayList<String>();
        opts.add("info.state");
        opts.add("info.error");
        List<String> opt = new ArrayList<String>();
        opt.add("state");

        suspendPoweredOnGuestVMs(attachHostMoref);

        ManagedObjectReference mainmodetask =
                vimPort.enterMaintenanceModeTask(attachHostMoref, 0, null, null);
        Object[] result =
                waitForValues.wait(mainmodetask, new String[]{"info.state",
                        "info.error"}, new String[]{"state"},
                        new Object[][]{new Object[]{TaskInfoState.SUCCESS,
                                TaskInfoState.ERROR}});
        if (result[0].equals(TaskInfoState.SUCCESS)) {
            System.out.printf("Success: Entered Maintenance Mode ");
        } else {
            String msg = "Failure: Entering Maintenance Mode "
                    + "Check and/or Configure Host Maintenance Mode Settings. "
                    + "Check that all Virtual Machines on this host are either suspended or powered off.";
            powerOnSuspendedGuestVMs(attachHostMoref);
            throw new RuntimeException(msg);
        }
        System.out.println("Applying Profile to Host");
        ManagedObjectReference apphostconftask =
                vimPort.applyHostConfigTask(hostprofileManager, attachHostMoref,
                        hostConfigSpec, null);
        Object[] resultone =
                waitForValues.wait(apphostconftask, new String[]{"info.state",
                        "info.error"}, new String[]{"state"},
                        new Object[][]{new Object[]{TaskInfoState.SUCCESS,
                                TaskInfoState.ERROR}});
        if (resultone[0].equals(TaskInfoState.SUCCESS)) {
            System.out.printf("Success: Apply Configuration to Host ");
        } else {
            exitMaintenanceMode(attachHostMoref);
            String msg = "Failure: Apply configuration to Host";
            throw new RuntimeException(msg);
        }

        exitMaintenanceMode(attachHostMoref);
    }

    public void exitMaintenanceMode(ManagedObjectReference attachHostMoref)
            throws InvalidStateFaultMsg, RuntimeFaultFaultMsg, TimedoutFaultMsg,
            InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference mainmodeexittask = vimPort.exitMaintenanceModeTask(attachHostMoref,
                0);
        Object[] results = waitForValues.wait(mainmodeexittask, new String[] { "info.state",
                "info.error" }, new String[] { "state" }, new Object[][] { new Object[] {
                TaskInfoState.SUCCESS, TaskInfoState.ERROR } });
        if (results[0].equals(TaskInfoState.SUCCESS)) {
            powerOnSuspendedGuestVMs(attachHostMoref);
        } else {
            throw new RuntimeException("Failure exiting maintenance mode.");
        }
    }

    public void powerOnSuspendedGuestVMs(ManagedObjectReference attachHostMoref)
            throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        for (ManagedObjectReference vmMor : suspendedVMList) {
            System.out.println("\nPowering ON virtual machine : " + "[" + vmMor.getValue() + "]");
            try {
                ManagedObjectReference taskmor = vimPort.powerOnVMTask(vmMor, null);
                if (getTaskResultAfterDone(taskmor)) {
                    System.out.println("[" + vmMor.getValue() + "] powered on successfully");
                }
            } catch (Exception e) {
                System.out.println("Unable to power on vm : " + "[" + vmMor.getValue() + "]");
                System.err.println("Reason :" + e.getLocalizedMessage());
            }
        }
    }

    public void suspendPoweredOnGuestVMs(ManagedObjectReference attachHostMoref)
            throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        Map<ManagedObjectReference, Map<String, Object>> vms = getMOREFs.inContainerByType(
                attachHostMoref, "VirtualMachine", new String[] { "name", "runtime.powerState" },
                new RetrieveOptions());
        suspendedVMList = new ArrayList<ManagedObjectReference>();
        for (ManagedObjectReference vm : vms.keySet()) {
            Map<String, Object> vmProp = vms.get(vm);
            VirtualMachinePowerState vmPowerState = (VirtualMachinePowerState) vmProp
                    .get("runtime.powerState");
            String vmName = (String) vmProp.get("name");
            if ((vmPowerState.equals(VirtualMachinePowerState.POWERED_ON))) {
                try {
                    ManagedObjectReference taskmor = vimPort.suspendVMTask(vm);
                    if (getTaskResultAfterDone(taskmor)) {
                        System.out.println(vmName + "[" + vm.getValue() + "] suspended successfully");
                        suspendedVMList.add(vm);
                    }

                } catch (Exception e) {
                    System.out.println(vmName + "Unable to suspend vm : " + vm + "[" + vm.getValue() + "]");
                    System.err.println("Reason :" + e.getLocalizedMessage());
                }
            }
        }
    }

    /*
    * Destroy the Profile.
    *
    * @param hostProfile
    * @throws RuntimeFault
    * @throws RemoteException
    */
    void deleteHostProfile(ManagedObjectReference hostProfile)
            throws RuntimeFaultFaultMsg {
        System.out.println("Deleting Profile");
        System.out.println("---------------");
        vimPort.destroyProfile(hostProfile);
        System.out.println("Profile : " + hostProfile.getValue());
    }

    /**
     * Detach a profile from a managed entity.
     *
     * @param hostProfile
     * @param managedObjectReferences
     * @throws RuntimeFault
     * @throws RemoteException
     */
    void detachHostFromProfile(
            ManagedObjectReference hostProfile,
            ManagedObjectReference entity)
            throws RuntimeFaultFaultMsg {
        System.out.println("------------------------");
        System.out.println("* Detach Host From Profile");
        System.out.println("------------------------");
        List<ManagedObjectReference> entityList = new ArrayList<ManagedObjectReference>();
        entityList.add(entity);
        vimPort.dissociateProfile(hostProfile, entityList);
        System.out.println("Detached Host : "
                + entityList.get(0).getValue() + " From Profile : "
                + hostProfile.getValue());
    }

    @Action
    public void run() throws DuplicateNameFaultMsg, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg, RemoteException, HostConfigFailedFaultMsg, InvalidStateFaultMsg, TimedoutFaultMsg {
        if (createHostEntityName == null || attachEntityName == null
                || attachEntityType == null) {
            throw new IllegalArgumentException(
                    "Expected --sourcehostname, --entityname, --entitytype arguments properly");
        }


        hostprofileManager = serviceContent.getHostProfileManager();
        profilecomplianceManager = serviceContent.getComplianceManager();

        RetrieveOptions retrieveOptions = new RetrieveOptions();

        Map<String, ManagedObjectReference> results = getMOREFs.inFolderByType(serviceContent.getRootFolder(),
                "HostSystem", retrieveOptions);

        ManagedObjectReference createHostMoref = results.get(createHostEntityName);

        if (createHostMoref == null) {
            throw new IllegalStateException("HostSystem " + createHostEntityName);
        }

        ManagedObjectReference attachMoref =
                getMOREFs.inFolderByType(serviceContent.getRootFolder(),
                        attachEntityType, retrieveOptions).get(attachEntityName);
        List<ManagedObjectReference> hmor =
                new ArrayList<ManagedObjectReference>();
        hmor.add(attachMoref);
        ManagedObjectReference hostProfile =
                createHostProfile(createHostMoref);
        attachProfileWithManagedEntity(hostProfile, hmor);
        printProfilesAssociatedWithEntity(attachMoref);
        if (attachEntityType.equals("HostSystem")) {
            updateReferenceHost(hostProfile, attachMoref);
            HostConfigSpec hostConfigSpec =
                    executeHostProfile(hostProfile, attachMoref);
            if (hostConfigSpec != null) {
                configurationTasksToBeAppliedOnHost(hostConfigSpec, attachMoref);
            }
            if (checkProfileCompliance(hostProfile, attachMoref)) {
                applyConfigurationToHost(attachMoref, hostConfigSpec);
            }
        } else {
            checkProfileCompliance(hostProfile, attachMoref);
        }
        detachHostFromProfile(hostProfile, attachMoref);
        deleteHostProfile(hostProfile);
    }
}
