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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.SOAPFaultException;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.performance.widgets.LineChart;
import com.vmware.vim25.ArrayOfPerfCounterInfo;
import com.vmware.vim25.ArrayOfPerfInterval;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfInterval;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RuntimeFaultFaultMsg;

/**
 * <pre>
 * VIUsage
 *
 * This sample creates a GUI for graphical representation of the counters
 *
 * <b>Parameters:</b>
 * url        [required] : url of the web service
 * username   [required] : username for the authentication
 * password   [required] : password for the authentication
 * host       [required] : Name of the host
 * counter    [required] : Full counter name in dotted notation
 *                         (group.counter.rollup)
 *                         e.g. cpu.usage.none
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.performance.VIUsage --url [webserviceurl]
 * --username [username] --password [password]
 * --host [host name] --counter [Counter_type {group.counter.rollup}]
 * </pre>
 */

@Sample(name = "vi-usage", description = "This sample creates a GUI for graphical representation of the counters")
public class VIUsage extends ConnectedVimServiceBase implements ActionListener{
    private ManagedObjectReference perfManager;
    private ManagedObjectReference propCollectorRef;

    private String hostname;
    private String countername;

    @Option(name = "hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Option(name = "counter")
    public void setCountername(String countername) {
        this.countername = countername;
    }

    private PerfInterval[] intervals;
    private LineChart chart;
    private JPanel mainPanel, selectPanel, displayPanel;
    private JComboBox intervalBox = null;
    private JLabel chartLabel = null;
    private String stats;
    private ManagedObjectReference hostmor;
    private JFrame frame;

    /**
     * Establishes session with the virtual center server.
     *
     * @throws Exception the exception
     */
    void init() {
        propCollectorRef = serviceContent.getPropertyCollector();
        perfManager = serviceContent.getPerfManager();
    }

    /**
     * @throws Exception
     */
    void populateData() throws DatatypeConfigurationException {
        createMainPanel();
        initChart();
        updateChart();
    }

    /**
     * @throws InterruptedException
     * @throws Exception
     */
    public void displayUsage() throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException, InterruptedException {
        stats = countername;

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
            prop = getMOREFs.entityProps(perfManager, new String[] { "historicalInterval" });
        } catch (InvalidPropertyFaultMsg e) {
            e.printStackTrace();
        } catch (RuntimeFaultFaultMsg e) {
            e.printStackTrace();
        }
        ArrayOfPerfInterval arrayPerlInterval = (ArrayOfPerfInterval) prop
                .get("historicalInterval");
        List<PerfInterval> historicalInterval = arrayPerlInterval.getPerfInterval();
        intervals = new PerfInterval[historicalInterval.size()];
        for (int i = 0; i < historicalInterval.size(); i++) {
            intervals[i] = historicalInterval.get(i);
        }


        if (intervals.length == 0) {
            System.out.println("No historical intervals");
            return;
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    createAndShowGUI();
                } catch (SOAPFaultException sfe) {
                    printSoapFaultException(sfe);
                } catch (Exception ex) {
                    System.out.println("Exception -: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        Thread.currentThread().join();
    }

    /**
     *
     */
    private void initChart() {
        PerfInterval interval = intervals[intervalBox.getSelectedIndex()];
        int period = interval.getSamplingPeriod();
        int tickInterval;
        String format;
        if (period <= 300) {
            tickInterval = 3600 / period;
            format = "{3}:{4}";
        } else if (period <= 3600) {
            tickInterval = 6 * 3600 / period;
            format = "{1}/{2} {3}:{4}";
        } else {
            tickInterval = 24 * 3600 / period;
            format = "{1}/{2}";
        }
        int movingAvg = tickInterval;
        if (chart != null) {
            displayPanel.remove(chart);
        }
        chart =
                new LineChart(tickInterval, period * 1000, format, format,
                        movingAvg, true);
        chart.setPreferredSize(new Dimension(600, 150));
        displayPanel.add(chart);
        if (frame != null) {
            frame.pack();
        }
    }

    @SuppressWarnings("unchecked")
    private void updateChart() throws DatatypeConfigurationException {
        List<PerfCounterInfo> counterInfoList = new ArrayList<PerfCounterInfo>();
        Map<String, Object> prop = null;
        try {
            prop = getMOREFs.entityProps(perfManager, new String[] { "perfCounter" });
            ArrayOfPerfCounterInfo arrayPerfCounterInfo = (ArrayOfPerfCounterInfo) prop
                    .get("perfCounter");
            counterInfoList = arrayPerfCounterInfo.getPerfCounterInfo();
        } catch (SOAPFaultException sfe) {
            printSoapFaultException(sfe);
        } catch (Exception x) {
            System.out.println("Error in getting perfCounter property: " + x);
            return;
        }
        if (counterInfoList != null && !counterInfoList.isEmpty()) {
            Map<String, PerfCounterInfo> counterMap =
                    new HashMap<String, PerfCounterInfo>();
            for (PerfCounterInfo counterInfo : counterInfoList) {
                String group = counterInfo.getGroupInfo().getKey();
                String counter = counterInfo.getNameInfo().getKey();
                String rollup = counterInfo.getRollupType().value();
                String key = group + "." + counter + "." + rollup;
                counterMap.put(key, counterInfo);
            }

            List<PerfMetricId> metricIds = new ArrayList<PerfMetricId>();
            String[] statNames = new String[1];
            String key = stats;
            if (counterMap.containsKey(key)) {
                PerfCounterInfo counterInfo = counterMap.get(key);
                statNames[0] = counterInfo.getNameInfo().getLabel();
                String instance = "";
                PerfMetricId pmfids = new PerfMetricId();
                pmfids.setCounterId(counterInfo.getKey());
                pmfids.setInstance(instance);
                metricIds.add(pmfids);

            } else {
                System.out.println("Unknown counter " + key);
                for (PerfCounterInfo counterInfo : counterInfoList) {
                    String group = counterInfo.getGroupInfo().getKey();
                    String counter = counterInfo.getNameInfo().getKey();
                    String rollup = counterInfo.getRollupType().value();
                    System.out.println("Counter " + group + "." + counter + "."
                            + rollup);
                }
                System.out.println("Select The Counter From This list");
                System.exit(1);
            }

            PerfInterval interval = intervals[intervalBox.getSelectedIndex()];
            XMLGregorianCalendar endTime =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            new GregorianCalendar());
            PerfQuerySpec querySpec = new PerfQuerySpec();
            querySpec.setEntity(hostmor);
            querySpec.setFormat("csv");
            querySpec.setIntervalId(interval.getSamplingPeriod());
            //querySpec.setEndTime(endTime);
            querySpec.getMetricId().addAll(metricIds);
            List<PerfEntityMetricBase> metrics =
                    new ArrayList<PerfEntityMetricBase>();
            try {
                List<PerfQuerySpec> listpqspecs =
                        Arrays.asList(new PerfQuerySpec[]{querySpec});
                List<PerfEntityMetricBase> listpemb =
                        vimPort.queryPerf(perfManager, listpqspecs);
                metrics = listpemb;
            } catch (SOAPFaultException sfe) {
                printSoapFaultException(sfe);
            } catch (Exception x) {
                System.out.println("Error in queryPerf: " + x);
                return;
            }
            if (metrics == null || metrics.size() == 0) {
                System.out.println("queryPerf returned no entity metrics");
                return;
            }
            PerfEntityMetricBase metric = metrics.get(0);
            PerfEntityMetricCSV csvMetric = (PerfEntityMetricCSV) metric;
            List<PerfMetricSeriesCSV> lipmscsv = csvMetric.getValue();
            List<PerfMetricSeriesCSV> csvSeriesList = lipmscsv;
            if (csvSeriesList.size() == 0) {
                System.out.println("queryPerf returned no CSV series");
                return;
            }
            String statName = statNames[0];
            PerfMetricSeriesCSV csvSeries = csvSeriesList.get(0);
            String[] strValues = csvSeries.getValue().split(",");
            int[] values = new int[strValues.length];
            for (int i = 0; i < strValues.length; ++i) {
                values[i] = Integer.parseInt(strValues[i]);
            }
            chart.setValues(values, endTime.getMillisecond());
            displayPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(statName),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
    }

    private void createMainPanel() {
        selectPanel = new JPanel();
        displayPanel = new JPanel();

        chartLabel = new JLabel();
        chartLabel.setHorizontalAlignment(JLabel.CENTER);
        chartLabel.setVerticalAlignment(JLabel.CENTER);
        chartLabel.setVerticalTextPosition(JLabel.CENTER);
        chartLabel.setHorizontalTextPosition(JLabel.CENTER);

        String[] intervalNames = new String[intervals.length];
        for (int i = 0; i < intervals.length; ++i) {
            intervalNames[i] = intervals[i].getName();
        }
        intervalBox = new JComboBox(intervalNames);
        selectPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Interval"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Metric Name"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        displayPanel.add(chartLabel);
        selectPanel.add(intervalBox);
        intervalBox.addActionListener(this);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(selectPanel);
        mainPanel.add(displayPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("comboBoxChanged".equals(event.getActionCommand())) {
            System.out.println("Updating interval");
            initChart();
            try {
                updateChart();
            } catch (SOAPFaultException sfe) {
                printSoapFaultException(sfe);
            } catch (DatatypeConfigurationException ex) {
                System.out.println("Error encountered: " + ex);
            }
        }
    }

    private void createAndShowGUI() throws DatatypeConfigurationException {
        try {
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (Exception x) {
            x.printStackTrace();
        }
        populateData();
        frame = new JFrame("VIUsage");
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


        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void printSoapFaultException(SOAPFaultException sfe) {
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
    public void run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        init();
        displayUsage();
    }
}
