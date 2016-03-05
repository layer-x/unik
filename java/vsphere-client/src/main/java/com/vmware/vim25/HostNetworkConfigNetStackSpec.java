
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostNetworkConfigNetStackSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostNetworkConfigNetStackSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="netStackInstance" type="{urn:vim25}HostNetStackInstance"/>
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostNetworkConfigNetStackSpec", propOrder = {
    "netStackInstance",
    "operation"
})
public class HostNetworkConfigNetStackSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected HostNetStackInstance netStackInstance;
    protected String operation;

    /**
     * Gets the value of the netStackInstance property.
     * 
     * @return
     *     possible object is
     *     {@link HostNetStackInstance }
     *     
     */
    public HostNetStackInstance getNetStackInstance() {
        return netStackInstance;
    }

    /**
     * Sets the value of the netStackInstance property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostNetStackInstance }
     *     
     */
    public void setNetStackInstance(HostNetStackInstance value) {
        this.netStackInstance = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

}
