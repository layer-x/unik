
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostStorageSystemVmfsVolumeResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostStorageSystemVmfsVolumeResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostStorageSystemVmfsVolumeResult" type="{urn:vim25}HostStorageSystemVmfsVolumeResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostStorageSystemVmfsVolumeResult", propOrder = {
    "hostStorageSystemVmfsVolumeResult"
})
public class ArrayOfHostStorageSystemVmfsVolumeResult {

    @XmlElement(name = "HostStorageSystemVmfsVolumeResult")
    protected List<HostStorageSystemVmfsVolumeResult> hostStorageSystemVmfsVolumeResult;

    /**
     * Gets the value of the hostStorageSystemVmfsVolumeResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostStorageSystemVmfsVolumeResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostStorageSystemVmfsVolumeResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostStorageSystemVmfsVolumeResult }
     * 
     * 
     */
    public List<HostStorageSystemVmfsVolumeResult> getHostStorageSystemVmfsVolumeResult() {
        if (hostStorageSystemVmfsVolumeResult == null) {
            hostStorageSystemVmfsVolumeResult = new ArrayList<HostStorageSystemVmfsVolumeResult>();
        }
        return this.hostStorageSystemVmfsVolumeResult;
    }

}
