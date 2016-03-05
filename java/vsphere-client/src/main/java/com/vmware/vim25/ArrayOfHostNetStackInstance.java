
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostNetStackInstance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostNetStackInstance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostNetStackInstance" type="{urn:vim25}HostNetStackInstance" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostNetStackInstance", propOrder = {
    "hostNetStackInstance"
})
public class ArrayOfHostNetStackInstance {

    @XmlElement(name = "HostNetStackInstance")
    protected List<HostNetStackInstance> hostNetStackInstance;

    /**
     * Gets the value of the hostNetStackInstance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostNetStackInstance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostNetStackInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostNetStackInstance }
     * 
     * 
     */
    public List<HostNetStackInstance> getHostNetStackInstance() {
        if (hostNetStackInstance == null) {
            hostNetStackInstance = new ArrayList<HostNetStackInstance>();
        }
        return this.hostNetStackInstance;
    }

}
