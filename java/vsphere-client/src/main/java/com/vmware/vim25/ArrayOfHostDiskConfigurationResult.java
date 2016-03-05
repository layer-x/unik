
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostDiskConfigurationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostDiskConfigurationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostDiskConfigurationResult" type="{urn:vim25}HostDiskConfigurationResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostDiskConfigurationResult", propOrder = {
    "hostDiskConfigurationResult"
})
public class ArrayOfHostDiskConfigurationResult {

    @XmlElement(name = "HostDiskConfigurationResult")
    protected List<HostDiskConfigurationResult> hostDiskConfigurationResult;

    /**
     * Gets the value of the hostDiskConfigurationResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostDiskConfigurationResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostDiskConfigurationResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostDiskConfigurationResult }
     * 
     * 
     */
    public List<HostDiskConfigurationResult> getHostDiskConfigurationResult() {
        if (hostDiskConfigurationResult == null) {
            hostDiskConfigurationResult = new ArrayList<HostDiskConfigurationResult>();
        }
        return this.hostDiskConfigurationResult;
    }

}
