
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsVmVnicResourceAllocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsVmVnicResourceAllocation">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="reservationQuota" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsVmVnicResourceAllocation", propOrder = {
    "reservationQuota"
})
public class DvsVmVnicResourceAllocation
    extends DynamicData
{

    protected Long reservationQuota;

    /**
     * Gets the value of the reservationQuota property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservationQuota() {
        return reservationQuota;
    }

    /**
     * Sets the value of the reservationQuota property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservationQuota(Long value) {
        this.reservationQuota = value;
    }

}
