
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemNotEnoughFreeCapacityIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemNotEnoughFreeCapacityIssue">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VsanUpgradeSystemPreflightCheckIssue">
 *       &lt;sequence>
 *         &lt;element name="reducedRedundancyUpgradePossible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemNotEnoughFreeCapacityIssue", propOrder = {
    "reducedRedundancyUpgradePossible"
})
public class VsanUpgradeSystemNotEnoughFreeCapacityIssue
    extends VsanUpgradeSystemPreflightCheckIssue
{

    protected boolean reducedRedundancyUpgradePossible;

    /**
     * Gets the value of the reducedRedundancyUpgradePossible property.
     * 
     */
    public boolean isReducedRedundancyUpgradePossible() {
        return reducedRedundancyUpgradePossible;
    }

    /**
     * Sets the value of the reducedRedundancyUpgradePossible property.
     * 
     */
    public void setReducedRedundancyUpgradePossible(boolean value) {
        this.reducedRedundancyUpgradePossible = value;
    }

}
