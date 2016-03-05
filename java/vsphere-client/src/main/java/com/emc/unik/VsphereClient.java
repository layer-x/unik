package com.emc.unik;

import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.mo.*;

import java.net.URL;
import java.util.HashMap;

public class VsphereClient {

    private static final String TARGET_FOLDER_NAME = "unik";

    public static void main(final String[] args) throws Exception {
        Runnable getVm = new Runnable() {
            public void run() {
                try {
                    getVms(args);
                } catch(Exception e) {
                    System.err.printf("failed to get VM: "+e.toString()+"\n");
                }
            }
        };
        Runnable getFolder = new Runnable() {
            public void run() {
                try {
                    getFolder(args);
                } catch(Exception e) {
                    System.err.printf("failed to get Folder: "+e.toString()+"\n");
                }
            }
        };
        Runnable createVmFromVmdk = new Runnable() {
            public void run() {
                try {
                    createVmFromVmdk(args);
                } catch(Exception e) {
                    System.err.printf("failed to get Folder: "+e.toString()+"\n");
                }
            }
        };

        HashMap<String, Runnable> methods = new HashMap<String, Runnable>();
        methods.put("get-folder", getFolder);
        methods.put("create-vm-from-vmdk", createVmFromVmdk);

        methods.put("get-vms", getVm);
        methods.put("delete-vm", getVm);

        methods.put("delete-vm-image", getVm);
        methods.put("create-vm-image", getVm);
        methods.put("run-vm-image", getVm);
        methods.put("get-vm-image", getVm);
        methods.put("get-vm-images", getVm);
        methods.put("delete-vm-image-by-name", getVm);

        methods.put("get-vm-logs", getVm);

        if (args.length < 1) {
            System.err.println(
                    "java VsphereClient <method> <args>");
            System.err.println(
                    "java VsphereClient "+methods.keySet());
            return;
        }

        String command = args[0];

        Runnable method = methods.get(command);
        if (method == null) {
            System.out.println("Available commands: "+methods.keySet());
            return;
        }
        method.run();

    }

    public static void getVms(String[] args) throws Exception {
        if (args.length < 6) {
            System.err.println(
                    "java VsphereClient get-vm <targetURL> <username> <password> <hostip>");
            System.err.println(
                    "java VsphereClient https://10.20.140.47/sdk Administrator password 10.17.204.115");
            return;
        }
        String url = args[0];
        String username = args[1];
        String password = args[2];
        String hostip = args[3];

        ServiceInstance si = new ServiceInstance(new URL(args[0]), args[1], args[2], true);
    }

    public static void createVmFromVmdk(String[] args) throws Exception {
        if (args.length < 6) {
            System.err.println(
                    "java VsphereClient create-vm-from-vmdk <targetURL> <username> <password> <vmdk-path>");
            System.err.println(
                    "java VsphereClient get-folder https://10.20.140.47/sdk Administrator password unik/disk.vmdk");
            return;
        }
        String url = args[1];
        String username = args[2];
        String password = args[3];
        String hostip = args[4];
        String vmdkPath = args[5];

        ServiceInstance si = new ServiceInstance(new URL(url), username, password, true);
        Folder rootFolder = si.getRootFolder();

        Folder targetFolder = (Folder) new InventoryNavigator(rootFolder).searchManagedEntity("Folder", TARGET_FOLDER_NAME);
        System.out.println(targetFolder);


//        try {
//            System.out.printf("reached\n");
//            targetFolder = rootFolder.createFolder(TARGET_FOLDER_NAME);
//            System.out.printf("reached\n");
//        } catch (DuplicateName e) {
//            for (ManagedEntity me : rootFolder.getChildEntity()) {
//                if (me.getName().equals(TARGET_FOLDER_NAME)) {
//                    targetFolder = (Folder) me;
//                }
//            }
//            if (targetFolder == null) {
//                throw e;
//            }
//        }

//        targetFolder = (Folder) host.getVms()[0].getParent();
//        System.out.println(targetFolder.getMOR().getVal());
//        System.out.println(targetFolder.getName());
//        System.out.println(targetFolder.toString());
//        System.out.println(targetFolder.getParent());
    }

    public static void getFolder(String[] args) throws Exception {
        if (args.length < 6) {
            System.err.println(
                    "java VsphereClient get-folder <targetURL> <username> <password> <hostip> <FolderName>");
            System.err.println(
                    "java VsphereClient get-folder https://10.20.140.47/sdk Administrator password 10.10.10.10 FolderName");
            return;
        }
        String url = args[1];
        String username = args[2];
        String password = args[3];
        String hostip = args[4];
        String folderName = args[5];

        ServiceInstance si = new ServiceInstance(new URL(url), username, password, true);
        Folder rootFolder = si.getRootFolder();
        getSubfolders(rootFolder);


        HostSystem host = (HostSystem) si.getSearchIndex().findByIp(null, hostip, false);

//        Folder targetFolder = (Folder) new InventoryNavigator(rootFolder).searchManagedEntity("Folder", folderName);

        Folder targetFolder = null;
        try {
            System.out.printf("reached\n");
            targetFolder = rootFolder.createFolder(TARGET_FOLDER_NAME);
            System.out.printf("reached\n");
        } catch (DuplicateName e) {
            for (ManagedEntity me : rootFolder.getChildEntity()) {
                if (me.getName().equals(TARGET_FOLDER_NAME)) {
                    targetFolder = (Folder) me;
                }
            }
            if (targetFolder == null) {
                throw e;
            }
        }

//        targetFolder = (Folder) host.getVms()[0].getParent();
//        System.out.println(targetFolder.getMOR().getVal());
//        System.out.println(targetFolder.getName());
//        System.out.println(targetFolder.toString());
//        System.out.println(targetFolder.getParent());
    }


    private static void getSubfolders(Folder rootFolder) throws Exception {
        for (ManagedEntity entity : rootFolder.getChildEntity()) {
            System.out.println("Found entity: "+entity);
            if (entity.getClass() == Folder.class) {
                Folder subFolder = (Folder) entity;

                System.out.println(subFolder.getMOR().getVal());
                System.out.println(subFolder.getName());
                System.out.println(subFolder.toString());
                System.out.println(subFolder.getParent());
                getSubfolders(subFolder);
            }
            if (entity.getClass() == Datacenter.class) {
                Datacenter datacenter = (Datacenter) entity;

                System.out.println(datacenter.getMOR().getVal());
                System.out.println(datacenter.getName());
                System.out.println(datacenter.toString());
                System.out.println(datacenter.getParent());
                getSubfolders(datacenter.getDatastoreFolder());
            }
        }
    }

    private static void getDatacenter(Folder rootFolder) throws Exception {
        for (ManagedEntity entity : rootFolder.getChildEntity()) {
            System.out.println("Found entity: "+entity);
            if (entity.getClass() == Folder.class) {
                Folder subFolder = (Folder) entity;

                System.out.println(subFolder.getMOR().getVal());
                System.out.println(subFolder.getName());
                System.out.println(subFolder.toString());
                System.out.println(subFolder.getParent());
                getSubfolders(subFolder);
            }
            if (entity.getClass() == Datacenter.class) {
                Datacenter datacenter = (Datacenter) entity;

                System.out.println(datacenter.getMOR().getVal());
                System.out.println(datacenter.getName());
                System.out.println(datacenter.toString());
                System.out.println(datacenter.getParent());
                getSubfolders(datacenter.getDatastoreFolder());
            }
        }
    }
}
