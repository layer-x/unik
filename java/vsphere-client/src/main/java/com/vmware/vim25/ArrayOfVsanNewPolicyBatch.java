
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanNewPolicyBatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanNewPolicyBatch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanNewPolicyBatch" type="{urn:vim25}VsanNewPolicyBatch" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanNewPolicyBatch", propOrder = {
    "vsanNewPolicyBatch"
})
public class ArrayOfVsanNewPolicyBatch {

    @XmlElement(name = "VsanNewPolicyBatch")
    protected List<VsanNewPolicyBatch> vsanNewPolicyBatch;

    /**
     * Gets the value of the vsanNewPolicyBatch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanNewPolicyBatch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanNewPolicyBatch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanNewPolicyBatch }
     * 
     * 
     */
    public List<VsanNewPolicyBatch> getVsanNewPolicyBatch() {
        if (vsanNewPolicyBatch == null) {
            vsanNewPolicyBatch = new ArrayList<VsanNewPolicyBatch>();
        }
        return this.vsanNewPolicyBatch;
    }

}
