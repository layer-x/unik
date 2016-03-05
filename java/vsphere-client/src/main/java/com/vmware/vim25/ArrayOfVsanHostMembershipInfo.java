
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostMembershipInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostMembershipInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostMembershipInfo" type="{urn:vim25}VsanHostMembershipInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostMembershipInfo", propOrder = {
    "vsanHostMembershipInfo"
})
public class ArrayOfVsanHostMembershipInfo {

    @XmlElement(name = "VsanHostMembershipInfo")
    protected List<VsanHostMembershipInfo> vsanHostMembershipInfo;

    /**
     * Gets the value of the vsanHostMembershipInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostMembershipInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostMembershipInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostMembershipInfo }
     * 
     * 
     */
    public List<VsanHostMembershipInfo> getVsanHostMembershipInfo() {
        if (vsanHostMembershipInfo == null) {
            vsanHostMembershipInfo = new ArrayList<VsanHostMembershipInfo>();
        }
        return this.vsanHostMembershipInfo;
    }

}
