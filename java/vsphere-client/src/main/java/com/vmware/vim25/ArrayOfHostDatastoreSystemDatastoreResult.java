
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostDatastoreSystemDatastoreResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostDatastoreSystemDatastoreResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostDatastoreSystemDatastoreResult" type="{urn:vim25}HostDatastoreSystemDatastoreResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostDatastoreSystemDatastoreResult", propOrder = {
    "hostDatastoreSystemDatastoreResult"
})
public class ArrayOfHostDatastoreSystemDatastoreResult {

    @XmlElement(name = "HostDatastoreSystemDatastoreResult")
    protected List<HostDatastoreSystemDatastoreResult> hostDatastoreSystemDatastoreResult;

    /**
     * Gets the value of the hostDatastoreSystemDatastoreResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostDatastoreSystemDatastoreResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostDatastoreSystemDatastoreResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostDatastoreSystemDatastoreResult }
     * 
     * 
     */
    public List<HostDatastoreSystemDatastoreResult> getHostDatastoreSystemDatastoreResult() {
        if (hostDatastoreSystemDatastoreResult == null) {
            hostDatastoreSystemDatastoreResult = new ArrayList<HostDatastoreSystemDatastoreResult>();
        }
        return this.hostDatastoreSystemDatastoreResult;
    }

}
