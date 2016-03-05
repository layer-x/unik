
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestRegValueNameSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegValueNameSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="keyName" type="{urn:vim25}GuestRegKeyNameSpec"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegValueNameSpec", propOrder = {
    "keyName",
    "name"
})
public class GuestRegValueNameSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected GuestRegKeyNameSpec keyName;
    @XmlElement(required = true)
    protected String name;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
