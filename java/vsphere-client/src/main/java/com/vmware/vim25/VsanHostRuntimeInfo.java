
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="membershipList" type="{urn:vim25}VsanHostMembershipInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="diskIssues" type="{urn:vim25}VsanHostRuntimeInfoDiskIssue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="accessGenNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostRuntimeInfo", propOrder = {
    "membershipList",
    "diskIssues",
    "accessGenNo"
})
public class VsanHostRuntimeInfo
    extends DynamicData
{

    protected List<VsanHostMembershipInfo> membershipList;
    protected List<VsanHostRuntimeInfoDiskIssue> diskIssues;
    protected Integer accessGenNo;

    /**
     * Gets the value of the membershipList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the membershipList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMembershipList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostMembershipInfo }
     * 
     * 
     */
    public List<VsanHostMembershipInfo> getMembershipList() {
        if (membershipList == null) {
            membershipList = new ArrayList<VsanHostMembershipInfo>();
        }
        return this.membershipList;
    }

    /**
     * Gets the value of the diskIssues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diskIssues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiskIssues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostRuntimeInfoDiskIssue }
     * 
     * 
     */
    public List<VsanHostRuntimeInfoDiskIssue> getDiskIssues() {
        if (diskIssues == null) {
            diskIssues = new ArrayList<VsanHostRuntimeInfoDiskIssue>();
        }
        return this.diskIssues;
    }

    /**
     * Gets the value of the accessGenNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccessGenNo() {
        return accessGenNo;
    }

    /**
     * Sets the value of the accessGenNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccessGenNo(Integer value) {
        this.accessGenNo = value;
    }

}
