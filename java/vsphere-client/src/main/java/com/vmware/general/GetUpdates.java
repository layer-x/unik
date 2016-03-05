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

package com.vmware.general;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * <pre>
 * GetUpdates
 *
 * This sample demonstrates how to use the PropertyCollector to monitor one or more
 * properties of one or more managed objects.
 * In particular this sample monitors all or one Virtual Machine for changes to some basic properties
 *
 * <b>Parameters:</b>
 * url          [required] : url of the web service
 * username     [required] : username for the authentication
 * password     [required] : password for the authentication
 * vmname       [optional] : name of the virtual machine (if not defined sample will get the updates for all the VM under VMfolder)
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.general.GetUpdates --url [webserviceurl]
 * --username [username] --password [password]
 * --vmname  [vm name]
 * </pre>
 */
@Sample(name = "get-updates", description = "This sample demonstrates how to use the PropertyCollector to monitor one or more "
		+ "properties of one or more managed objects.")
public class GetUpdates extends ConnectedVimServiceBase {
	private String virtualmachinename;
	private ManagedObjectReference propCollectorRef;

	@Option(name = "vmname", description = "name of the virtual machine", required = false)
	public void setVmname(String name) {
		this.virtualmachinename = name;
	}

	/**
	 * @return An array of SelectionSpec covering the entitity that provide
	 *        performance statistics for Virtual Machine.
	 */
	private static SelectionSpec[] buildVMTraversal() {
		// Terminal traversal specs
		// For Folder -> Folder recursion
		SelectionSpec sspecvfolders = new SelectionSpec();
		sspecvfolders.setName("VisitFolders");
		//DC -> VMF
		TraversalSpec dcToVmf = new TraversalSpec();
		dcToVmf.setType("Datacenter");
		dcToVmf.setSkip(Boolean.FALSE);
		dcToVmf.setPath("vmFolder");
		dcToVmf.setName("dcToVmf");
		dcToVmf.getSelectSet().add(sspecvfolders);

		//Folder -> Child
		TraversalSpec visitFolders = new TraversalSpec();
		visitFolders.setType("Folder");
		visitFolders.setPath("childEntity");
		visitFolders.setSkip(Boolean.FALSE);
		visitFolders.setName("VisitFolders");
		List<SelectionSpec> sspecarrvf = new ArrayList<SelectionSpec>();
		sspecarrvf.add(dcToVmf);
		sspecarrvf.add(sspecvfolders);
		visitFolders.getSelectSet().addAll(sspecarrvf);
		return new SelectionSpec[] {visitFolders};
	}

	public void getUpdates() throws RuntimeFaultFaultMsg, IOException,
			InvalidCollectorVersionFaultMsg, InvalidPropertyFaultMsg {
		ManagedObjectReference vmRef = getMOREFs.vmByVMname(virtualmachinename,
				this.propCollectorRef);
		if (vmRef == null && virtualmachinename != null) {
			System.out.println("Virtual Machine " + virtualmachinename
					+ " Not Found");
			return;
		}
		String[][] typeInfo = { new String[] { "VirtualMachine", "name",
				"runtime" } };
		List<PropertySpec> pSpecs = buildPropertySpecArray(typeInfo);
		List<ObjectSpec> oSpecs = new ArrayList<ObjectSpec>();
		boolean oneOnly = vmRef != null;
		ObjectSpec os = new ObjectSpec();
		os.setObj(oneOnly ? vmRef : rootRef);
		os.setSkip(new Boolean(!oneOnly));
		if (!oneOnly) {
			os.getSelectSet().addAll(Arrays.asList(buildVMTraversal()));
		}
		oSpecs.add(os);
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().addAll(pSpecs);
		propertyFilterSpec.getObjectSet().addAll(oSpecs);
		ManagedObjectReference propFilter = vimPort.createFilter(
				propCollectorRef, propertyFilterSpec, false);

		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));

		String version = "";
		do {
			UpdateSet update = vimPort.waitForUpdatesEx(propCollectorRef,
					version, null);
			if (update != null && update.getFilterSet() != null) {
				handleUpdate(update);
				version = update.getVersion();
			} else {
				System.out.println("No update is present!");
			}
			System.out.println("");
			System.out.println("Press <Enter> to check for updates");
			System.out.println("Enter 'exit' <Enter> to exit the program");
			String line = console.readLine();
			if (line != null && line.trim().equalsIgnoreCase("exit")) {
				break;
			}
		} while (true);
		vimPort.destroyPropertyFilter(propFilter);
	}

	void handleUpdate(UpdateSet update) {
		List<ObjectUpdate> vmUpdates = new ArrayList<ObjectUpdate>();
		List<PropertyFilterUpdate> pfus = update.getFilterSet();

		for (PropertyFilterUpdate pfu : pfus) {
			List<ObjectUpdate> listobup = pfu.getObjectSet();
			for (ObjectUpdate oup : listobup) {
				if (oup.getObj().getType().equals("VirtualMachine")) {
					vmUpdates.add(oup);
				}
			}
		}

		if (vmUpdates.size() > 0) {
			System.out.println("Virtual Machine updates:");
			for (ObjectUpdate up : vmUpdates) {
				handleObjectUpdate(up);
			}
		}
	}

	void handleObjectUpdate(ObjectUpdate oUpdate) {
		List<PropertyChange> pc = oUpdate.getChangeSet();
		if (oUpdate.getKind() == ObjectUpdateKind.ENTER) {
			System.out.println(" New Data:");
			handleChanges(pc);
		} else if (oUpdate.getKind() == ObjectUpdateKind.LEAVE) {
			System.out.println(" Removed Data:");
			handleChanges(pc);
		} else if (oUpdate.getKind() == ObjectUpdateKind.MODIFY) {
			System.out.println(" Changed Data:");
			handleChanges(pc);
		}

	}

	void handleChanges(List<PropertyChange> changes) {
		for (int pci = 0; pci < changes.size(); ++pci) {
			String name = changes.get(pci).getName();
			Object value = changes.get(pci).getVal();
			PropertyChangeOp op = changes.get(pci).getOp();
			if (op != PropertyChangeOp.REMOVE) {
				System.out.println("  Property Name: " + name);
				if ("runtime".equals(name)) {
					if (value instanceof VirtualMachineRuntimeInfo) {
						VirtualMachineRuntimeInfo vmri = (VirtualMachineRuntimeInfo) value;
						System.out.println("   Power State: "
								+ vmri.getPowerState().toString());
						System.out.println("   Connection State: "
								+ vmri.getConnectionState().toString());
						XMLGregorianCalendar bTime = vmri.getBootTime();
						if (bTime != null) {
							System.out.println("   Boot Time: "
									+ bTime.toGregorianCalendar().getTime());
						}
						Long mOverhead = vmri.getMemoryOverhead();
						if (mOverhead != null) {
							System.out.println("   Memory Overhead: "
									+ mOverhead);
						}
					}
				} else if ("name".equals(name)) {
					System.out.println("   " + value);
				} else {
					System.out.println("   " + value.toString());
				}
			} else {
				System.out
						.println("Property Name: " + name + " value removed.");
			}
		}
	}

	/**
	 * This code takes an array of [typename, property, property, ...] and
	 * converts it into a PropertySpec[]. handles case where multiple references
	 * to the same typename are specified.
	 *
	 * @param typeinfo
	 *            2D array of type and properties to retrieve
	 * @return Array of container filter specs
	 */
	List<PropertySpec> buildPropertySpecArray(String[][] typeinfo) {
		// Eliminate duplicates
		HashMap<String, Set<String>> tInfo = new HashMap<String, Set<String>>();
		for (int ti = 0; ti < typeinfo.length; ++ti) {
			Set<String> props = tInfo.get(typeinfo[ti][0]);
			if (props == null) {
				props = new HashSet<String>();
				tInfo.put(typeinfo[ti][0], props);
			}
			boolean typeSkipped = false;
			for (int pi = 0; pi < typeinfo[ti].length; ++pi) {
				String prop = typeinfo[ti][pi];
				if (typeSkipped) {
					props.add(prop);
				} else {
					typeSkipped = true;
				}
			}
		}

		// Create PropertySpecs
		ArrayList<PropertySpec> pSpecs = new ArrayList<PropertySpec>();

		for (String type : tInfo.keySet()) {
			PropertySpec pSpec = new PropertySpec();
			Set<String> props = tInfo.get(type);
			pSpec.setType(type);
			pSpec.setAll(props.isEmpty() ? Boolean.TRUE : Boolean.FALSE);
			for (Iterator<?> pi = props.iterator(); pi.hasNext();) {
				String prop = (String) pi.next();
				pSpec.getPathSet().add(prop);
			}
			pSpecs.add(pSpec);
		}

		return pSpecs;
	}

	@Action
	public void action() throws RuntimeFaultFaultMsg, IOException,
			InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
		this.propCollectorRef = serviceContent.getPropertyCollector();
		getUpdates();
	}
}
