
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostInternetScsiHbaIscsiIpv6Address complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostInternetScsiHbaIscsiIpv6Address">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostInternetScsiHbaIscsiIpv6Address" type="{urn:vim25}HostInternetScsiHbaIscsiIpv6Address" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostInternetScsiHbaIscsiIpv6Address", propOrder = {
    "hostInternetScsiHbaIscsiIpv6Address"
})
public class ArrayOfHostInternetScsiHbaIscsiIpv6Address {

    @XmlElement(name = "HostInternetScsiHbaIscsiIpv6Address")
    protected List<HostInternetScsiHbaIscsiIpv6Address> hostInternetScsiHbaIscsiIpv6Address;

    /**
     * Gets the value of the hostInternetScsiHbaIscsiIpv6Address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostInternetScsiHbaIscsiIpv6Address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostInternetScsiHbaIscsiIpv6Address().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostInternetScsiHbaIscsiIpv6Address }
     * 
     * 
     */
    public List<HostInternetScsiHbaIscsiIpv6Address> getHostInternetScsiHbaIscsiIpv6Address() {
        if (hostInternetScsiHbaIscsiIpv6Address == null) {
            hostInternetScsiHbaIscsiIpv6Address = new ArrayList<HostInternetScsiHbaIscsiIpv6Address>();
        }
        return this.hostInternetScsiHbaIscsiIpv6Address;
    }

}
