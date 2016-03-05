
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostGetVFlashModuleDefaultConfigRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostGetVFlashModuleDefaultConfigRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="vFlashModule" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostGetVFlashModuleDefaultConfigRequestType", propOrder = {
    "_this",
    "vFlashModule"
})
public class HostGetVFlashModuleDefaultConfigRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String vFlashModule;

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
     * Gets the value of the vFlashModule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFlashModule() {
        return vFlashModule;
    }

    /**
     * Sets the value of the vFlashModule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFlashModule(String value) {
        this.vFlashModule = value;
    }

}
