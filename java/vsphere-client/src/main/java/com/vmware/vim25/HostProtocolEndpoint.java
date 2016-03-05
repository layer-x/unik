
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProtocolEndpoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostProtocolEndpoint">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="peType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostKey" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="storageArray" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nfsServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nfsDir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostProtocolEndpoint", propOrder = {
    "peType",
    "uuid",
    "hostKey",
    "storageArray",
    "nfsServer",
    "nfsDir",
    "deviceId"
})
public class HostProtocolEndpoint
    extends DynamicData
{

    @XmlElement(required = true)
    protected String peType;
    @XmlElement(required = true)
    protected String uuid;
    protected List<ManagedObjectReference> hostKey;
    protected String storageArray;
    protected String nfsServer;
    protected String nfsDir;
    protected String deviceId;

    /**
     * Gets the value of the peType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeType() {
        return peType;
    }

    /**
     * Sets the value of the peType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeType(String value) {
        this.peType = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the hostKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getHostKey() {
        if (hostKey == null) {
            hostKey = new ArrayList<ManagedObjectReference>();
        }
        return this.hostKey;
    }

    /**
     * Gets the value of the storageArray property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageArray() {
        return storageArray;
    }

    /**
     * Sets the value of the storageArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageArray(String value) {
        this.storageArray = value;
    }

    /**
     * Gets the value of the nfsServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNfsServer() {
        return nfsServer;
    }

    /**
     * Sets the value of the nfsServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNfsServer(String value) {
        this.nfsServer = value;
    }

    /**
     * Gets the value of the nfsDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNfsDir() {
        return nfsDir;
    }

    /**
     * Sets the value of the nfsDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNfsDir(String value) {
        this.nfsDir = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

}
