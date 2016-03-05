
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanPolicySatisfiability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanPolicySatisfiability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanPolicySatisfiability" type="{urn:vim25}VsanPolicySatisfiability" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanPolicySatisfiability", propOrder = {
    "vsanPolicySatisfiability"
})
public class ArrayOfVsanPolicySatisfiability {

    @XmlElement(name = "VsanPolicySatisfiability")
    protected List<VsanPolicySatisfiability> vsanPolicySatisfiability;

    /**
     * Gets the value of the vsanPolicySatisfiability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanPolicySatisfiability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanPolicySatisfiability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanPolicySatisfiability }
     * 
     * 
     */
    public List<VsanPolicySatisfiability> getVsanPolicySatisfiability() {
        if (vsanPolicySatisfiability == null) {
            vsanPolicySatisfiability = new ArrayList<VsanPolicySatisfiability>();
        }
        return this.vsanPolicySatisfiability;
    }

}
