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

package com.vmware.alarms;

import java.util.Arrays;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

/**
 * <pre>
 * VMPowerStateAlarm
 *
 * This sample which creates an Alarm to monitor the virtual machine's power state
 *
 * <b>Parameters:</b>
 * url         [required] : url of the web service
 * username    [required] : username for the authentication
 * password    [required] : password for the authentication
 * vmname      [required] : Name of the virtual machine
 * alarm       [required] : Name of the alarms
 *
 * <b>Command Line:</b>
 * Create an alarm AlarmABC on a virtual machine
 * run.bat com.vmware.vm.VMPowerStateAlarm --url [webserviceurl]
 * --username [username] --password  [password] --vmname [vmname] --alarm [alarm]
 * </pre>
 */

@Sample(name = "vm-power-state-alarm", description = "This sample which creates an Alarm to monitor the virtual machine's power state")
public class VMPowerStateAlarm extends ConnectedVimServiceBase {
	private ManagedObjectReference propCollectorRef;
	private ManagedObjectReference alarmManager;
	private ManagedObjectReference vmMor;

	private String alarm = null;
	private String vmname = null;

	@Option(name = "vmname", description = "name of the virtual machine to monitor")
	public void setVmname(String vmname) {
		this.vmname = vmname;
	}

	@Option(name = "alarm", description = "Name of the alarms")
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	/**
	 * Creates the state alarm expression.
	 *
	 * @return the state alarm expression
	 * @throws Exception
	 *           the exception
	 */
	StateAlarmExpression createStateAlarmExpression() {
		StateAlarmExpression expression = new StateAlarmExpression();
		expression.setType("VirtualMachine");
		expression.setStatePath("runtime.powerState");
		expression.setOperator(StateAlarmOperator.IS_EQUAL);
		expression.setRed("poweredOff");
		return expression;
	}

	/**
	 * Creates the power on action.
	 *
	 * @return the method action
	 */
	MethodAction createPowerOnAction() {
		MethodAction action = new MethodAction();
		action.setName("PowerOnVM_Task");
		MethodActionArgument argument = new MethodActionArgument();
		argument.setValue(null);
		action.getArgument().addAll(
				Arrays.asList(new MethodActionArgument[] { argument }));
		return action;
	}

	/**
	 * Creates the alarm trigger action.
	 *
	 * @param methodAction
	 *            the method action
	 * @return the alarm triggering action
	 * @throws Exception
	 *            the exception
	 */
	AlarmTriggeringAction createAlarmTriggerAction(MethodAction methodAction) {
		AlarmTriggeringAction alarmAction = new AlarmTriggeringAction();
		alarmAction.setYellow2Red(true);
		alarmAction.setAction(methodAction);
		return alarmAction;
	}

	/**
	 * Creates the alarm spec.
	 *
	 * @param action
	 *          the action
	 * @param expression
	 *            the expression
	 * @return the alarm spec object
	 * @throws Exception
	 *             the exception
	 */
	AlarmSpec createAlarmSpec(AlarmAction action, AlarmExpression expression) {
		AlarmSpec spec = new AlarmSpec();
		spec.setAction(action);
		spec.setExpression(expression);
		spec.setName(alarm);
		spec
				.setDescription("Monitor VM state and send email if VM power's off");
		spec.setEnabled(true);
		return spec;
	}

	/**
	 * Creates the alarm.
	 *
	 * @param alarmSpec
	 *            the alarm spec object
	 * @throws Exception
	 *             the exception
	 */
	void createAlarm(AlarmSpec alarmSpec) throws DuplicateNameFaultMsg,
			RuntimeFaultFaultMsg, InvalidNameFaultMsg {
		ManagedObjectReference alarmmor = vimPort.createAlarm(alarmManager,
				vmMor, alarmSpec);
		System.out
				.println("Successfully created Alarm: " + alarmmor.getValue());
	}

	@Action
	public void run() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg,
			DuplicateNameFaultMsg, InvalidNameFaultMsg {
		propCollectorRef = serviceContent.getPropertyCollector();
		alarmManager = serviceContent.getAlarmManager();
		// Getting the MOR of the vm by using Name.
		vmMor = getMOREFs.vmByVMname(vmname, propCollectorRef);
		if (vmMor != null) {
			StateAlarmExpression expression = createStateAlarmExpression();
			MethodAction methodAction = createPowerOnAction();
			AlarmAction alarmAction = createAlarmTriggerAction(methodAction);
			AlarmSpec alarmSpec = createAlarmSpec(alarmAction, expression);
			createAlarm(alarmSpec);
		} else {
			System.out.println("Virtual Machine " + vmname + " Not Found");
		}
	}

}
