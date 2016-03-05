
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hostSystem" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *         &lt;element name="clusterInfo" type="{urn:vim25}VsanHostConfigInfoClusterInfo" minOccurs="0"/>
 *         &lt;element name="storageInfo" type="{urn:vim25}VsanHostConfigInfoStorageInfo" minOccurs="0"/>
 *         &lt;element name="networkInfo" type="{urn:vim25}VsanHostConfigInfoNetworkInfo" minOccurs="0"/>
 *         &lt;element name="faultDomainInfo" type="{urn:vim25}VsanHostFaultDomainInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostConfigInfo", propOrder = {
    "enabled",
    "hostSystem",
    "clusterInfo",
    "storageInfo",
    "networkInfo",
    "faultDomainInfo"
})
public class VsanHostConfigInfo
    extends DynamicData
{

    protected Boolean enabled;
    protected ManagedObjectReference hostSystem;
    protected VsanHostConfigInfoClusterInfo clusterInfo;
    protected VsanHostConfigInfoStorageInfo storageInfo;
    protected VsanHostConfigInfoNetworkInfo networkInfo;
    protected VsanHostFaultDomainInfo faultDomainInfo;

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the hostSystem property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getHostSystem() {
        return hostSystem;
    }

    /**
     * Sets the value of the hostSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setHostSystem(ManagedObjectReference value) {
        this.hostSystem = value;
    }

    /**
     * Gets the value of the clusterInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostConfigInfoClusterInfo }
     *     
     */
    public VsanHostConfigInfoClusterInfo getClusterInfo() {
        return clusterInfo;
    }

    /**
     * Sets the value of the clusterInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostConfigInfoClusterInfo }
     *     
     */
    public void setClusterInfo(VsanHostConfigInfoClusterInfo value) {
        this.clusterInfo = value;
    }

    /**
     * Gets the value of the storageInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostConfigInfoStorageInfo }
     *     
     */
    public VsanHostConfigInfoStorageInfo getStorageInfo() {
        return storageInfo;
    }

    /**
     * Sets the value of the storageInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostConfigInfoStorageInfo }
     *     
     */
    public void setStorageInfo(VsanHostConfigInfoStorageInfo value) {
        this.storageInfo = value;
    }

    /**
     * Gets the value of the networkInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostConfigInfoNetworkInfo }
     *     
     */
    public VsanHostConfigInfoNetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    /**
     * Sets the value of the networkInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostConfigInfoNetworkInfo }
     *     
     */
    public void setNetworkInfo(VsanHostConfigInfoNetworkInfo value) {
        this.networkInfo = value;
    }

    /**
     * Gets the value of the faultDomainInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostFaultDomainInfo }
     *     
     */
    public VsanHostFaultDomainInfo getFaultDomainInfo() {
        return faultDomainInfo;
    }

    /**
     * Sets the value of the faultDomainInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostFaultDomainInfo }
     *     
     */
    public void setFaultDomainInfo(VsanHostFaultDomainInfo value) {
        this.faultDomainInfo = value;
    }

}
