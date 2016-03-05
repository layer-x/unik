
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostAccessControlEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostAccessControlEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostAccessControlEntry" type="{urn:vim25}HostAccessControlEntry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostAccessControlEntry", propOrder = {
    "hostAccessControlEntry"
})
public class ArrayOfHostAccessControlEntry {

    @XmlElement(name = "HostAccessControlEntry")
    protected List<HostAccessControlEntry> hostAccessControlEntry;

    /**
     * Gets the value of the hostAccessControlEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostAccessControlEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostAccessControlEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostAccessControlEntry }
     * 
     * 
     */
    public List<HostAccessControlEntry> getHostAccessControlEntry() {
        if (hostAccessControlEntry == null) {
            hostAccessControlEntry = new ArrayList<HostAccessControlEntry>();
        }
        return this.hostAccessControlEntry;
    }

}
