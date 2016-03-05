
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsMacRewriteNetworkRuleAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsMacRewriteNetworkRuleAction">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsNetworkRuleAction">
 *       &lt;sequence>
 *         &lt;element name="rewriteMac" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsMacRewriteNetworkRuleAction", propOrder = {
    "rewriteMac"
})
public class DvsMacRewriteNetworkRuleAction
    extends DvsNetworkRuleAction
{

    @XmlElement(required = true)
    protected String rewriteMac;

    /**
     * Gets the value of the rewriteMac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRewriteMac() {
        return rewriteMac;
    }

    /**
     * Sets the value of the rewriteMac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRewriteMac(String value) {
        this.rewriteMac = value;
    }

}
