
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGuestAuthSubject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGuestAuthSubject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GuestAuthSubject" type="{urn:vim25}GuestAuthSubject" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGuestAuthSubject", propOrder = {
    "guestAuthSubject"
})
public class ArrayOfGuestAuthSubject {

    @XmlElement(name = "GuestAuthSubject")
    protected List<GuestAuthSubject> guestAuthSubject;

    /**
     * Gets the value of the guestAuthSubject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guestAuthSubject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuestAuthSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GuestAuthSubject }
     * 
     * 
     */
    public List<GuestAuthSubject> getGuestAuthSubject() {
        if (guestAuthSubject == null) {
            guestAuthSubject = new ArrayList<GuestAuthSubject>();
        }
        return this.guestAuthSubject;
    }

}
