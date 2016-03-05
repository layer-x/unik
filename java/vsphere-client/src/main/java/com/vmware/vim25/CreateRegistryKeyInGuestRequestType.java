
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRegistryKeyInGuestRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRegistryKeyInGuestRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="vm" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="auth" type="{urn:vim25}GuestAuthentication"/>
 *         &lt;element name="keyName" type="{urn:vim25}GuestRegKeyNameSpec"/>
 *         &lt;element name="isVolatile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="classType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRegistryKeyInGuestRequestType", propOrder = {
    "_this",
    "vm",
    "auth",
    "keyName",
    "isVolatile",
    "classType"
})
public class CreateRegistryKeyInGuestRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected ManagedObjectReference vm;
    @XmlElement(required = true)
    protected GuestAuthentication auth;
    @XmlElement(required = true)
    protected GuestRegKeyNameSpec keyName;
    protected boolean isVolatile;
    protected String classType;

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
     * Gets the value of the auth property.
     * 
     * @return
     *     possible object is
     *     {@link GuestAuthentication }
     *     
     */
    public GuestAuthentication getAuth() {
        return auth;
    }

    /**
     * Sets the value of the auth property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestAuthentication }
     *     
     */
    public void setAuth(GuestAuthentication value) {
        this.auth = value;
    }

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link GuestRegKeyNameSpec }
     *     
     */
    public GuestRegKeyNameSpec getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRegKeyNameSpec }
     *     
     */
    public void setKeyName(GuestRegKeyNameSpec value) {
        this.keyName = value;
    }

    /**
     * Gets the value of the isVolatile property.
     * 
     */
    public boolean isIsVolatile() {
        return isVolatile;
    }

    /**
     * Sets the value of the isVolatile property.
     * 
     */
    public void setIsVolatile(boolean value) {
        this.isVolatile = value;
    }

    /**
     * Gets the value of the classType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassType() {
        return classType;
    }

    /**
     * Sets the value of the classType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassType(String value) {
        this.classType = value;
    }

}
