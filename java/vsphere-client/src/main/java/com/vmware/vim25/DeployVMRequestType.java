
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeployVMRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeployVMRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="vrpId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vm" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="cluster" type="{urn:vim25}ManagedObjectReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeployVMRequestType", propOrder = {
    "_this",
    "vrpId",
    "vm",
    "cluster"
})
public class DeployVMRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String vrpId;
    @XmlElement(required = true)
    protected ManagedObjectReference vm;
    @XmlElement(required = true)
    protected ManagedObjectReference cluster;

    /**
     * Gets the value of the this property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getThis() {
        return _this;
    }

    /**
     * Sets the value of the this property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setThis(ManagedObjectReference value) {
        this._this = value;
    }

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
     * Gets the value of the cluster property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getCluster() {
        return cluster;
    }

    /**
     * Sets the value of the cluster property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setCluster(ManagedObjectReference value) {
        this.cluster = value;
    }

}
