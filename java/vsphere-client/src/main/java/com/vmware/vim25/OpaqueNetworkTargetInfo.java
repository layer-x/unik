
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpaqueNetworkTargetInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpaqueNetworkTargetInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualMachineTargetInfo">
 *       &lt;sequence>
 *         &lt;element name="network" type="{urn:vim25}OpaqueNetworkSummary"/>
 *         &lt;element name="networkReservationSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpaqueNetworkTargetInfo", propOrder = {
    "network",
    "networkReservationSupported"
})
public class OpaqueNetworkTargetInfo
    extends VirtualMachineTargetInfo
{

    @XmlElement(required = true)
    protected OpaqueNetworkSummary network;
    protected Boolean networkReservationSupported;

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link OpaqueNetworkSummary }
     *     
     */
    public OpaqueNetworkSummary getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpaqueNetworkSummary }
     *     
     */
    public void setNetwork(OpaqueNetworkSummary value) {
        this.network = value;
    }

    /**
     * Gets the value of the networkReservationSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNetworkReservationSupported() {
        return networkReservationSupported;
    }

    /**
     * Sets the value of the networkReservationSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNetworkReservationSupported(Boolean value) {
        this.networkReservationSupported = value;
    }

}
