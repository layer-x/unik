
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NetStackInstanceProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NetStackInstanceProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}ApplyProfile">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dnsConfig" type="{urn:vim25}NetworkProfileDnsConfigProfile"/>
 *         &lt;element name="ipRouteConfig" type="{urn:vim25}IpRouteProfile"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetStackInstanceProfile", propOrder = {
    "key",
    "dnsConfig",
    "ipRouteConfig"
})
public class NetStackInstanceProfile
    extends ApplyProfile
{

    @XmlElement(required = true)
    protected String key;
    @XmlElement(required = true)
    protected NetworkProfileDnsConfigProfile dnsConfig;
    @XmlElement(required = true)
    protected IpRouteProfile ipRouteConfig;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the dnsConfig property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkProfileDnsConfigProfile }
     *     
     */
    public NetworkProfileDnsConfigProfile getDnsConfig() {
        return dnsConfig;
    }

    /**
     * Sets the value of the dnsConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkProfileDnsConfigProfile }
     *     
     */
    public void setDnsConfig(NetworkProfileDnsConfigProfile value) {
        this.dnsConfig = value;
    }

    /**
     * Gets the value of the ipRouteConfig property.
     * 
     * @return
     *     possible object is
     *     {@link IpRouteProfile }
     *     
     */
    public IpRouteProfile getIpRouteConfig() {
        return ipRouteConfig;
    }

    /**
     * Sets the value of the ipRouteConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link IpRouteProfile }
     *     
     */
    public void setIpRouteConfig(IpRouteProfile value) {
        this.ipRouteConfig = value;
    }

}
