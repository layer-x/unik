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

package com.vmware.host;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Option;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * AcquireSessionInfo
 *
 * This sample will acquire a session with VC or ESX
 * and print a cim service ticket and related
 * session information to a file
 *
 * <b>Parameters:</b>
 * url         [required] : url of the web service
 * username    [required] : username for the authentication
 * password    [required] : password for the authentication
 * host        [required] : Name of the host
 * info        [optional] : Type of info required
 *                          only [cimticket] for now
 * file        [optional] : Full path of the file to save data to
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.general.Browser --url [webserviceurl]
 * --username [username] --password [password]
 * --host [hostname] --info [password] --file [path_to_file]
 * </pre>
 */
@Sample(
        name = "acquire-session-info",
        description = "This sample will acquire a session with VC or ESX " +
                "and print a cim service ticket and related session information to a file"
)
public class AcquireSessionInfo extends ConnectedVimServiceBase {
    private ManagedObjectReference propCollectorRef;

    private String hostname;
    private String info;
    private String filename;

    @Option(name = "host", description = "name of host")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Option(name = "info", required = false, description = "Type of info required [cimticket]")
    public void setInfo(String info) {
        this.info = info;
    }

    @Option(name = "file", required = false, description = "Full path of the file to save data to")
    public void setFilename(String filename) {
        this.filename = filename;
    }

  /**
     * Uses the new RetrievePropertiesEx method to emulate the now deprecated
     * RetrieveProperties method
     *
     * @param listpfs
     * @return list of object content
     * @throws Exception
     */
    List<ObjectContent> retrievePropertiesAllObjects(List<PropertyFilterSpec> listpfs) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {

        RetrieveOptions propObjectRetrieveOpts = new RetrieveOptions();

        List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();

        RetrieveResult rslts =
                vimPort.retrievePropertiesEx(propCollectorRef, listpfs,
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
            rslts =
                    vimPort.continueRetrievePropertiesEx(propCollectorRef, token);
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

    String stringToWrite(HostServiceTicket serviceTicket) {
        String sslThumbprint = "undefined";
        String host = "undefined";
        String port = "undefined";
        String service = serviceTicket.getService();
        String serviceVersion = serviceTicket.getServiceVersion();
        String serviceSessionId = serviceTicket.getSessionId();

        if (serviceTicket.getSslThumbprint() != null) {
            sslThumbprint = serviceTicket.getSslThumbprint();

        }
        if (serviceTicket.getHost() != null) {
            host = serviceTicket.getHost();

        }
        if (serviceTicket.getPort() != null) {
            port = Integer.toString(serviceTicket.getPort());

        }
        StringBuilder datatowrite = new StringBuilder("");
        datatowrite.append("CIM Host Service Ticket Information\n");
        datatowrite.append("Service        : ");
        datatowrite.append(service);
        datatowrite.append("\n");
        datatowrite.append("Service Version: ");
        datatowrite.append(serviceVersion);
        datatowrite.append("\n");
        datatowrite.append("Session Id     : ");
        datatowrite.append(serviceSessionId);
        datatowrite.append("\n");
        datatowrite.append("SSL Thumbprint : ");
        datatowrite.append(sslThumbprint);
        datatowrite.append("\n");
        datatowrite.append("Host           : ");
        datatowrite.append(host);
        datatowrite.append("\n");
        datatowrite.append("Port           : ");
        datatowrite.append(port);
        datatowrite.append("\n");
        System.out.println("CIM Host Service Ticket Information\n");
        System.out.println("Service           : " + service);
        System.out.println("Service Version   : " + serviceVersion);
        System.out.println("Session Id        : " + serviceSessionId);
        System.out.println("SSL Thumbprint    : " + sslThumbprint);
        System.out.println("Host              : " + host);
        System.out.println("Port              : " + port);
        return datatowrite.toString();
    }

    void acquireSessionInfo() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, IOException {
        Map<String, ManagedObjectReference> results = getMOREFs.inFolderByType(serviceContent
                .getRootFolder(), "HostSystem", new RetrieveOptions());

        ManagedObjectReference hostmor = results.get(hostname);
        if (hostmor == null) {
            String msg = "Failure: Host [ " + hostname + "] not found";
            throw new HostFailure(msg);
        }

        if ((info == null) || (info.equalsIgnoreCase("cimticket"))) {

            HostServiceTicket serviceTicket =
                    vimPort.acquireCimServicesTicket(hostmor);

            if (serviceTicket != null) {
                String datatoWrite = stringToWrite(serviceTicket);
                writeToFile(datatoWrite, filename);
            }
        } else {
            System.out.println("Support for " + info + " not implemented.");
        }
    }

    void writeToFile(String data, String fileName)
            throws IOException {
        fileName = fileName == null ? "cimTicketInfo.txt" : fileName;
        File cimFile = new File(fileName);
        FileOutputStream fop = new FileOutputStream(cimFile);
        if (cimFile.exists()) {
            String str = data;
            fop.write(str.getBytes());
            fop.flush();
            fop.close();
            System.out.println("Saved session information at "
                    + cimFile.getAbsolutePath());
        }
    }

    @Action
    public void run() throws RuntimeFaultFaultMsg, IOException, InvalidPropertyFaultMsg {
        propCollectorRef = serviceContent.getPropertyCollector();
        acquireSessionInfo();
    }

    private class HostFailure extends RuntimeException {
		private static final long serialVersionUID = 1L;
        public HostFailure(String msg) {
            super(msg);
        }
    }
}

