
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanUpgradeSystemPreflightCheckIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanUpgradeSystemPreflightCheckIssue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanUpgradeSystemPreflightCheckIssue" type="{urn:vim25}VsanUpgradeSystemPreflightCheckIssue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanUpgradeSystemPreflightCheckIssue", propOrder = {
    "vsanUpgradeSystemPreflightCheckIssue"
})
public class ArrayOfVsanUpgradeSystemPreflightCheckIssue {

    @XmlElement(name = "VsanUpgradeSystemPreflightCheckIssue")
    protected List<VsanUpgradeSystemPreflightCheckIssue> vsanUpgradeSystemPreflightCheckIssue;

    /**
     * Gets the value of the vsanUpgradeSystemPreflightCheckIssue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanUpgradeSystemPreflightCheckIssue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanUpgradeSystemPreflightCheckIssue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanUpgradeSystemPreflightCheckIssue }
     * 
     * 
     */
    public List<VsanUpgradeSystemPreflightCheckIssue> getVsanUpgradeSystemPreflightCheckIssue() {
        if (vsanUpgradeSystemPreflightCheckIssue == null) {
            vsanUpgradeSystemPreflightCheckIssue = new ArrayList<VsanUpgradeSystemPreflightCheckIssue>();
        }
        return this.vsanUpgradeSystemPreflightCheckIssue;
    }

}
