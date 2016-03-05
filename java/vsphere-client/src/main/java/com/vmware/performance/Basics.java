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

package com.vmware.performance;

import com.vmware.common.Main;
import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * Basics
 *
 * This sample displays available performance counters or other data
 * for an requested ESX system. Output is in following report style:
 *
 * Performance Interval:
 *    Interval Name
 *    Interval Period
 *    Interval Length
 *    Performance counters:
 *    Host perf capabilities:
 *    Summary supported
 *    Current supported
 *    Current refresh rate
 *
 * <b>Parameters:</b>
 * url          [required] : url of the web service
 * username     [required] : username for the authentication
 * password     [required] : password for the authentication
 * info         [required] : requested info - [interval|counter|host]
 * hostname     [optional] : required when 'info' is 'host'
 *
 * <b>Command Line:</b>
 * Display name and description of all perf counters on VCenter
 * run.bat com.vmware.performance.Basics --url [webserviceurl]
 * --username [username] --password [password]
 * --info [interval|counter|host] --hostname [VC hostname]
 *
 * Display counter names, sampling period, length of all intervals
 * run.bat com.vmware.performance.Basics --url [webserviceurl]
 * --username [username] --password [password]
 * --info interval --hostname [VC or ESX hostname]
 *
 * Display name and description of all perf counters on ESX
 *  run.bat com.vmware.performance.Basics --url [webserviceurl]
 *  --username [username] --password [password]
 * --info counter --hostname [ESX hostname]
 *
 * Display name, description and metrics of all perf counters on ESX
 * run.bat com.vmware.performance.Basics --url [webserviceurl]
 * --username [username] --password [password]
 * --info host  --hostname [ESX hostname]
 * </pre>
 */
@Sample(name = "performance-basics", description = "displays available performance counters or other data")
public class Basics extends ConnectedVimServiceBase {
    private ManagedObjectReference propCollectorRef;
    private ManagedObjectReference perfManager;

    private String info;
    private String hostname;

    @Option(name = "info",description = "requested info - [interval|counter|host]")
    public void setInfo(String info) {
        this.info = info;
    }

    @Option(name="hostname", required = false,description = "required when 'info' is 'host'")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    void validateTheInput() {
        if (info.equalsIgnoreCase("host")) {
            if (hostname == null) {
                throw new Main.SampleInputValidationException("Must specify the --hostname"
                        + " parameter when --info is host");
            }
        }
        return;
    }

    void displayBasics() throws RuntimeFaultFaultMsg, DatatypeConfigurationException,
            InvalidPropertyFaultMsg {

        if (info.equalsIgnoreCase("interval")) {
            getIntervals(perfManager, vimPort);
        } else if (info.equalsIgnoreCase("counter")) {
            getCounters(perfManager, vimPort);
        } else if (info.equalsIgnoreCase("host")) {
            Map<String, ManagedObjectReference> results = getMOREFs.inFolderByType(serviceContent
                    .getRootFolder(), "HostSystem", new RetrieveOptions());

            ManagedObjectReference hostmor = results.get(hostname);
            if (hostmor == null) {
                System.out.println("Host " + hostname + " not found");
                return;
            }
            getQuerySummary(perfManager, hostmor, vimPort);
            getQueryAvailable(perfManager, hostmor, vimPort);
        } else {
            System.out.println("Invalid info argument [host|counter|interval]");
        }
    }

    void getIntervals(ManagedObjectReference perfMgr,
                      VimPortType service) throws InvalidPropertyFaultMsg,
            RuntimeFaultFaultMsg {
        Object property = getMOREFs.entityProps(perfMgr, new String[] { "historicalInterval" })
                .get("historicalInterval");
        ArrayOfPerfInterval arrayInterval = (ArrayOfPerfInterval) property;
        List<PerfInterval> intervals = arrayInterval.getPerfInterval();
        System.out.println("Performance intervals (" + intervals.size() + "):");
        System.out.println("---------------------");

        int count = 0;
        for (PerfInterval interval : intervals) {
            System.out.print((++count) + ": " + interval.getName());
            System.out.print(" -- period = " + interval.getSamplingPeriod());
            System.out.println(", length = " + interval.getLength());
        }
        System.out.println();
    }

    void getCounters(ManagedObjectReference perfMgr,
                     VimPortType service) throws InvalidPropertyFaultMsg,
            RuntimeFaultFaultMsg {
        Object property = getMOREFs.entityProps(perfMgr, new String[] { "perfCounter" }).get(
                "perfCounter");
        ArrayOfPerfCounterInfo arrayCounter = (ArrayOfPerfCounterInfo) property;
        List<PerfCounterInfo> counters = arrayCounter.getPerfCounterInfo();
        System.out.println("Performance counters (averages only):");
        System.out.println("-------------------------------------");
        for (PerfCounterInfo counter : counters) {
            if (counter.getRollupType() == PerfSummaryType.AVERAGE) {
                ElementDescription desc = counter.getNameInfo();
                System.out.println(desc.getLabel() + ": " + desc.getSummary());
            }
        }
        System.out.println();
    }

    void getQuerySummary(ManagedObjectReference perfMgr,
                         ManagedObjectReference hostmor, VimPortType service)
            throws RuntimeFaultFaultMsg {
        PerfProviderSummary summary =
                service.queryPerfProviderSummary(perfMgr, hostmor);
        System.out.println("Host perf capabilities:");
        System.out.println("----------------------");
        System.out
                .println("  Summary supported: " + summary.isSummarySupported());
        System.out
                .println("  Current supported: " + summary.isCurrentSupported());
        if (summary.isCurrentSupported()) {
            System.out.println("  Current refresh rate: "
                    + summary.getRefreshRate());
        }
        System.out.println();
    }

   void getQueryAvailable(ManagedObjectReference perfMgr,
                           ManagedObjectReference hostmor, VimPortType service)
            throws DatatypeConfigurationException, RuntimeFaultFaultMsg {

        PerfProviderSummary perfProviderSummary =
                service.queryPerfProviderSummary(perfMgr, hostmor);
        List<PerfMetricId> pmidlist =
                service.queryAvailablePerfMetric(perfMgr, hostmor, null, null,
                        perfProviderSummary.getRefreshRate());

        List<Integer> idslist = new ArrayList<Integer>();

        for (int i = 0; i != pmidlist.size(); ++i) {
            idslist.add(pmidlist.get(i).getCounterId());
        }

        List<PerfCounterInfo> pcinfolist =
                service.queryPerfCounter(perfMgr, idslist);
        System.out.println("Available real-time metrics for host ("
                + pmidlist.size() + "):");
        System.out.println("--------------------------");
        for (int i = 0; i != pmidlist.size(); ++i) {
            String label = pcinfolist.get(i).getNameInfo().getLabel();
            String instance = pmidlist.get(i).getInstance();
            System.out.print("   " + label);
            if (instance.length() != 0) {
                System.out.print(" [" + instance + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Action
    public void run() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, DatatypeConfigurationException {
        validateTheInput();
        propCollectorRef = serviceContent.getPropertyCollector();
        perfManager = serviceContent.getPerfManager();
        displayBasics();
    }
}