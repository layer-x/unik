
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IpRange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IpRange">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}IpAddress">
 *       &lt;sequence>
 *         &lt;element name="addressPrefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prefixLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IpRange", propOrder = {
    "addressPrefix",
    "prefixLength"
})
public class IpRange
    extends IpAddress
{

    @XmlElement(required = true)
    protected String addressPrefix;
    protected Integer prefixLength;

    /**
     * Gets the value of the addressPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressPrefix() {
        return addressPrefix;
    }

    /**
     * Sets the value of the addressPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressPrefix(String value) {
        this.addressPrefix = value;
    }

    /**
     * Gets the value of the prefixLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrefixLength() {
        return prefixLength;
    }

    /**
     * Sets the value of the prefixLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrefixLength(Integer value) {
        this.prefixLength = value;
    }

}
