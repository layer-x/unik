
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostGatewaySpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostGatewaySpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="gatewayType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gatewayId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trustVerificationToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hostAuthParams" type="{urn:vim25}KeyValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostGatewaySpec", propOrder = {
    "gatewayType",
    "gatewayId",
    "trustVerificationToken",
    "hostAuthParams"
})
public class HostGatewaySpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected String gatewayType;
    protected String gatewayId;
    protected String trustVerificationToken;
    protected List<KeyValue> hostAuthParams;

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

    /**
     * Gets the value of the trustVerificationToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustVerificationToken() {
        return trustVerificationToken;
    }

    /**
     * Sets the value of the trustVerificationToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustVerificationToken(String value) {
        this.trustVerificationToken = value;
    }

    /**
     * Gets the value of the hostAuthParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostAuthParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostAuthParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValue }
     * 
     * 
     */
    public List<KeyValue> getHostAuthParams() {
        if (hostAuthParams == null) {
            hostAuthParams = new ArrayList<KeyValue>();
        }
        return this.hostAuthParams;
    }

}
