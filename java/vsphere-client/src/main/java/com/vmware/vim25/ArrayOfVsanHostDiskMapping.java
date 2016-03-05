
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostDiskMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostDiskMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostDiskMapping" type="{urn:vim25}VsanHostDiskMapping" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostDiskMapping", propOrder = {
    "vsanHostDiskMapping"
})
public class ArrayOfVsanHostDiskMapping {

    @XmlElement(name = "VsanHostDiskMapping")
    protected List<VsanHostDiskMapping> vsanHostDiskMapping;

    /**
     * Gets the value of the vsanHostDiskMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostDiskMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostDiskMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskMapping }
     * 
     * 
     */
    public List<VsanHostDiskMapping> getVsanHostDiskMapping() {
        if (vsanHostDiskMapping == null) {
            vsanHostDiskMapping = new ArrayList<VsanHostDiskMapping>();
        }
        return this.vsanHostDiskMapping;
    }

}
