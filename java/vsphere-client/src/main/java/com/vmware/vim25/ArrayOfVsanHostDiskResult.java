
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostDiskResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostDiskResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostDiskResult" type="{urn:vim25}VsanHostDiskResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostDiskResult", propOrder = {
    "vsanHostDiskResult"
})
public class ArrayOfVsanHostDiskResult {

    @XmlElement(name = "VsanHostDiskResult")
    protected List<VsanHostDiskResult> vsanHostDiskResult;

    /**
     * Gets the value of the vsanHostDiskResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostDiskResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostDiskResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskResult }
     * 
     * 
     */
    public List<VsanHostDiskResult> getVsanHostDiskResult() {
        if (vsanHostDiskResult == null) {
            vsanHostDiskResult = new ArrayList<VsanHostDiskResult>();
        }
        return this.vsanHostDiskResult;
    }

}
