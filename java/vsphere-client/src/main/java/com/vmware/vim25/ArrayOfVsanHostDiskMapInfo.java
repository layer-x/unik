
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostDiskMapInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostDiskMapInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostDiskMapInfo" type="{urn:vim25}VsanHostDiskMapInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostDiskMapInfo", propOrder = {
    "vsanHostDiskMapInfo"
})
public class ArrayOfVsanHostDiskMapInfo {

    @XmlElement(name = "VsanHostDiskMapInfo")
    protected List<VsanHostDiskMapInfo> vsanHostDiskMapInfo;

    /**
     * Gets the value of the vsanHostDiskMapInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostDiskMapInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostDiskMapInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskMapInfo }
     * 
     * 
     */
    public List<VsanHostDiskMapInfo> getVsanHostDiskMapInfo() {
        if (vsanHostDiskMapInfo == null) {
            vsanHostDiskMapInfo = new ArrayList<VsanHostDiskMapInfo>();
        }
        return this.vsanHostDiskMapInfo;
    }

}
