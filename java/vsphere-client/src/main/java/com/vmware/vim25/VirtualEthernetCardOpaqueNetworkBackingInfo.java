
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualEthernetCardOpaqueNetworkBackingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualEthernetCardOpaqueNetworkBackingInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualDeviceBackingInfo">
 *       &lt;sequence>
 *         &lt;element name="opaqueNetworkId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="opaqueNetworkType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualEthernetCardOpaqueNetworkBackingInfo", propOrder = {
    "opaqueNetworkId",
    "opaqueNetworkType"
})
public class VirtualEthernetCardOpaqueNetworkBackingInfo
    extends VirtualDeviceBackingInfo
{

    @XmlElement(required = true)
    protected String opaqueNetworkId;
    @XmlElement(required = true)
    protected String opaqueNetworkType;

    /**
     * Gets the value of the opaqueNetworkId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpaqueNetworkId() {
        return opaqueNetworkId;
    }

    /**
     * Sets the value of the opaqueNetworkId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpaqueNetworkId(String value) {
        this.opaqueNetworkId = value;
    }

    /**
     * Gets the value of the opaqueNetworkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpaqueNetworkType() {
        return opaqueNetworkType;
    }

    /**
     * Sets the value of the opaqueNetworkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpaqueNetworkType(String value) {
        this.opaqueNetworkType = value;
    }

}
