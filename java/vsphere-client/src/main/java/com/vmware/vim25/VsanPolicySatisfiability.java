
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanPolicySatisfiability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanPolicySatisfiability">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isSatisfiable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reason" type="{urn:vim25}LocalizableMessage" minOccurs="0"/>
 *         &lt;element name="cost" type="{urn:vim25}VsanPolicyCost" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanPolicySatisfiability", propOrder = {
    "uuid",
    "isSatisfiable",
    "reason",
    "cost"
})
public class VsanPolicySatisfiability
    extends DynamicData
{

    protected String uuid;
    protected boolean isSatisfiable;
    protected LocalizableMessage reason;
    protected VsanPolicyCost cost;

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the isSatisfiable property.
     * 
     */
    public boolean isIsSatisfiable() {
        return isSatisfiable;
    }

    /**
     * Sets the value of the isSatisfiable property.
     * 
     */
    public void setIsSatisfiable(boolean value) {
        this.isSatisfiable = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizableMessage }
     *     
     */
    public LocalizableMessage getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizableMessage }
     *     
     */
    public void setReason(LocalizableMessage value) {
        this.reason = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link VsanPolicyCost }
     *     
     */
    public VsanPolicyCost getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanPolicyCost }
     *     
     */
    public void setCost(VsanPolicyCost value) {
        this.cost = value;
    }

}
