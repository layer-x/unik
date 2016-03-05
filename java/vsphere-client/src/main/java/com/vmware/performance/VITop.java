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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.SOAPFaultException;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.performance.widgets.StatsTable;
import com.vmware.vim25.ArrayOfPerfCounterInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfProviderSummary;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RuntimeFaultFaultMsg;

/**
 * <pre>
 * VITop
 *
 * This sample is an ESX-Top-like application that lets administrators specify
 * the CPU and memory counters by name to obtain metrics for a specified host
 *
 * <b>Parameters:</b>
 * url        [required] : url of the web service
 * username   [required] : username for the authentication
 * password   [required] : password for the authentication
 * host       [required] : name of the host
 * cpu        [required] : CPU counter name
 *                         e.g. usage, ready, guaranteed
 * memory     [required] : memory counter name
 *                         e.g. usage, granted
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.performance.VITop
 * --url [webservice url] --username [user] --password [password]
 * --host [FQDN_host_name]
 * --cpu [cpu_counter_name] --memory [mem_counter_name]
 * </pre>
 */
@Sample(
        name = "vi-top",
        description = "an ESX-Top-like application that lets " +
                "administrators specify the CPU and memory " +
                "counters by name to obtain metrics for a specified host"
)
public class VITop extends ConnectedVimServiceBase{
    String hostname;
    String cpu;
    String memory;
    StatsTable statsTable;
    ManagedObjectReference perfManager;
    PerfQuerySpec querySpec;

    private ManagedObjectReference hostmor;

    @Option(name = "host", description = "name of the host")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Option(name = "cpu", description = "CPU counter name [usage|ready|guaranteed]")
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Option(name = "memory", description = "memory counter name [usage|granted]")
    public void setMemory(String memory) {
        this.memory = memory;
    }

    void createAndShowGUI(String firstColumnName, List<String> statNames) {
        try {
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (SOAPFaultException sfe) {
            printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("VITop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    connection.disconnect();
                } catch (SOAPFaultException sfe) {
                    printSoapFaultException(sfe);
                } catch (Exception ex) {
                    System.out.println("Failed to disconnect - " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }
        });

        String[] columnNamesArray = new String[statNames.size() + 1];
        columnNamesArray[0] = firstColumnName;
        for (int i = 0; i < statNames.size(); i++) {
            columnNamesArray[i + 1] = statNames.get(i);
        }
        statsTable = new StatsTable(columnNamesArray);
        statsTable.setOpaque(true);
        frame.setContentPane(statsTable);

        frame.pack();
        frame.setVisible(true);
    }

    String getEntityName(ManagedObjectReference moRef) throws InvalidPropertyFaultMsg,
            RuntimeFaultFaultMsg {
        String ret = null;
        Map<String, Object> prop = null;
        try {
            prop = getMOREFs.entityProps(moRef, new String[] { "name" });
        } catch (InvalidPropertyFaultMsg e) {
            e.printStackTrace();
        } catch (RuntimeFaultFaultMsg e) {
            e.printStackTrace();
        }
        try {
            ret = (String) prop.get("name");
        } catch (SOAPFaultException sfe) {
            printSoapFaultException(sfe);
        } catch (Exception e) {
            return "<Unknown Entity>";
        }

        if (ret != null) {
            return ret;
        } else {
            return "<Unknown Entity>";
        }
    }

    /**
     * @param midList
     * @param compMetric
     * @return
     * @throws RuntimeException
     * @throws RemoteException
     * @throws InvalidPropertyFaultMsg
     * @throws RuntimeFaultFaultMsg
     */
    XMLGregorianCalendar displayStats(List<PerfMetricId> midList,
                                      PerfCompositeMetric compMetric) throws RuntimeException,
            RemoteException, InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        if (compMetric == null || (compMetric.getEntity() == null)) {
            return null;
        }

        List<Object[]> data = new ArrayList<Object[]>();
        PerfEntityMetric entityMetric = (PerfEntityMetric) compMetric.getEntity();
        PerfMetricIntSeries intSeries =
                (PerfMetricIntSeries) entityMetric.getValue().get(0);
        int numSamples = entityMetric.getSampleInfo().size();

        XMLGregorianCalendar timeStamp =
                entityMetric.getSampleInfo().get(numSamples - 1).getTimestamp();
        long overallUsage = intSeries.getValue().get(numSamples - 1);
        System.out.println("Info Updated");
        int numColumns = midList.size() + 1;
        List<PerfEntityMetricBase> listpemb = compMetric.getChildEntity();
        List<PerfEntityMetricBase> childEntityMetric = listpemb;
        for (int childNum = 0; childNum < childEntityMetric.size(); childNum++) {
            PerfEntityMetric childStats =
                    (PerfEntityMetric) childEntityMetric.get(childNum);
            String childName = getEntityName(childStats.getEntity());
            int numChildSamples = childStats.getSampleInfo().size();
            Object[] tableData = new Object[numColumns];
            tableData[0] = childName;

            for (int i = 0; i < childStats.getValue().size(); i++) {
                PerfMetricIntSeries childSeries =
                        (PerfMetricIntSeries) childStats.getValue().get(i);
                int col = findStatsIndex(midList, childSeries.getId());
                if (col >= 0) {
                    long statsVal = childSeries.getValue().get(numChildSamples - 1);
                    tableData[col + 1] = new Long(statsVal);
                }
            }
            data.add(tableData);
        }

        if (statsTable != null) {
            statsTable.setData(timeStamp.toGregorianCalendar(), overallUsage,
                    "Mhz", data);
        }
        return timeStamp;
    }

    int findStatsIndex(List<PerfMetricId> midList,
                       PerfMetricId mid) {
        int count = 0;
        for (PerfMetricId pmid : midList) {
            if ((pmid.getCounterId() == mid.getCounterId())
                    && pmid.getInstance().equals(mid.getInstance())) {
                return count;
            }
            ++count;
        }
        return -1;
    }

    PerfCounterInfo getCounterInfo(
            List<PerfCounterInfo> counterInfo, String groupName, String counterName) {
        for (PerfCounterInfo info : counterInfo) {
            if (info.getGroupInfo().getKey().equals(groupName)
                    && info.getNameInfo().getKey().equals(counterName)) {
                return info;
            }
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    String[][] getCounters() {

        String[] cpuCounters = cpu.split(",");
        String[] memCounters = memory.split(",");
        String[][] ret = new String[(cpuCounters.length + memCounters.length)][2];

        for (int i = 0; i < cpuCounters.length; i++) {
            ret[i] = new String[]{"cpu", cpuCounters[i]};
        }

        for (int i = 0; i < memCounters.length; i++) {
            ret[(cpuCounters.length + i)] = new String[]{"mem", memCounters[i]};
        }
        return ret;
    }

    /**
    *
    */
   void refreshStats() {
       try {
           PerfCompositeMetric metric =
                   vimPort.queryPerfComposite(perfManager, querySpec);
           XMLGregorianCalendar latestTs =
                   displayStats(querySpec.getMetricId(), metric);
           if (latestTs != null) {
               querySpec.setStartTime(latestTs);
           }
       } catch (SOAPFaultException sfe) {
           printSoapFaultException(sfe);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }

    /**
     * @throws InterruptedException
     * @throws Exception
     */
    void displayStats() throws RuntimeFaultFaultMsg, InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, InterruptedException {
        String[][] statsList = getCounters();
        Map<String, ManagedObjectReference> results = null;
        try {
            results = getMOREFs.inFolderByType(serviceContent.getRootFolder(), "HostSystem",
                    new RetrieveOptions());
        } catch (RuntimeFaultFaultMsg e) {
            e.printStackTrace();
        } catch (InvalidPropertyFaultMsg e) {
            e.printStackTrace();
        }

        hostmor = results.get(hostname);

        if (hostmor == null) {
            System.out.println("Host " + hostname + " Not Found");
            return;
        }
        Map<String, Object> prop = null;
        try {
            prop = getMOREFs.entityProps(perfManager, new String[] { "perfCounter" });
        } catch (InvalidPropertyFaultMsg e) {
            e.printStackTrace();
        } catch (RuntimeFaultFaultMsg e) {
            e.printStackTrace();
        }
        ArrayOfPerfCounterInfo arrayPerfCounterInfo = (ArrayOfPerfCounterInfo) prop
                .get("perfCounter");
        List<PerfCounterInfo> props = arrayPerfCounterInfo.getPerfCounterInfo();
        List<PerfMetricId> midVector = new ArrayList<PerfMetricId>();
        List<String> statNames = new ArrayList<String>();
        for (int i = 0; i < statsList.length; i++) {
            PerfCounterInfo counterInfo = getCounterInfo(props, statsList[i][0], statsList[i][1]);
            if (counterInfo == null) {
                System.out.println("Warning: Unable to find stat "
                        + statsList[i][0] + " " + statsList[i][1]);
                continue;
            }
            String counterName = counterInfo.getNameInfo().getLabel();
            statNames.add(counterName);

            PerfMetricId pmid = new PerfMetricId();
            pmid.setCounterId(counterInfo.getKey());
            pmid.setInstance("");
            midVector.add(pmid);
        }
        List<PerfMetricId> midList = new ArrayList<PerfMetricId>(midVector);
        Collections.copy(midList, midVector);


        PerfProviderSummary perfProviderSummary =
                vimPort.queryPerfProviderSummary(perfManager, hostmor);
        PerfQuerySpec spec = new PerfQuerySpec();
        spec.setEntity(hostmor);
        spec.getMetricId().addAll(midList);
        spec.setIntervalId(perfProviderSummary.getRefreshRate());
        querySpec = spec;

        final List<String> statNames2 = statNames;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI("VM Name", statNames2);
            }
        });

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                refreshStats();
            }
        }, 1000, 21000);
        Thread.currentThread().join();
    }

    void printSoapFaultException(SOAPFaultException sfe) {
        System.out.println("SOAP Fault -");
        if (sfe.getFault().hasDetail()) {
            System.out.println(sfe.getFault().getDetail().getFirstChild()
                    .getLocalName());
        }
        if (sfe.getFault().getFaultString() != null) {
            System.out.println("\n Message: " + sfe.getFault().getFaultString());
        }
    }

    @Action
    public void run() throws RuntimeFaultFaultMsg, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        perfManager = serviceContent.getPerfManager();
        displayStats();
    }
}
