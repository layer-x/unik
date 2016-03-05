
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostRuntimeInfoDiskIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostRuntimeInfoDiskIssue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostRuntimeInfoDiskIssue" type="{urn:vim25}VsanHostRuntimeInfoDiskIssue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostRuntimeInfoDiskIssue", propOrder = {
    "vsanHostRuntimeInfoDiskIssue"
})
public class ArrayOfVsanHostRuntimeInfoDiskIssue {

    @XmlElement(name = "VsanHostRuntimeInfoDiskIssue")
    protected List<VsanHostRuntimeInfoDiskIssue> vsanHostRuntimeInfoDiskIssue;

    /**
     * Gets the value of the vsanHostRuntimeInfoDiskIssue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostRuntimeInfoDiskIssue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostRuntimeInfoDiskIssue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostRuntimeInfoDiskIssue }
     * 
     * 
     */
    public List<VsanHostRuntimeInfoDiskIssue> getVsanHostRuntimeInfoDiskIssue() {
        if (vsanHostRuntimeInfoDiskIssue == null) {
            vsanHostRuntimeInfoDiskIssue = new ArrayList<VsanHostRuntimeInfoDiskIssue>();
        }
        return this.vsanHostRuntimeInfoDiskIssue;
    }

}
