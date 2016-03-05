
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVVolHostPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVVolHostPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VVolHostPE" type="{urn:vim25}VVolHostPE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVVolHostPE", propOrder = {
    "vVolHostPE"
})
public class ArrayOfVVolHostPE {

    @XmlElement(name = "VVolHostPE")
    protected List<VVolHostPE> vVolHostPE;

    /**
     * Gets the value of the vVolHostPE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vVolHostPE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVVolHostPE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VVolHostPE }
     * 
     * 
     */
    public List<VVolHostPE> getVVolHostPE() {
        if (vVolHostPE == null) {
            vVolHostPE = new ArrayList<VVolHostPE>();
        }
        return this.vVolHostPE;
    }

}
