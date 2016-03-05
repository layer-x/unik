package com.vmware.vm;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import java.util.*;

@Sample(name = "xvmotion", description = "This sample is used to migrate/relocate VM to another host and datastore"
   + "using the drs placement recommendations")
   public class XVMotion extends ConnectedVimServiceBase {

   String vmName = null;
   String destCluster = null;

   @Option(name = "vmname", description = "Name of the virtual machine to be migrated")
   public void setVmName(String name) {
      this.vmName = name;
   }

   @Option(name = "clustername", description = "Target cluster name")
   public void setCluster(String clusterName) {
      this.destCluster = clusterName;
   }

   /**
    * This method returns a boolean value specifying whether the Task is
    * succeeded or failed.
    *
    * @param task ManagedObjectReference representing the Task.
    * @return boolean value representing the Task result.
    *
    * @throws InvalidCollectorVersionFaultMsg
    * @throws RuntimeFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    */
   boolean getTaskResultAfterDone(ManagedObjectReference task)
   throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg,
   InvalidCollectorVersionFaultMsg {

      boolean retVal = false;

      // info has a property - state for state of the task
      Object[] result =
         waitForValues.wait(task,
               new String[] { "info.state", "info.error" },
               new String[] { "state" }, new Object[][] { new Object[] {
                     TaskInfoState.SUCCESS, TaskInfoState.ERROR } });

      if (result[0].equals(TaskInfoState.SUCCESS)) {
         retVal = true;
      }
      if (result[1] instanceof LocalizedMethodFault) {
         throw new RuntimeException(((LocalizedMethodFault) result[1])
               .getLocalizedMessage());
      }
      return retVal;
   }

   /**
    * This method is used to relocate the VM to the computing resource
    * recommended by the DRS.
    *
    * @throws RuntimeFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    * @throws FileFaultFaultMsg
    * @throws InsufficientResourcesFaultFaultMsg
    * @throws InvalidDatastoreFaultMsg
    * @throws InvalidStateFaultMsg
    * @throws MigrationFaultFaultMsg
    * @throws TimedoutFaultMsg
    * @throws VmConfigFaultFaultMsg
    * @throws InvalidCollectorVersionFaultMsg
    */
   @Action
   public void placeVM() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, FileFaultFaultMsg,
   InsufficientResourcesFaultFaultMsg, InvalidDatastoreFaultMsg,
   InvalidStateFaultMsg, MigrationFaultFaultMsg, TimedoutFaultMsg,
   VmConfigFaultFaultMsg, InvalidCollectorVersionFaultMsg {
      PlacementResult placementResult;
      PlacementSpec placementSpec = new PlacementSpec();
      ManagedObjectReference vmMOR = null;
      ManagedObjectReference clusterMor = null;
      Map<String, ManagedObjectReference> clusters =
         getMOREFs.inContainerByType(serviceContent.getRootFolder(), "ClusterComputeResource");
      if (clusters.containsKey(destCluster)) {
         clusterMor = clusters.get(destCluster);
      } else {
         throw new IllegalStateException("No Cluster by the name of '" + destCluster
               + "' found!");
      }
         Map<String, ManagedObjectReference> vms =
            getMOREFs.inContainerByType(serviceContent.getRootFolder(), "VirtualMachine");
         if (vms.containsKey(vmName)) {
            vmMOR = vms.get(vmName);
         } else {
            throw new IllegalStateException("No VM by the name of '" + vmName
                  + "' found!");
         }
         placementSpec.setVm(vmMOR);
         placementResult = vimPort.placeVm(clusterMor, placementSpec);
         // Check Placement Results
         PlacementAction placementAction = getPlacementAction(placementResult);
         if (placementAction != null) {
            VirtualMachineRelocateSpec relocateSpec =
               placementAction.getRelocateSpec();
            ManagedObjectReference taskMOR =
               vimPort.relocateVMTask(vmMOR, relocateSpec, VirtualMachineMovePriority.DEFAULT_PRIORITY);
            if (getTaskResultAfterDone(taskMOR)) {
               System.out.println("Relocation done successfully");
            } else {
               System.out.println("Error::  Relocation failed");
            }
         } else {
            System.out.println("Recommendations are not correct");
         }
   }

   /**
    * This method is to return  the first valid PlacementAction out of the DRS recommendations.
    *
    * @param placementResult
    * @return PlacementAction
    * @throws RuntimeFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    */
   PlacementAction getPlacementAction(PlacementResult placementResult) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
      List<ClusterRecommendation> recommendations = placementResult.getRecommendations();
      PlacementAction placementAction = null;
      int size = recommendations.size();
      boolean actionOk = false;
      if (size > 0) {
         System.out.println("Total number of recommendations are " + size);
         System.out.println("Processing the xvmotion placement recommendations out of the recommendations received");
         for (ClusterRecommendation clusterrecommend : recommendations) {
            if (clusterrecommend.getReason().equalsIgnoreCase("xvmotionPlacement")) {
               List<ClusterAction> actions = clusterrecommend.getAction();
               for (ClusterAction action : actions) {
                  if (action instanceof PlacementAction) {
                     placementAction = (PlacementAction) action;
                     break;
                  }
               }
               if (placementAction != null) {
                  if (placementAction.getVm() == null || placementAction.getTargetHost() == null ) {
                     System.out.println("Placement Action doesnot have a vm and targethost set");
                  } else {
                     if( placementAction.getRelocateSpec() != null) {
                        actionOk = checkRelocateSpec(placementAction.getRelocateSpec());
                        if (actionOk)
                           break;
                        else
                           placementAction = null;
                     }
                  }
               } else {
                  System.out.println("Recommendation doesnot have a placement action");
               }
            }
         }
      } else {
         System.out.println("No recommendations by DRS");
      }
      return placementAction;
   }

   /**
    * This method is to validate the RelocateSpec.
    *
    * @param relocateSpec
    * @return boolean
    * @throws RuntimeFaultFaultMsg
    * @throws InvalidPropertyFaultMsg
    */
   boolean checkRelocateSpec(VirtualMachineRelocateSpec relocateSpec)
   throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
      boolean check = false;
      if (relocateSpec.getHost() != null) {
         if (relocateSpec.getPool() != null) {
            if (relocateSpec.getDatastore() != null) {
               check = true;
            } else {
               System.out.println("RelocateSpec doesnot have a datastore");
            }
         } else {
            System.out.println("RelocateSpec doesnot have a resource pool");
         }
      } else {
         System.out.println("RelocateSpec doesnot have a host");
      }
      return check;
   }
}
