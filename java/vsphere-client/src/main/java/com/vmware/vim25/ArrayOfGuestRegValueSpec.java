
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGuestRegValueSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGuestRegValueSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GuestRegValueSpec" type="{urn:vim25}GuestRegValueSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGuestRegValueSpec", propOrder = {
    "guestRegValueSpec"
})
public class ArrayOfGuestRegValueSpec {

    @XmlElement(name = "GuestRegValueSpec")
    protected List<GuestRegValueSpec> guestRegValueSpec;

    /**
     * Gets the value of the guestRegValueSpec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guestRegValueSpec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuestRegValueSpec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GuestRegValueSpec }
     * 
     * 
     */
    public List<GuestRegValueSpec> getGuestRegValueSpec() {
        if (guestRegValueSpec == null) {
            guestRegValueSpec = new ArrayList<GuestRegValueSpec>();
        }
        return this.guestRegValueSpec;
    }

}
