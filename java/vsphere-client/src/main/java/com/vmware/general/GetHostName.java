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
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.connection.VCenterSampleBase;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * GetHostName
 *
 * This sample gets the hostname and additional details of the ESX Servers
 * in the inventory
 *
 * <b>Parameters:</b>
 * url          [required] : url of the web service
 * username     [required] : username for the authentication
 * password     [required] : password for the authentication
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.general.GetHostName
 * --url [webservicesurl] --username [username] --password [password]
 * </pre>
 */
@Sample(name = "get-hostname", description = "This sample gets the hostname and additional details of the ESX Servers in the inventory")
public class GetHostName extends VCenterSampleBase {
    private static final List<String> hostSystemAttributesArr =
            new ArrayList<String>();

    public void setHostSystemAttributesList() {
        hostSystemAttributesArr.add("name");
        hostSystemAttributesArr.add("config.product.productLineId");
        hostSystemAttributesArr.add("summary.hardware.cpuMhz");
        hostSystemAttributesArr.add("summary.hardware.numCpuCores");
        hostSystemAttributesArr.add("summary.hardware.cpuModel");
        hostSystemAttributesArr.add("summary.hardware.uuid");
        hostSystemAttributesArr.add("summary.hardware.vendor");
        hostSystemAttributesArr.add("summary.hardware.model");
        hostSystemAttributesArr.add("summary.hardware.memorySize");
        hostSystemAttributesArr.add("summary.hardware.numNics");
        hostSystemAttributesArr.add("summary.config.name");
        hostSystemAttributesArr.add("summary.config.product.osType");
        hostSystemAttributesArr.add("summary.config.vmotionEnabled");
        hostSystemAttributesArr.add("summary.quickStats.overallCpuUsage");
        hostSystemAttributesArr.add("summary.quickStats.overallMemoryUsage");
    }

    /**
     * Prints the Host names.
     *
     * @throws RuntimeFaultFaultMsg
     * @throws InvalidPropertyFaultMsg
     */
    @Action
    public void printHostProductDetails() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        setHostSystemAttributesList();
        Map<ManagedObjectReference, Map<String, Object>> hosts =
                getMOREFs.inContainerByType(serviceContent.getRootFolder(),
                        "HostSystem",
                        hostSystemAttributesArr.toArray(new String[]{}));

        for (ManagedObjectReference host : hosts.keySet()) {
            Map<String, Object> hostprops = hosts.get(host);
            for (String prop : hostprops.keySet()) {
                System.out.println(prop + " : " + hostprops.get(prop));
            }
            System.out
                    .println("\n\n***************************************************************");
        }
    }
}