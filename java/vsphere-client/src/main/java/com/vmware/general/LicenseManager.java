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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * LicenseManager
 *
 * Demonstrates uses of the Licensing API using License Manager
 * Reference.
 *
 * <b>Parameters:</b>
 * url         [required] : url of the web service
 * username    [required] : username for the authentication
 * password    [required] : password for the authentication
 * action      [required] : action to be performed
 *                          [browse|setserver|featureinfo]
 * feature     [optional] : Licensed feature e.g. vMotion
 * licensekey  [optional] : License key for KL servers
 *
 * <b>Command Line:</b>
 * Display all license information
 * run.bat com.vmware.general.LicenseManager --url [webserviceurl]
 * --username [username] --password [password] --action[browse]
 *
 * Retrieve the feature information
 * run.bat com.vmware.general.LicenseManager --url [webserviceurl]
 * --username [username] --password [password] --action[featureinfo] --feature [drs]
 *
 * run.bat com.vmware.general.LicenseManager --url [webserviceurl]
 * --username [username] --password [password] --action[setserver] --licensekey [key]
 * </pre>
 */
@Sample(name = "license-manager", description = "Demonstrates uses of the Licensing API")
public class LicenseManager extends ConnectedVimServiceBase {

    /* Start Sample functional code */

    String action = null;
    String feature = null;
    String licenseKey = null;

    @Option(name = "action", description = "action to be performed: [browse|setserver|featureinfo]")
    public void setAction(String act) {
        this.action = act;
    }

    @Option(name = "feature", required = false, description = "licensed feature")
    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Option(name = "licensekey", required = false, description = "License key for KL servers")
    public void setLicenseKey(String key) {
        this.licenseKey = key;
    }

    private ManagedObjectReference licManagerRef = null;
    private ManagedObjectReference licenseAssignmentManagerRef = null;
    private List<LicenseAssignmentManagerLicenseAssignment> licenses;

    public void initLicManagerRef() {
        if (serviceContent != null) {
            licManagerRef = serviceContent.getLicenseManager();
        }
    }

    public void initLicAssignmentManagerRef() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        licenseAssignmentManagerRef = (ManagedObjectReference) getMOREFs.entityProps(licManagerRef,
                new String[] { "licenseAssignmentManager" }).get("licenseAssignmentManager");
    }

    public void initLicenseAssignmentManagerLicenseAssignments() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        licenses = vimPort.queryAssignedLicenses(licenseAssignmentManagerRef, null);
    }

    public void useLicenseManager() throws RuntimeFaultFaultMsg, LicenseEntityNotFoundFaultMsg {
        if (action.equalsIgnoreCase("browse")) {
            System.out.println("Display the license usage. "
                    + "It gives details of license features " + "like license key "
                    + " edition key and entity id.");
            displayLicenseUsage();
        } else if (action.equalsIgnoreCase("setkey")) {
            System.out.println("Set the license key.");
            setLicKey();
        } else if (action.equalsIgnoreCase("featureinfo")) {
            if (feature != null) {
                displayFeatureInfo();
            } else {
                throw new IllegalArgumentException("Expected --feature argument.");
            }
        } else {
            System.out.println("Invalid Action ");
            System.out.println("Valid Actions [browse|setserver|featureinfo]");
        }
    }

    public void displayLicenseUsage() throws RuntimeFaultFaultMsg {
        print(licenses);
    }

    public void setLicKey() throws RuntimeFaultFaultMsg, LicenseEntityNotFoundFaultMsg {
        boolean flag = true;
        if (licenseKey == null) {
            System.out
                    .println("Error:: For KL servers licensekey is a mandatory option");
            flag = false;
        }
        if (flag) {
            String apitype = serviceContent.getAbout().getApiType();
            if (apitype.equalsIgnoreCase("VirtualCenter")) {
                String entity = serviceContent.getAbout().getInstanceUuid();
                vimPort.updateAssignedLicense(licenseAssignmentManagerRef,
                        entity, licenseKey, null);
                System.out.println("License key set for VC server");
            } else if (apitype.equalsIgnoreCase("HostAgent")) {
                vimPort.decodeLicense(licManagerRef, licenseKey);
                vimPort.updateLicense(licManagerRef, licenseKey, null);
                System.out.println("License key set for ESX server");
            }
        }
    }

    public void displayFeatureInfo() {
        String featureName = feature;
        boolean found = false;
        Map<String, List<KeyValue>> licenseFeatures = new HashMap<String, List<KeyValue>>();
        for (LicenseAssignmentManagerLicenseAssignment license : licenses) {
            if (license.getAssignedLicense() != null
                    && license.getAssignedLicense().getProperties() != null) {
                List<KeyValue> licFeatures = new ArrayList<KeyValue>();
                for (KeyAnyValue property : license.getAssignedLicense().getProperties()) {

                    if (property.getKey().equalsIgnoreCase("feature")) {
                        KeyValue feature = (KeyValue) property.getValue();
                        if (feature != null) {
                            if (feature.getKey().equalsIgnoreCase(featureName)) {
                                found = true;
                                System.out
                                        .println("Entity Name: " + license.getEntityDisplayName());
                                System.out.println("License Name: "
                                        + license.getAssignedLicense().getName());
                                System.out.println("Feature Name: " + feature.getKey());
                                System.out.println("Description: " + feature.getValue());
                            }
                            licFeatures.add(feature);
                        }
                    }
                }
                licenseFeatures.put(license.getAssignedLicense().getName(), licFeatures);
            }
        }
        if (!found) {
            System.out.println("Could not find feature " + featureName);
            if (licenseFeatures.keySet().size() > 0) {
                System.out.println("Available features are: ");
                for (Iterator<String> iterator = licenseFeatures.keySet().iterator(); iterator
                        .hasNext();) {
                    String key = (String) iterator.next();
                    List<KeyValue> v = licenseFeatures.get(key);
                    for (KeyValue val : v)
                    {
                      System.out.println(val.getKey() +" : " +val.getValue());
                    }
                }

            }
        }
    }

    public void print(
            List<LicenseAssignmentManagerLicenseAssignment> licAssignment) {
        if (licAssignment != null) {
            for (LicenseAssignmentManagerLicenseAssignment la : licAssignment) {
                String entityId = la.getEntityId();
                String editionKey = la.getAssignedLicense().getEditionKey();
                String licensekey = la.getAssignedLicense().getLicenseKey();
                String name = la.getAssignedLicense().getName();
                System.out.println("\nName of the license: " + name
                        + "\n License Key:  " + licensekey + "\n Edition Key: "
                        + editionKey + "\n EntityID: " + entityId + "\n\n");
            }
        }
    }

    @Action
    public void action() throws RuntimeFaultFaultMsg, LicenseEntityNotFoundFaultMsg, InvalidPropertyFaultMsg {
        initLicManagerRef();
        initLicAssignmentManagerRef();
        initLicenseAssignmentManagerLicenseAssignments();
        useLicenseManager();
    }
}
