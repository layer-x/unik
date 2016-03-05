
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorageDrsAutomationConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StorageDrsAutomationConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="spaceLoadBalanceAutomationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ioLoadBalanceAutomationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleEnforcementAutomationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyEnforcementAutomationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vmEvacuationAutomationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageDrsAutomationConfig", propOrder = {
    "spaceLoadBalanceAutomationMode",
    "ioLoadBalanceAutomationMode",
    "ruleEnforcementAutomationMode",
    "policyEnforcementAutomationMode",
    "vmEvacuationAutomationMode"
})
public class StorageDrsAutomationConfig
    extends DynamicData
{

    protected String spaceLoadBalanceAutomationMode;
    protected String ioLoadBalanceAutomationMode;
    protected String ruleEnforcementAutomationMode;
    protected String policyEnforcementAutomationMode;
    protected String vmEvacuationAutomationMode;

    /**
     * Gets the value of the spaceLoadBalanceAutomationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpaceLoadBalanceAutomationMode() {
        return spaceLoadBalanceAutomationMode;
    }

    /**
     * Sets the value of the spaceLoadBalanceAutomationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpaceLoadBalanceAutomationMode(String value) {
        this.spaceLoadBalanceAutomationMode = value;
    }

    /**
     * Gets the value of the ioLoadBalanceAutomationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIoLoadBalanceAutomationMode() {
        return ioLoadBalanceAutomationMode;
    }

    /**
     * Sets the value of the ioLoadBalanceAutomationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIoLoadBalanceAutomationMode(String value) {
        this.ioLoadBalanceAutomationMode = value;
    }

    /**
     * Gets the value of the ruleEnforcementAutomationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleEnforcementAutomationMode() {
        return ruleEnforcementAutomationMode;
    }

    /**
     * Sets the value of the ruleEnforcementAutomationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleEnforcementAutomationMode(String value) {
        this.ruleEnforcementAutomationMode = value;
    }

    /**
     * Gets the value of the policyEnforcementAutomationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyEnforcementAutomationMode() {
        return policyEnforcementAutomationMode;
    }

    /**
     * Sets the value of the policyEnforcementAutomationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyEnforcementAutomationMode(String value) {
        this.policyEnforcementAutomationMode = value;
    }

    /**
     * Gets the value of the vmEvacuationAutomationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmEvacuationAutomationMode() {
        return vmEvacuationAutomationMode;
    }

    /**
     * Sets the value of the vmEvacuationAutomationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmEvacuationAutomationMode(String value) {
        this.vmEvacuationAutomationMode = value;
    }

}
