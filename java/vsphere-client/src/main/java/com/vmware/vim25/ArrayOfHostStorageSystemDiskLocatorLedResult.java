
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostStorageSystemDiskLocatorLedResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostStorageSystemDiskLocatorLedResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostStorageSystemDiskLocatorLedResult" type="{urn:vim25}HostStorageSystemDiskLocatorLedResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostStorageSystemDiskLocatorLedResult", propOrder = {
    "hostStorageSystemDiskLocatorLedResult"
})
public class ArrayOfHostStorageSystemDiskLocatorLedResult {

    @XmlElement(name = "HostStorageSystemDiskLocatorLedResult")
    protected List<HostStorageSystemDiskLocatorLedResult> hostStorageSystemDiskLocatorLedResult;

    /**
     * Gets the value of the hostStorageSystemDiskLocatorLedResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostStorageSystemDiskLocatorLedResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostStorageSystemDiskLocatorLedResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostStorageSystemDiskLocatorLedResult }
     * 
     * 
     */
    public List<HostStorageSystemDiskLocatorLedResult> getHostStorageSystemDiskLocatorLedResult() {
        if (hostStorageSystemDiskLocatorLedResult == null) {
            hostStorageSystemDiskLocatorLedResult = new ArrayList<HostStorageSystemDiskLocatorLedResult>();
        }
        return this.hostStorageSystemDiskLocatorLedResult;
    }

}
