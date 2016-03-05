
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterDasAdvancedRuntimeInfoVmcpCapabilityInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterDasAdvancedRuntimeInfoVmcpCapabilityInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="storageAPDSupported" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="storagePDLSupported" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterDasAdvancedRuntimeInfoVmcpCapabilityInfo", propOrder = {
    "storageAPDSupported",
    "storagePDLSupported"
})
public class ClusterDasAdvancedRuntimeInfoVmcpCapabilityInfo
    extends DynamicData
{

    protected boolean storageAPDSupported;
    protected boolean storagePDLSupported;

    /**
     * Gets the value of the storageAPDSupported property.
     * 
     */
    public boolean isStorageAPDSupported() {
        return storageAPDSupported;
    }

    /**
     * Sets the value of the storageAPDSupported property.
     * 
     */
    public void setStorageAPDSupported(boolean value) {
        this.storageAPDSupported = value;
    }

    /**
     * Gets the value of the storagePDLSupported property.
     * 
     */
    public boolean isStoragePDLSupported() {
        return storagePDLSupported;
    }

    /**
     * Sets the value of the storagePDLSupported property.
     * 
     */
    public void setStoragePDLSupported(boolean value) {
        this.storagePDLSupported = value;
    }

}
