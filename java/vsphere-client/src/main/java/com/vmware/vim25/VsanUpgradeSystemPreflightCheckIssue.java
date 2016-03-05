
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemPreflightCheckIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemPreflightCheckIssue">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemPreflightCheckIssue", propOrder = {
    "msg"
})
@XmlSeeAlso({
    VsanUpgradeSystemNotEnoughFreeCapacityIssue.class,
    VsanUpgradeSystemHostsDisconnectedIssue.class,
    VsanUpgradeSystemWrongEsxVersionIssue.class,
    VsanUpgradeSystemNetworkPartitionIssue.class,
    VsanUpgradeSystemRogueHostsInClusterIssue.class,
    VsanUpgradeSystemAPIBrokenIssue.class,
    VsanUpgradeSystemMissingHostsInClusterIssue.class,
    VsanUpgradeSystemV2ObjectsPresentDuringDowngradeIssue.class,
    VsanUpgradeSystemAutoClaimEnabledOnHostsIssue.class
})
public class VsanUpgradeSystemPreflightCheckIssue
    extends DynamicData
{

    @XmlElement(required = true)
    protected String msg;

    /**
     * Gets the value of the msg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the value of the msg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

}
