
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualResourcePoolSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualResourcePoolSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vrpId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vrpName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpuAllocation" type="{urn:vim25}VrpResourceAllocationInfo"/>
 *         &lt;element name="memoryAllocation" type="{urn:vim25}VrpResourceAllocationInfo"/>
 *         &lt;element name="rpList" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hubList" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rootVRP" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="staticVRP" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "VirtualResourcePoolSpec", propOrder = {
    "vrpId",
    "vrpName",
    "description",
    "cpuAllocation",
    "memoryAllocation",
    "rpList",
    "hubList",
    "rootVRP",
    "staticVRP",
    "changeVersion"
})
public class VirtualResourcePoolSpec
    extends DynamicData
{

    protected String vrpId;
    protected String vrpName;
    protected String description;
    @XmlElement(required = true)
    protected VrpResourceAllocationInfo cpuAllocation;
    @XmlElement(required = true)
    protected VrpResourceAllocationInfo memoryAllocation;
    protected List<ManagedObjectReference> rpList;
    protected List<ManagedObjectReference> hubList;
    protected Boolean rootVRP;
    protected Boolean staticVRP;
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
     * Gets the value of the vrpName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVrpName() {
        return vrpName;
    }

    /**
     * Sets the value of the vrpName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVrpName(String value) {
        this.vrpName = value;
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
     * Gets the value of the rpList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rpList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRpList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getRpList() {
        if (rpList == null) {
            rpList = new ArrayList<ManagedObjectReference>();
        }
        return this.rpList;
    }

    /**
     * Gets the value of the hubList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hubList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHubList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getHubList() {
        if (hubList == null) {
            hubList = new ArrayList<ManagedObjectReference>();
        }
        return this.hubList;
    }

    /**
     * Gets the value of the rootVRP property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRootVRP() {
        return rootVRP;
    }

    /**
     * Sets the value of the rootVRP property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRootVRP(Boolean value) {
        this.rootVRP = value;
    }

    /**
     * Gets the value of the staticVRP property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStaticVRP() {
        return staticVRP;
    }

    /**
     * Sets the value of the staticVRP property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStaticVRP(Boolean value) {
        this.staticVRP = value;
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
