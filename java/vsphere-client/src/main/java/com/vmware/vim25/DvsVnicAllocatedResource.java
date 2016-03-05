
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsVnicAllocatedResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsVnicAllocatedResource">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vm" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="vnicKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reservation" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsVnicAllocatedResource", propOrder = {
    "vm",
    "vnicKey",
    "reservation"
})
public class DvsVnicAllocatedResource
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference vm;
    @XmlElement(required = true)
    protected String vnicKey;
    protected Long reservation;

    /**
     * Gets the value of the vm property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getVm() {
        return vm;
    }

    /**
     * Sets the value of the vm property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setVm(ManagedObjectReference value) {
        this.vm = value;
    }

    /**
     * Gets the value of the vnicKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnicKey() {
        return vnicKey;
    }

    /**
     * Sets the value of the vnicKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnicKey(String value) {
        this.vnicKey = value;
    }

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

}
