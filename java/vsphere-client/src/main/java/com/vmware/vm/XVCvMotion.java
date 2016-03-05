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

import com.vmware.vim25.*;
import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.BasicConnection;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.connection.Connection;
import com.vmware.connection.helpers.GetMOREF;
import java.util.*;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * <pre>
 * XVCvMotion
 *
 * Used to Relocate VM from one VC to another.
 *
 * <b>Parameters:</b>
 * url            [required] : url of the web service
 * username       [required] : username for the authentication
 * password       [required] : password for the authentication
 * vmname         [required] : name of the virtual machine
 * destcluster    [required] : name of the cluster on target virtual center where to migrate the virtual machine.
 * remoteurl      [required] : url of web service for target virtual center
 * ruser          [required] : username for the authentication to the target virtual center.
 * rpassword      [required] : password for the authentication to the target virtual center.
 * rthumbprint    [required] : thumbprint of the target virtual center.
 * targetfolder   [optional] : folder on the target virtual center where to migrate the virtual machine.
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.vm.XVCvMotion --url [URLString] --username [User] --password [Password]
 * --vmname [VMName] --destcluster [Target Cluster] --remoteurl [TargetVC URLString] --ruser [TargetVC User]
 * --rpassword [TargetVC Password] --rthumbprint [TargetVC Thumbprint] --targetfolder [Target Folder]
 * </pre>
 */

@Sample(name = "XVCvMotion", description = "Used to Relocate VM from one VC to another")
public class XVCvMotion extends ConnectedVimServiceBase {

	/*
	 * Connection input parameters for the Source and target vCenter Servers.
	 */
	String vmName = null;
	String destCluster = null;
	String remoteurl = null;
	String ruser = null;
	String rpassword = null;
	String rthumbprint = null;
	String targetFolder = null; // default value for targetFolder is vm
	private static VimPortType destVimPort = null; // VimPort for the target VC
	// Connection
	private static ServiceContent destServiceContent = null;

	@Option(name = "vmname", description = "name of the virtual machine to be migrated")
	public void setVmName(String name) {
		this.vmName = name;
	}

	@Option(name = "clustername", description = "Target Cluster Name")
	public void setCluster(String clusterName) {
		this.destCluster = clusterName;
	}

	@Option(name = "remoteurl", description = "Target vCenter URL ")
	public void setRemoteUrl(String remoteurl) {
		this.remoteurl = remoteurl;
	}

	@Option(name = "ruser", description = "Target vCenter username")
	public void setRuser(String ruser) {
		this.ruser = ruser;
	}

	@Option(name = "rpassword", description = "Target vCenter Password")
	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	@Option(name = "rthumbprint", description = "Thumbprint of Target vCenter")
	public void setThumbprint(String thumbprint) {
		this.rthumbprint = thumbprint;
	}

	@Option(name = "targetfolder", description = "Target Folder", required = false)
	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	/**
	 * This method returns Managed Object Reference to VM root Folder
	 *
	 * @param MOR
	 *            for the starting Point of Filter
	 * @param Type
	 *            OF Entity we are looking For.
	 * @return Managed Object Reference to VM root Folder
	 * @throws RuntimeFaultFaultMsg
	 * @throws InvalidPropertyFaultMsg
	 */
	ManagedObjectReference getVMFolderMor(ManagedObjectReference mor,
			String type, String name) throws RuntimeFaultFaultMsg,
			InvalidPropertyFaultMsg {

		List<SelectionSpec> tSpec = buildVmFolderTraversal();
		ManagedObjectReference retVal = null;

		// Create PropertySpec
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.getPathSet().add("name");
		propertySpec.setType(type);

		// Now create ObjectSpec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(mor);
		objectSpec.setSkip(Boolean.TRUE);
		objectSpec.getSelectSet().addAll(tSpec);

		// Create PropertyFilterSpec using the PropertySpec and ObjectSpec
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);

		List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(1);
		listpfs.add(propertyFilterSpec);

		List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
		if (listobjcont != null) {
			for (ObjectContent oc : listobjcont) {
				ManagedObjectReference mr = oc.getObj();
				String entityName = null;
				List<DynamicProperty> listDynamicProps = oc.getPropSet();
				DynamicProperty[] dps = listDynamicProps
						.toArray(new DynamicProperty[listDynamicProps.size()]);
				if (dps != null) {
					for (DynamicProperty dp : dps) {
						entityName = (String) dp.getVal();
					}
				}
				if (entityName != null && entityName.equals(name)) {
					retVal = mr;
					break;
				}
			}
		} else {
			System.out.println("The Object Content is Null");
		}
		return retVal;
	}

	// RetrievePropertiesAllObjects

	List<ObjectContent> retrievePropertiesAllObjects(
			List<PropertyFilterSpec> listpfs) throws RuntimeFaultFaultMsg,
			InvalidPropertyFaultMsg {
		RetrieveOptions propObjectRetrieveOpts = new RetrieveOptions();
		List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();
		RetrieveResult rslts = destVimPort.retrievePropertiesEx(
				destServiceContent.getPropertyCollector(), listpfs,
				propObjectRetrieveOpts);
		if (rslts != null && rslts.getObjects() != null
				&& !rslts.getObjects().isEmpty()) {
			listobjcontent.addAll(rslts.getObjects());
		}
		String token = null;
		if (rslts != null && rslts.getToken() != null) {
			token = rslts.getToken();
		}

		while (token != null && !token.isEmpty()) {
			rslts = destVimPort.continueRetrievePropertiesEx(destServiceContent
					.getPropertyCollector(), token);
			token = null;
			if (rslts != null) {
				token = rslts.getToken();
				if (rslts.getObjects() != null && !rslts.getObjects().isEmpty()) {
					listobjcontent.addAll(rslts.getObjects());
				}
			}
		}
		return listobjcontent;
	}

	// buildVmFolderTraversal
	// This to Get the MOR for the Folder where to place the VM.
	//It Include traversal from CLuster to datacenter and then to VMFolder.

	List<SelectionSpec> buildVmFolderTraversal() {

		// DC -> VM Folder
		TraversalSpec dcToVmf = new TraversalSpec();
		dcToVmf.setType("Datacenter");
		dcToVmf.setSkip(Boolean.FALSE);
		dcToVmf.setPath("vmFolder");
		dcToVmf.setName("dcToVmf");
		dcToVmf.getSelectSet().add(getSelectionSpec("visitFolders"));

		// CLUSTER -> Parent
		TraversalSpec CrtoPr = new TraversalSpec();
		CrtoPr.setType("ClusterComputeResource");
		CrtoPr.setSkip(Boolean.FALSE);
		CrtoPr.setPath("parent");
		CrtoPr.setName("cctoparent");
		CrtoPr.getSelectSet().add(getSelectionSpec("foldertoparent"));

		// Folder->Parent
		TraversalSpec FoltoPr = new TraversalSpec();
		FoltoPr.setType("Folder");
		FoltoPr.setSkip(Boolean.FALSE);
		FoltoPr.setPath("parent");
		FoltoPr.setName("foldertoparent");
		FoltoPr.getSelectSet().add(getSelectionSpec("foldertoparent"));
		FoltoPr.getSelectSet().add(getSelectionSpec("dcToVmf"));

		// For Folder -> Folder recursion
		TraversalSpec visitFolders = new TraversalSpec();
		visitFolders.setType("Folder");
		visitFolders.setPath("childEntity");
		visitFolders.setSkip(Boolean.FALSE);
		visitFolders.setName("visitFolders");
		visitFolders.getSelectSet().add(getSelectionSpec("visitFolders"));


		List<SelectionSpec> resultspec = new ArrayList<SelectionSpec>();
		resultspec.add(visitFolders);
		resultspec.add(dcToVmf);
		resultspec.add(CrtoPr);
		resultspec.add(FoltoPr);
		return resultspec;
	}

	// GetSelectionSpec
	SelectionSpec getSelectionSpec(String name) {
		SelectionSpec genericSpec = new SelectionSpec();
		genericSpec.setName(name);
		return genericSpec;
	}

	/**
	 * This method returns a boolean value specifying whether the Task is
	 * succeeded or failed.
	 *
	 * @param task
	 *            ManagedObjectReference representing the Task.
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
		Object[] result = waitForValues.wait(task, new String[] { "info.state",
				"info.error" }, new String[] { "state" },
				new Object[][] { new Object[] { TaskInfoState.SUCCESS,
						TaskInfoState.ERROR } });

		if (result[0].equals(TaskInfoState.SUCCESS)) {
			retVal = true;
		}
		if (result[1] instanceof LocalizedMethodFault) {
			throw new RuntimeException(((LocalizedMethodFault) result[1])
					.getLocalizedMessage());
		}
		return retVal;
	}

	@Action
	public void placeVM() throws DuplicateNameFaultMsg, InvalidNameFaultMsg,
			RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, FileFaultFaultMsg,
			InsufficientResourcesFaultFaultMsg, InvalidDatastoreFaultMsg,
			InvalidStateFaultMsg, MigrationFaultFaultMsg, TimedoutFaultMsg,
			VmConfigFaultFaultMsg, InvalidCollectorVersionFaultMsg,
			InvalidLocaleFaultMsg, InvalidLoginFaultMsg {

		PlacementResult placementResult;
		ManagedObjectReference clusterMor = null;
		PlacementSpec placementSpec = new PlacementSpec();
		ManagedObjectReference vmMOR = null;
		System.out.println("Searching for VM '" + vmName + "'..");
		Map<String, ManagedObjectReference> vms = getMOREFs.inContainerByType(
				serviceContent.getRootFolder(), "VirtualMachine");
		if (vms.containsKey(vmName)) {
			System.out.println("Found VM: " + vmName);
			vmMOR = vms.get(vmName);
		} else {
			throw new IllegalStateException("No VM by the name of '" + vmName
					+ "' found!");
		}
		Map<String, Object> vmProperties = getMOREFs.entityProps(vmMOR,
				new String[] { "config.version", "config.cpuAllocation",
						"config.memoryAllocation", "config.hardware.numCPU",
						"config.hardware.memoryMB", "config.files",
						"config.swapPlacement", "config.hardware.device",
						"config.name" });

		// Setting VirtualMachineConfigSpec properties
		System.out.println("Setting VirtualMachineConfigSpec properties--");
		VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
		configSpec.setVersion((String) vmProperties.get("config.version"));
		configSpec.setCpuAllocation((ResourceAllocationInfo) vmProperties
				.get("config.cpuAllocation"));
		configSpec.setMemoryAllocation((ResourceAllocationInfo) vmProperties
				.get("config.memoryAllocation"));
		configSpec.setNumCPUs((Integer) vmProperties
				.get("config.hardware.numCPU"));
		Integer memoryinMBs = (Integer) vmProperties
				.get("config.hardware.memoryMB");
		configSpec.setMemoryMB(memoryinMBs.longValue());
		configSpec.setFiles((VirtualMachineFileInfo) vmProperties
				.get("config.files"));
		configSpec.setSwapPlacement((String) vmProperties
				.get("config.swapPlacement"));
		configSpec.setName((String) vmProperties.get("config.name"));
		List<VirtualDevice> listvd = ((ArrayOfVirtualDevice) vmProperties
				.get("config.hardware.device")).getVirtualDevice();
		for (VirtualDevice device : listvd) {
			VirtualDeviceConfigSpec vdConfigSpec = new VirtualDeviceConfigSpec();
			vdConfigSpec.setDevice(device);
			configSpec.getDeviceChange().add(vdConfigSpec);
		}
		placementSpec.setConfigSpec(configSpec);

		// Connection to Destination VC
		System.out.println("Connecting to Destination vCenter - " + remoteurl);
		DestConnect destConnect = new DestConnect();
		Connection targetConnection = destConnect.getDestConnection(remoteurl,
				ruser, rpassword);
		destVimPort = targetConnection.getVimPort();
		destServiceContent = targetConnection.getServiceContent();

		// clusters will contain the list of Clusters available
		System.out.println("Looking for the Cluster defined on Destination VC");
		Map<String, ManagedObjectReference> clusters = destConnect.destGetMOREFs
				.inContainerByType(destServiceContent.getRootFolder(),
						"ClusterComputeResource");
		if (clusters.containsKey(destCluster)) {
			clusterMor = clusters.get(destCluster);
			System.out.println("Found Cluster '" + destCluster
					+ "'on Destination vCenter!");
		} else {
			throw new IllegalStateException("No Cluster by the name of '"
					+ destCluster + "' found!");
		}
		System.out.println("Getting Recommendations from DRS for XVCvMotion--");
		PlacementAction action = new PlacementAction();
		try {
			placementResult = destVimPort.placeVm(clusterMor, placementSpec);
			action = getPlacementAction(placementResult);
		} catch (SOAPFaultException e) {
            if (e.getMessage().contains("vim.fault.InvalidState")) {
                throw new IllegalStateException(
                     "Check the Cluster setting and make sure that DRS is enabled");
            }
            else {
                throw new IllegalStateException(e.getMessage());
            }
        }

		if (action != null) {
			ManagedObjectReference vmfoldermor;

			if (targetFolder == null) {
				System.out
						.println("Target Folder undefined Using Default VM Folder");
				vmfoldermor = getVMFolderMor(clusterMor, "Folder", "vm");
			} else {
				System.out
						.println("Setting Defined TargetFolder as VMFolder for XVCvMotion");
				vmfoldermor = getVMFolderMor(clusterMor, "Folder", targetFolder);
			}
			if (vmfoldermor != null) {

				ServiceLocatorNamePassword slNamePassowrd = new ServiceLocatorNamePassword();
				slNamePassowrd.setPassword(rpassword);
				slNamePassowrd.setUsername(ruser);
				ServiceLocator locator = new ServiceLocator();
				locator.setCredential(slNamePassowrd);
				locator.setUrl(remoteurl);
				locator.setInstanceUuid(destServiceContent.getAbout()
						.getInstanceUuid());
				locator.setSslThumbprint(rthumbprint);
				VirtualMachineRelocateSpec relocateSpec = action
						.getRelocateSpec();
				relocateSpec.setService(locator);
				// Manually set the folder else Exception would be thrown
				relocateSpec.setFolder(vmfoldermor);
				System.out.println("Relocation in Progress!");
				ManagedObjectReference taskMOR = vimPort.relocateVMTask(vmMOR,
						relocateSpec,
						VirtualMachineMovePriority.DEFAULT_PRIORITY);
				if (getTaskResultAfterDone(taskMOR)) {
					System.out.println("Relocation done successfully");
				} else {
					System.out.println("Error::  Relocation failed");
				}
			} else {
				throw new IllegalStateException("No Folder by the name of '"
						+ targetFolder + "' found!");
			}
		} else {
			System.out.println("Recommendations are not correct");
		}
		destConnect.disconnect();
	}

	/**
	 * This method is to return the first valid PlacementAction out of the DRS
	 * recommendations.
	 *
	 * @param placementResult
	 * @return PlacementAction
	 * @throws RuntimeFaultFaultMsg
	 * @throws InvalidPropertyFaultMsg
	 */
	PlacementAction getPlacementAction(PlacementResult placementResult)
			throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
		List<ClusterRecommendation> recommendations = placementResult
				.getRecommendations();
		PlacementAction placementAction = null;
		int size = recommendations.size();
		boolean actionOk = false;
		if (size > 0) {
			System.out.println("Total number of recommendations are " + size);
			System.out
					.println("Processing the xvcvmotion placement recommendations out of the recommendations received");
			for (ClusterRecommendation clusterrecommend : recommendations) {
				if (clusterrecommend.getReason().equalsIgnoreCase(
						"xvmotionPlacement")) {
					List<ClusterAction> actions = clusterrecommend.getAction();
					for (ClusterAction action : actions) {
						if (action instanceof PlacementAction) {
							placementAction = (PlacementAction) action;
							break;
						}
					}
					if (placementAction != null) {
						if (placementAction.getVm() == null
								|| placementAction.getTargetHost() == null) {
							System.out
									.println("Placement Action doesnot have a vm and targethost set");
						} else {
							if (placementAction.getRelocateSpec() != null) {
								actionOk = checkRelocateSpec(placementAction
										.getRelocateSpec());
								if (actionOk)
									break;
								else
									placementAction = null;
							}
						}
					} else {
						System.out
								.println("Recommendation doesnot have a placement action");
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

/**
 * DestConnect Used to establish connection to the target Virtual Center.
 */

class DestConnect extends ConnectedVimServiceBase {

	/**
	 * This method is to establish the connection to the Target vCenter.
	 *
	 * @param url
	 *            , username, password
	 * @return Connection
	 */
	public GetMOREF destGetMOREFs;

	Connection getDestConnection(String url, String username, String password) {
		Connection connection = new BasicConnection();

		connection.setPassword(password);
		connection.setUrl(url);
		connection.setUsername(username);
		this.setHostConnection(true);
		this.setConnection(connection);
		Connection destConnection = this.connect();
		this.destGetMOREFs = getMOREFs;
		return destConnection;
	}

}
