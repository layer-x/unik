
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetChildRPforHubRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetChildRPforHubRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="vrpId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hub" type="{urn:vim25}ManagedObjectReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetChildRPforHubRequestType", propOrder = {
    "_this",
    "vrpId",
    "hub"
})
public class GetChildRPforHubRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String vrpId;
    @XmlElement(required = true)
    protected ManagedObjectReference hub;

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
     * Gets the value of the hub property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getHub() {
        return hub;
    }

    /**
     * Sets the value of the hub property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setHub(ManagedObjectReference value) {
        this.hub = value;
    }

}
