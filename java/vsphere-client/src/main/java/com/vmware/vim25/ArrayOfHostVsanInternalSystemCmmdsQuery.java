
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostVsanInternalSystemCmmdsQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostVsanInternalSystemCmmdsQuery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostVsanInternalSystemCmmdsQuery" type="{urn:vim25}HostVsanInternalSystemCmmdsQuery" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostVsanInternalSystemCmmdsQuery", propOrder = {
    "hostVsanInternalSystemCmmdsQuery"
})
public class ArrayOfHostVsanInternalSystemCmmdsQuery {

    @XmlElement(name = "HostVsanInternalSystemCmmdsQuery")
    protected List<HostVsanInternalSystemCmmdsQuery> hostVsanInternalSystemCmmdsQuery;

    /**
     * Gets the value of the hostVsanInternalSystemCmmdsQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostVsanInternalSystemCmmdsQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostVsanInternalSystemCmmdsQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostVsanInternalSystemCmmdsQuery }
     * 
     * 
     */
    public List<HostVsanInternalSystemCmmdsQuery> getHostVsanInternalSystemCmmdsQuery() {
        if (hostVsanInternalSystemCmmdsQuery == null) {
            hostVsanInternalSystemCmmdsQuery = new ArrayList<HostVsanInternalSystemCmmdsQuery>();
        }
        return this.hostVsanInternalSystemCmmdsQuery;
    }

}
