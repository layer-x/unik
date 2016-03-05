
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostGraphicsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostGraphicsInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="deviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vendorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pciId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="graphicsType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="memorySizeInKB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vm" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostGraphicsInfo", propOrder = {
    "deviceName",
    "vendorName",
    "pciId",
    "graphicsType",
    "memorySizeInKB",
    "vm"
})
public class HostGraphicsInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected String deviceName;
    @XmlElement(required = true)
    protected String vendorName;
    @XmlElement(required = true)
    protected String pciId;
    @XmlElement(required = true)
    protected String graphicsType;
    protected long memorySizeInKB;
    protected List<ManagedObjectReference> vm;

    /**
     * Gets the value of the deviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Sets the value of the deviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceName(String value) {
        this.deviceName = value;
    }

    /**
     * Gets the value of the vendorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Sets the value of the vendorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorName(String value) {
        this.vendorName = value;
    }

    /**
     * Gets the value of the pciId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPciId() {
        return pciId;
    }

    /**
     * Sets the value of the pciId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPciId(String value) {
        this.pciId = value;
    }

    /**
     * Gets the value of the graphicsType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGraphicsType() {
        return graphicsType;
    }

    /**
     * Sets the value of the graphicsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGraphicsType(String value) {
        this.graphicsType = value;
    }

    /**
     * Gets the value of the memorySizeInKB property.
     * 
     */
    public long getMemorySizeInKB() {
        return memorySizeInKB;
    }

    /**
     * Sets the value of the memorySizeInKB property.
     * 
     */
    public void setMemorySizeInKB(long value) {
        this.memorySizeInKB = value;
    }

    /**
     * Gets the value of the vm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getVm() {
        if (vm == null) {
            vm = new ArrayList<ManagedObjectReference>();
        }
        return this.vm;
    }

}
