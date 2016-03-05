
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualEthernetCardResourceAllocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualEthernetCardResourceAllocation">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="reservation" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="share" type="{urn:vim25}SharesInfo"/>
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualEthernetCardResourceAllocation", propOrder = {
    "reservation",
    "share",
    "limit"
})
public class VirtualEthernetCardResourceAllocation
    extends DynamicData
{

    protected Long reservation;
    @XmlElement(required = true)
    protected SharesInfo share;
    protected Long limit;

    /**
     * Gets the value of the reservation property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservation() {
        return reservation;
    }

    /**
     * Sets the value of the reservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservation(Long value) {
        this.reservation = value;
    }

    /**
     * Gets the value of the share property.
     * 
     * @return
     *     possible object is
     *     {@link SharesInfo }
     *     
     */
    public SharesInfo getShare() {
        return share;
    }

    /**
     * Sets the value of the share property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharesInfo }
     *     
     */
    public void setShare(SharesInfo value) {
        this.share = value;
    }

    /**
     * Gets the value of the limit property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * Sets the value of the limit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLimit(Long value) {
        this.limit = value;
    }

}
