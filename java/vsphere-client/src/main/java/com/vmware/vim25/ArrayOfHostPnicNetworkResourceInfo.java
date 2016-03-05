
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostPnicNetworkResourceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostPnicNetworkResourceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostPnicNetworkResourceInfo" type="{urn:vim25}HostPnicNetworkResourceInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostPnicNetworkResourceInfo", propOrder = {
    "hostPnicNetworkResourceInfo"
})
public class ArrayOfHostPnicNetworkResourceInfo {

    @XmlElement(name = "HostPnicNetworkResourceInfo")
    protected List<HostPnicNetworkResourceInfo> hostPnicNetworkResourceInfo;

    /**
     * Gets the value of the hostPnicNetworkResourceInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostPnicNetworkResourceInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostPnicNetworkResourceInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostPnicNetworkResourceInfo }
     * 
     * 
     */
    public List<HostPnicNetworkResourceInfo> getHostPnicNetworkResourceInfo() {
        if (hostPnicNetworkResourceInfo == null) {
            hostPnicNetworkResourceInfo = new ArrayList<HostPnicNetworkResourceInfo>();
        }
        return this.hostPnicNetworkResourceInfo;
    }

}
