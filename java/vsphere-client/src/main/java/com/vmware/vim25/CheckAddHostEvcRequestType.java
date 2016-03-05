
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CheckAddHostEvcRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckAddHostEvcRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="cnxSpec" type="{urn:vim25}HostConnectSpec"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckAddHostEvcRequestType", propOrder = {
    "_this",
    "cnxSpec"
})
public class CheckAddHostEvcRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected HostConnectSpec cnxSpec;

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
     * Gets the value of the cnxSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostConnectSpec }
     *     
     */
    public HostConnectSpec getCnxSpec() {
        return cnxSpec;
    }

    /**
     * Sets the value of the cnxSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostConnectSpec }
     *     
     */
    public void setCnxSpec(HostConnectSpec value) {
        this.cnxSpec = value;
    }

}
