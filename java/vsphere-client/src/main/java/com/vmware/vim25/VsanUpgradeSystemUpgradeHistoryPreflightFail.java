
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemUpgradeHistoryPreflightFail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemUpgradeHistoryPreflightFail">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VsanUpgradeSystemUpgradeHistoryItem">
 *       &lt;sequence>
 *         &lt;element name="preflightResult" type="{urn:vim25}VsanUpgradeSystemPreflightCheckResult"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemUpgradeHistoryPreflightFail", propOrder = {
    "preflightResult"
})
public class VsanUpgradeSystemUpgradeHistoryPreflightFail
    extends VsanUpgradeSystemUpgradeHistoryItem
{

    @XmlElement(required = true)
    protected VsanUpgradeSystemPreflightCheckResult preflightResult;

    /**
     * Gets the value of the preflightResult property.
     * 
     * @return
     *     possible object is
     *     {@link VsanUpgradeSystemPreflightCheckResult }
     *     
     */
    public VsanUpgradeSystemPreflightCheckResult getPreflightResult() {
        return preflightResult;
    }

    /**
     * Sets the value of the preflightResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanUpgradeSystemPreflightCheckResult }
     *     
     */
    public void setPreflightResult(VsanUpgradeSystemPreflightCheckResult value) {
        this.preflightResult = value;
    }

}
