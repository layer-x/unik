
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlacementSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlacementSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="priority" type="{urn:vim25}VirtualMachineMovePriority" minOccurs="0"/>
 *         &lt;element name="vm" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *         &lt;element name="configSpec" type="{urn:vim25}VirtualMachineConfigSpec" minOccurs="0"/>
 *         &lt;element name="relocateSpec" type="{urn:vim25}VirtualMachineRelocateSpec" minOccurs="0"/>
 *         &lt;element name="hosts" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="datastores" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="storagePods" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="disallowPrerequisiteMoves" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="rules" type="{urn:vim25}ClusterRuleInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="placementType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cloneSpec" type="{urn:vim25}VirtualMachineCloneSpec" minOccurs="0"/>
 *         &lt;element name="cloneName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlacementSpec", propOrder = {
    "priority",
    "vm",
    "configSpec",
    "relocateSpec",
    "hosts",
    "datastores",
    "storagePods",
    "disallowPrerequisiteMoves",
    "rules",
    "key",
    "placementType",
    "cloneSpec",
    "cloneName"
})
public class PlacementSpec
    extends DynamicData
{

    protected VirtualMachineMovePriority priority;
    protected ManagedObjectReference vm;
    protected VirtualMachineConfigSpec configSpec;
    protected VirtualMachineRelocateSpec relocateSpec;
    protected List<ManagedObjectReference> hosts;
    protected List<ManagedObjectReference> datastores;
    protected List<ManagedObjectReference> storagePods;
    protected Boolean disallowPrerequisiteMoves;
    protected List<ClusterRuleInfo> rules;
    protected String key;
    protected String placementType;
    protected VirtualMachineCloneSpec cloneSpec;
    protected String cloneName;

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineMovePriority }
     *     
     */
    public VirtualMachineMovePriority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineMovePriority }
     *     
     */
    public void setPriority(VirtualMachineMovePriority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the vm property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getVm() {
        return vm;
    }

    /**
     * Sets the value of the vm property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setVm(ManagedObjectReference value) {
        this.vm = value;
    }

    /**
     * Gets the value of the configSpec property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineConfigSpec }
     *     
     */
    public VirtualMachineConfigSpec getConfigSpec() {
        return configSpec;
    }

    /**
     * Sets the value of the configSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineConfigSpec }
     *     
     */
    public void setConfigSpec(VirtualMachineConfigSpec value) {
        this.configSpec = value;
    }

    /**
     * Gets the value of the relocateSpec property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineRelocateSpec }
     *     
     */
    public VirtualMachineRelocateSpec getRelocateSpec() {
        return relocateSpec;
    }

    /**
     * Sets the value of the relocateSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineRelocateSpec }
     *     
     */
    public void setRelocateSpec(VirtualMachineRelocateSpec value) {
        this.relocateSpec = value;
    }

    /**
     * Gets the value of the hosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getHosts() {
        if (hosts == null) {
            hosts = new ArrayList<ManagedObjectReference>();
        }
        return this.hosts;
    }

    /**
     * Gets the value of the datastores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datastores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatastores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getDatastores() {
        if (datastores == null) {
            datastores = new ArrayList<ManagedObjectReference>();
        }
        return this.datastores;
    }

    /**
     * Gets the value of the storagePods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storagePods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStoragePods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getStoragePods() {
        if (storagePods == null) {
            storagePods = new ArrayList<ManagedObjectReference>();
        }
        return this.storagePods;
    }

    /**
     * Gets the value of the disallowPrerequisiteMoves property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisallowPrerequisiteMoves() {
        return disallowPrerequisiteMoves;
    }

    /**
     * Sets the value of the disallowPrerequisiteMoves property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisallowPrerequisiteMoves(Boolean value) {
        this.disallowPrerequisiteMoves = value;
    }

    /**
     * Gets the value of the rules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterRuleInfo }
     * 
     * 
     */
    public List<ClusterRuleInfo> getRules() {
        if (rules == null) {
            rules = new ArrayList<ClusterRuleInfo>();
        }
        return this.rules;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the placementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlacementType() {
        return placementType;
    }

    /**
     * Sets the value of the placementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlacementType(String value) {
        this.placementType = value;
    }

    /**
     * Gets the value of the cloneSpec property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineCloneSpec }
     *     
     */
    public VirtualMachineCloneSpec getCloneSpec() {
        return cloneSpec;
    }

    /**
     * Sets the value of the cloneSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineCloneSpec }
     *     
     */
    public void setCloneSpec(VirtualMachineCloneSpec value) {
        this.cloneSpec = value;
    }

    /**
     * Gets the value of the cloneName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloneName() {
        return cloneName;
    }

    /**
     * Sets the value of the cloneName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloneName(String value) {
        this.cloneName = value;
    }

}
