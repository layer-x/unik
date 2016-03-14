package com.emc.unik;


import java.net.URL;

import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.mo.*;

public class VmAttachDisk {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java VmAttachDisk|CopyFile [<opts>]");
            System.exit(-1);
        }

        if (args[0].equals("VmAttachDisk")) {
            if (args.length != 7) {
                System.err.println("Usage: java VmAttachDisk <url> " +
                        "<username> <password> <vmname> <vmdkPath> <controllerKey>");
                System.exit(-1);
            }

            String vmname = args[4];
            String vmdkPath = args[5];
            int controllerKey = Integer.parseInt(args[6]);

            ServiceInstance si = new ServiceInstance(
                    new URL(args[1]), args[2], args[3], true);

            Folder rootFolder = si.getRootFolder();
            VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
                    rootFolder).searchManagedEntity("VirtualMachine", vmname);

            if (vm == null) {
                System.out.println("No VM " + vmname + " found");
                si.getServerConnection().logout();
                return;
            }

            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

            // mode: persistent|independent_persistent,independent_nonpersistent
            String diskMode = "persistent";
            VirtualDeviceConfigSpec vdiskSpec = createExistingDiskSpec(vmdkPath, controllerKey, diskMode);
            VirtualDeviceConfigSpec [] vdiskSpecArray = {vdiskSpec};
            vmConfigSpec.setDeviceChange(vdiskSpecArray);

            Task task = vm.reconfigVM_Task(vmConfigSpec);
            System.out.println(task.waitForTask());
            if (task.getTaskInfo() != null && task.getTaskInfo().getDescription() != null) {
                System.out.println(task.getTaskInfo().getDescription().getMessage());
            }
        }
//       TODO: remove, not in use
//        if (args[0].equals("CopyFile")) {
//            if (args.length != 6) {
//                System.err.println("Usage: java CopyFile <url> " +
//                        "<username> <password> <sourcePath> <destinationPath>");
//                System.exit(-1);
//            }
//
//            ServiceInstance si = new ServiceInstance(
//                    new URL(args[1]), args[2], args[3], true);
//
//            Datacenter datacenter = (Datacenter) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("Datacenter", "ha-datacenter");
//
//            String sourcePath = args[4];
//            String destinationPath = args[5];
//
//            FileManager fileManager = si.getFileManager();
//            if (fileManager == null) {
//                System.err.println("filemanager not available");
//                System.exit(-1);
//            }
//            Task copyTask = fileManager.copyDatastoreFile_Task(sourcePath, datacenter, destinationPath, datacenter, true);
//
//            System.out.println(copyTask.waitForTask());
//            if (copyTask.getTaskInfo() != null && copyTask.getTaskInfo().getDescription() != null) {
//                System.out.println(copyTask.getTaskInfo().getDescription().getMessage());
//            }
//        }
    }

    static VirtualDeviceConfigSpec createExistingDiskSpec(String fileName, int cKey, String diskMode) {
        VirtualDeviceConfigSpec diskSpec =
                new VirtualDeviceConfigSpec();
        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
        // do not set diskSpec.fileOperation!
        VirtualDisk vd = new VirtualDisk();
        vd.setCapacityInKB(-1);
        vd.setKey(0);
        vd.setUnitNumber(new Integer(0));
        vd.setControllerKey(new Integer(cKey));
        VirtualDiskFlatVer2BackingInfo diskfileBacking =
                new VirtualDiskFlatVer2BackingInfo();
        diskfileBacking.setFileName(fileName);
        diskfileBacking.setDiskMode(diskMode);
        vd.setBacking(diskfileBacking);
        diskSpec.setDevice(vd);
        return diskSpec;
    }
}
