
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryDisksUsingFilterRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryDisksUsingFilterRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="filterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="compRes" type="{urn:vim25}ManagedObjectReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryDisksUsingFilterRequestType", propOrder = {
    "_this",
    "filterId",
    "compRes"
})
public class QueryDisksUsingFilterRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String filterId;
    @XmlElement(required = true)
    protected ManagedObjectReference compRes;

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
     * Gets the value of the filterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * Sets the value of the filterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterId(String value) {
        this.filterId = value;
    }

    /**
     * Gets the value of the compRes property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getCompRes() {
        return compRes;
    }

    /**
     * Sets the value of the compRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setCompRes(ManagedObjectReference value) {
        this.compRes = value;
    }

}
