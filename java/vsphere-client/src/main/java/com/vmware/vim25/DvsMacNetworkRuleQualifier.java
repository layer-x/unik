
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsMacNetworkRuleQualifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsMacNetworkRuleQualifier">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsNetworkRuleQualifier">
 *       &lt;sequence>
 *         &lt;element name="sourceAddress" type="{urn:vim25}MacAddress" minOccurs="0"/>
 *         &lt;element name="destinationAddress" type="{urn:vim25}MacAddress" minOccurs="0"/>
 *         &lt;element name="protocol" type="{urn:vim25}IntExpression" minOccurs="0"/>
 *         &lt;element name="vlanId" type="{urn:vim25}IntExpression" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsMacNetworkRuleQualifier", propOrder = {
    "sourceAddress",
    "destinationAddress",
    "protocol",
    "vlanId"
})
public class DvsMacNetworkRuleQualifier
    extends DvsNetworkRuleQualifier
{

    protected MacAddress sourceAddress;
    protected MacAddress destinationAddress;
    protected IntExpression protocol;
    protected IntExpression vlanId;

    /**
     * Gets the value of the sourceAddress property.
     * 
     * @return
     *     possible object is
     *     {@link MacAddress }
     *     
     */
    public MacAddress getSourceAddress() {
        return sourceAddress;
    }

    /**
     * Sets the value of the sourceAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link MacAddress }
     *     
     */
    public void setSourceAddress(MacAddress value) {
        this.sourceAddress = value;
    }

    /**
     * Gets the value of the destinationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link MacAddress }
     *     
     */
    public MacAddress getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets the value of the destinationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link MacAddress }
     *     
     */
    public void setDestinationAddress(MacAddress value) {
        this.destinationAddress = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * @return
     *     possible object is
     *     {@link IntExpression }
     *     
     */
    public IntExpression getProtocol() {
        return protocol;
    }

    /**
     * Sets the value of the protocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntExpression }
     *     
     */
    public void setProtocol(IntExpression value) {
        this.protocol = value;
    }

    /**
     * Gets the value of the vlanId property.
     * 
     * @return
     *     possible object is
     *     {@link IntExpression }
     *     
     */
    public IntExpression getVlanId() {
        return vlanId;
    }

    /**
     * Sets the value of the vlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntExpression }
     *     
     */
    public void setVlanId(IntExpression value) {
        this.vlanId = value;
    }

}
