
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostGraphicsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostGraphicsInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostGraphicsInfo" type="{urn:vim25}HostGraphicsInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostGraphicsInfo", propOrder = {
    "hostGraphicsInfo"
})
public class ArrayOfHostGraphicsInfo {

    @XmlElement(name = "HostGraphicsInfo")
    protected List<HostGraphicsInfo> hostGraphicsInfo;

    /**
     * Gets the value of the hostGraphicsInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostGraphicsInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostGraphicsInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostGraphicsInfo }
     * 
     * 
     */
    public List<HostGraphicsInfo> getHostGraphicsInfo() {
        if (hostGraphicsInfo == null) {
            hostGraphicsInfo = new ArrayList<HostGraphicsInfo>();
        }
        return this.hostGraphicsInfo;
    }

}
