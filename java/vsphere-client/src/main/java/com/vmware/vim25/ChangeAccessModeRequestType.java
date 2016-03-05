
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangeAccessModeRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangeAccessModeRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="principal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isGroup" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="accessMode" type="{urn:vim25}HostAccessMode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeAccessModeRequestType", propOrder = {
    "_this",
    "principal",
    "isGroup",
    "accessMode"
})
public class ChangeAccessModeRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String principal;
    protected boolean isGroup;
    @XmlElement(required = true)
    protected HostAccessMode accessMode;

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
     * Gets the value of the principal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Sets the value of the principal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrincipal(String value) {
        this.principal = value;
    }

    /**
     * Gets the value of the isGroup property.
     * 
     */
    public boolean isIsGroup() {
        return isGroup;
    }

    /**
     * Sets the value of the isGroup property.
     * 
     */
    public void setIsGroup(boolean value) {
        this.isGroup = value;
    }

    /**
     * Gets the value of the accessMode property.
     * 
     * @return
     *     possible object is
     *     {@link HostAccessMode }
     *     
     */
    public HostAccessMode getAccessMode() {
        return accessMode;
    }

    /**
     * Sets the value of the accessMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostAccessMode }
     *     
     */
    public void setAccessMode(HostAccessMode value) {
        this.accessMode = value;
    }

}
