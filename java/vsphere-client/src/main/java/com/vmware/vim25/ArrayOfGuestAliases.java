
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGuestAliases complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGuestAliases">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GuestAliases" type="{urn:vim25}GuestAliases" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGuestAliases", propOrder = {
    "guestAliases"
})
public class ArrayOfGuestAliases {

    @XmlElement(name = "GuestAliases")
    protected List<GuestAliases> guestAliases;

    /**
     * Gets the value of the guestAliases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guestAliases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuestAliases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GuestAliases }
     * 
     * 
     */
    public List<GuestAliases> getGuestAliases() {
        if (guestAliases == null) {
            guestAliases = new ArrayList<GuestAliases>();
        }
        return this.guestAliases;
    }

}
