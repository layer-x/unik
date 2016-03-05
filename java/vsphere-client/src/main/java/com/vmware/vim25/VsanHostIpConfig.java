
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostIpConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostIpConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="upstreamIpAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="downstreamIpAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostIpConfig", propOrder = {
    "upstreamIpAddress",
    "downstreamIpAddress"
})
public class VsanHostIpConfig
    extends DynamicData
{

    @XmlElement(required = true)
    protected String upstreamIpAddress;
    @XmlElement(required = true)
    protected String downstreamIpAddress;

    /**
     * Gets the value of the upstreamIpAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpstreamIpAddress() {
        return upstreamIpAddress;
    }

    /**
     * Sets the value of the upstreamIpAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpstreamIpAddress(String value) {
        this.upstreamIpAddress = value;
    }

    /**
     * Gets the value of the downstreamIpAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownstreamIpAddress() {
        return downstreamIpAddress;
    }

    /**
     * Sets the value of the downstreamIpAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownstreamIpAddress(String value) {
        this.downstreamIpAddress = value;
    }

}
