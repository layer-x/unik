
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostListSummaryGatewaySummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostListSummaryGatewaySummary">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="gatewayType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gatewayId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostListSummaryGatewaySummary", propOrder = {
    "gatewayType",
    "gatewayId"
})
public class HostListSummaryGatewaySummary
    extends DynamicData
{

    @XmlElement(required = true)
    protected String gatewayType;
    @XmlElement(required = true)
    protected String gatewayId;

    /**
     * Gets the value of the gatewayType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayType() {
        return gatewayType;
    }

    /**
     * Sets the value of the gatewayType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayType(String value) {
        this.gatewayType = value;
    }

    /**
     * Gets the value of the gatewayId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayId() {
        return gatewayId;
    }

    /**
     * Sets the value of the gatewayId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayId(String value) {
        this.gatewayId = value;
    }

}
