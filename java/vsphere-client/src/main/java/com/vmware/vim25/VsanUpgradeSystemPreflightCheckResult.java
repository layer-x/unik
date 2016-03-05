
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemPreflightCheckResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemPreflightCheckResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="issues" type="{urn:vim25}VsanUpgradeSystemPreflightCheckIssue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="diskMappingToRestore" type="{urn:vim25}VsanHostDiskMapping" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemPreflightCheckResult", propOrder = {
    "issues",
    "diskMappingToRestore"
})
public class VsanUpgradeSystemPreflightCheckResult
    extends DynamicData
{

    protected List<VsanUpgradeSystemPreflightCheckIssue> issues;
    protected VsanHostDiskMapping diskMappingToRestore;

    /**
     * Gets the value of the issues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the issues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIssues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanUpgradeSystemPreflightCheckIssue }
     * 
     * 
     */
    public List<VsanUpgradeSystemPreflightCheckIssue> getIssues() {
        if (issues == null) {
            issues = new ArrayList<VsanUpgradeSystemPreflightCheckIssue>();
        }
        return this.issues;
    }

    /**
     * Gets the value of the diskMappingToRestore property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public VsanHostDiskMapping getDiskMappingToRestore() {
        return diskMappingToRestore;
    }

    /**
     * Sets the value of the diskMappingToRestore property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public void setDiskMappingToRestore(VsanHostDiskMapping value) {
        this.diskMappingToRestore = value;
    }

}
