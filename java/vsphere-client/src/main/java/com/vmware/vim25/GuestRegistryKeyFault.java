
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestRegistryKeyFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegistryKeyFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}GuestRegistryFault">
 *       &lt;sequence>
 *         &lt;element name="keyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegistryKeyFault", propOrder = {
    "keyName"
})
@XmlSeeAlso({
    GuestRegistryKeyInvalid.class,
    GuestRegistryKeyHasSubkeys.class,
    GuestRegistryKeyParentVolatile.class,
    GuestRegistryKeyAlreadyExists.class
})
public class GuestRegistryKeyFault
    extends GuestRegistryFault
{

    @XmlElement(required = true)
    protected String keyName;

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyName(String value) {
        this.keyName = value;
    }

}
