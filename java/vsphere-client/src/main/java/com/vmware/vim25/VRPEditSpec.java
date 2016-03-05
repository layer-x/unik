
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VRPEditSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VRPEditSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vrpId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpuAllocation" type="{urn:vim25}VrpResourceAllocationInfo" minOccurs="0"/>
 *         &lt;element name="memoryAllocation" type="{urn:vim25}VrpResourceAllocationInfo" minOccurs="0"/>
 *         &lt;element name="addedHubs" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removedHubs" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="changeVersion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VRPEditSpec", propOrder = {
    "vrpId",
    "description",
    "cpuAllocation",
    "memoryAllocation",
    "addedHubs",
    "removedHubs",
    "changeVersion"
})
public class VRPEditSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected String vrpId;
    protected String description;
    protected VrpResourceAllocationInfo cpuAllocation;
    protected VrpResourceAllocationInfo memoryAllocation;
    protected List<ManagedObjectReference> addedHubs;
    protected List<ManagedObjectReference> removedHubs;
    protected Long changeVersion;

    /**
     * Gets the value of the vrpId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVrpId() {
        return vrpId;
    }

    /**
     * Sets the value of the vrpId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVrpId(String value) {
        this.vrpId = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the cpuAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link VrpResourceAllocationInfo }
     *     
     */
    public VrpResourceAllocationInfo getCpuAllocation() {
        return cpuAllocation;
    }

    /**
     * Sets the value of the cpuAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link VrpResourceAllocationInfo }
     *     
     */
    public void setCpuAllocation(VrpResourceAllocationInfo value) {
        this.cpuAllocation = value;
    }

    /**
     * Gets the value of the memoryAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link VrpResourceAllocationInfo }
     *     
     */
    public VrpResourceAllocationInfo getMemoryAllocation() {
        return memoryAllocation;
    }

    /**
     * Sets the value of the memoryAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link VrpResourceAllocationInfo }
     *     
     */
    public void setMemoryAllocation(VrpResourceAllocationInfo value) {
        this.memoryAllocation = value;
    }

    /**
     * Gets the value of the addedHubs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addedHubs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddedHubs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getAddedHubs() {
        if (addedHubs == null) {
            addedHubs = new ArrayList<ManagedObjectReference>();
        }
        return this.addedHubs;
    }

    /**
     * Gets the value of the removedHubs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removedHubs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemovedHubs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getRemovedHubs() {
        if (removedHubs == null) {
            removedHubs = new ArrayList<ManagedObjectReference>();
        }
        return this.removedHubs;
    }

    /**
     * Gets the value of the changeVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getChangeVersion() {
        return changeVersion;
    }

    /**
     * Sets the value of the changeVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setChangeVersion(Long value) {
        this.changeVersion = value;
    }

}
