
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VMwareDvsIpfixCapability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VMwareDvsIpfixCapability">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="ipfixSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ipv6ForIpfixSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="observationDomainIdSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VMwareDvsIpfixCapability", propOrder = {
    "ipfixSupported",
    "ipv6ForIpfixSupported",
    "observationDomainIdSupported"
})
public class VMwareDvsIpfixCapability
    extends DynamicData
{

    protected Boolean ipfixSupported;
    protected Boolean ipv6ForIpfixSupported;
    protected Boolean observationDomainIdSupported;

    /**
     * Gets the value of the ipfixSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpfixSupported() {
        return ipfixSupported;
    }

    /**
     * Sets the value of the ipfixSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpfixSupported(Boolean value) {
        this.ipfixSupported = value;
    }

    /**
     * Gets the value of the ipv6ForIpfixSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpv6ForIpfixSupported() {
        return ipv6ForIpfixSupported;
    }

    /**
     * Sets the value of the ipv6ForIpfixSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpv6ForIpfixSupported(Boolean value) {
        this.ipv6ForIpfixSupported = value;
    }

    /**
     * Gets the value of the observationDomainIdSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isObservationDomainIdSupported() {
        return observationDomainIdSupported;
    }

    /**
     * Sets the value of the observationDomainIdSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setObservationDomainIdSupported(Boolean value) {
        this.observationDomainIdSupported = value;
    }

}
