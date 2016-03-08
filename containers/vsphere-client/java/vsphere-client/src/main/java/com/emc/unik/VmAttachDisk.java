package com.emc.unik;


import java.net.URL;

import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class VmAttachDisk {
    public static void main(String[] args) throws Exception {
        if (args.length != 6) {
            System.out.println("Usage: java VmAttachDisk <url> " +
                    "<username> <password> <vmname> <vmdkPath> <controllerKey>");
            System.exit(0);
        }
        String vmname = args[3];
        String vmdkPath = args[4];
        int controllerKey = Integer.parseInt(args[5]);

        ServiceInstance si = new ServiceInstance(
                new URL(args[0]), args[1], args[2], true);

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
