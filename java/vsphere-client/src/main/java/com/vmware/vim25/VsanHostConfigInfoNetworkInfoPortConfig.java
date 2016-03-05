
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostConfigInfoNetworkInfoPortConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostConfigInfoNetworkInfoPortConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="ipConfig" type="{urn:vim25}VsanHostIpConfig" minOccurs="0"/>
 *         &lt;element name="device" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostConfigInfoNetworkInfoPortConfig", propOrder = {
    "ipConfig",
    "device"
})
public class VsanHostConfigInfoNetworkInfoPortConfig
    extends DynamicData
{

    protected VsanHostIpConfig ipConfig;
    @XmlElement(required = true)
    protected String device;

    /**
     * Gets the value of the ipConfig property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostIpConfig }
     *     
     */
    public VsanHostIpConfig getIpConfig() {
        return ipConfig;
    }

    /**
     * Sets the value of the ipConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostIpConfig }
     *     
     */
    public void setIpConfig(VsanHostIpConfig value) {
        this.ipConfig = value;
    }

    /**
     * Gets the value of the device property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevice() {
        return device;
    }

    /**
     * Sets the value of the device property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevice(String value) {
        this.device = value;
    }

}
