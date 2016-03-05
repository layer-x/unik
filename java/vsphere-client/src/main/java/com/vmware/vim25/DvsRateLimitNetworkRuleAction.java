
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsRateLimitNetworkRuleAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsRateLimitNetworkRuleAction">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsNetworkRuleAction">
 *       &lt;sequence>
 *         &lt;element name="packetsPerSecond" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsRateLimitNetworkRuleAction", propOrder = {
    "packetsPerSecond"
})
public class DvsRateLimitNetworkRuleAction
    extends DvsNetworkRuleAction
{

    protected int packetsPerSecond;

    /**
     * Gets the value of the packetsPerSecond property.
     * 
     */
    public int getPacketsPerSecond() {
        return packetsPerSecond;
    }

    /**
     * Sets the value of the packetsPerSecond property.
     * 
     */
    public void setPacketsPerSecond(int value) {
        this.packetsPerSecond = value;
    }

}
