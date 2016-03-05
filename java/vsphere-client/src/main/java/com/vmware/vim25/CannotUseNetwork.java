
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CannotUseNetwork complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CannotUseNetwork">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VmConfigFault">
 *       &lt;sequence>
 *         &lt;element name="device" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="backing" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="connected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="network" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CannotUseNetwork", propOrder = {
    "device",
    "backing",
    "connected",
    "reason",
    "network"
})
public class CannotUseNetwork
    extends VmConfigFault
{

    @XmlElement(required = true)
    protected String device;
    @XmlElement(required = true)
    protected String backing;
    protected boolean connected;
    @XmlElement(required = true)
    protected String reason;
    protected ManagedObjectReference network;

    /**
     * Gets the value of the device property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevice() {
        return device;
    }

    /**
     * Sets the value of the device property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevice(String value) {
        this.device = value;
    }

    /**
     * Gets the value of the backing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBacking() {
        return backing;
    }

    /**
     * Sets the value of the backing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBacking(String value) {
        this.backing = value;
    }

    /**
     * Gets the value of the connected property.
     * 
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Sets the value of the connected property.
     * 
     */
    public void setConnected(boolean value) {
        this.connected = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setNetwork(ManagedObjectReference value) {
        this.network = value;
    }

}
