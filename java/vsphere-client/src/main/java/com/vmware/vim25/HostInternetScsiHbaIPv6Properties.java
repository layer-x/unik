
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostInternetScsiHbaIPv6Properties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostInternetScsiHbaIPv6Properties">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="iscsiIpv6Address" type="{urn:vim25}HostInternetScsiHbaIscsiIpv6Address" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ipv6DhcpConfigurationEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ipv6LinkLocalAutoConfigurationEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ipv6RouterAdvertisementConfigurationEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ipv6DefaultGateway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostInternetScsiHbaIPv6Properties", propOrder = {
    "iscsiIpv6Address",
    "ipv6DhcpConfigurationEnabled",
    "ipv6LinkLocalAutoConfigurationEnabled",
    "ipv6RouterAdvertisementConfigurationEnabled",
    "ipv6DefaultGateway"
})
public class HostInternetScsiHbaIPv6Properties
    extends DynamicData
{

    protected List<HostInternetScsiHbaIscsiIpv6Address> iscsiIpv6Address;
    protected Boolean ipv6DhcpConfigurationEnabled;
    protected Boolean ipv6LinkLocalAutoConfigurationEnabled;
    protected Boolean ipv6RouterAdvertisementConfigurationEnabled;
    protected String ipv6DefaultGateway;

    /**
     * Gets the value of the iscsiIpv6Address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iscsiIpv6Address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIscsiIpv6Address().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostInternetScsiHbaIscsiIpv6Address }
     * 
     * 
     */
    public List<HostInternetScsiHbaIscsiIpv6Address> getIscsiIpv6Address() {
        if (iscsiIpv6Address == null) {
            iscsiIpv6Address = new ArrayList<HostInternetScsiHbaIscsiIpv6Address>();
        }
        return this.iscsiIpv6Address;
    }

    /**
     * Gets the value of the ipv6DhcpConfigurationEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpv6DhcpConfigurationEnabled() {
        return ipv6DhcpConfigurationEnabled;
    }

    /**
     * Sets the value of the ipv6DhcpConfigurationEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpv6DhcpConfigurationEnabled(Boolean value) {
        this.ipv6DhcpConfigurationEnabled = value;
    }

    /**
     * Gets the value of the ipv6LinkLocalAutoConfigurationEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpv6LinkLocalAutoConfigurationEnabled() {
        return ipv6LinkLocalAutoConfigurationEnabled;
    }

    /**
     * Sets the value of the ipv6LinkLocalAutoConfigurationEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpv6LinkLocalAutoConfigurationEnabled(Boolean value) {
        this.ipv6LinkLocalAutoConfigurationEnabled = value;
    }

    /**
     * Gets the value of the ipv6RouterAdvertisementConfigurationEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpv6RouterAdvertisementConfigurationEnabled() {
        return ipv6RouterAdvertisementConfigurationEnabled;
    }

    /**
     * Sets the value of the ipv6RouterAdvertisementConfigurationEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpv6RouterAdvertisementConfigurationEnabled(Boolean value) {
        this.ipv6RouterAdvertisementConfigurationEnabled = value;
    }

    /**
     * Gets the value of the ipv6DefaultGateway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpv6DefaultGateway() {
        return ipv6DefaultGateway;
    }

    /**
     * Sets the value of the ipv6DefaultGateway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpv6DefaultGateway(String value) {
        this.ipv6DefaultGateway = value;
    }

}
