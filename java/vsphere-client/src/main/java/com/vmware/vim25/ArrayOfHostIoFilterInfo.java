
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostIoFilterInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostIoFilterInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostIoFilterInfo" type="{urn:vim25}HostIoFilterInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostIoFilterInfo", propOrder = {
    "hostIoFilterInfo"
})
public class ArrayOfHostIoFilterInfo {

    @XmlElement(name = "HostIoFilterInfo")
    protected List<HostIoFilterInfo> hostIoFilterInfo;

    /**
     * Gets the value of the hostIoFilterInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostIoFilterInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostIoFilterInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostIoFilterInfo }
     * 
     * 
     */
    public List<HostIoFilterInfo> getHostIoFilterInfo() {
        if (hostIoFilterInfo == null) {
            hostIoFilterInfo = new ArrayList<HostIoFilterInfo>();
        }
        return this.hostIoFilterInfo;
    }

}
