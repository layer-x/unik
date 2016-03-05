
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestRegKeyRecordSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegKeyRecordSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="key" type="{urn:vim25}GuestRegKeySpec"/>
 *         &lt;element name="fault" type="{urn:vim25}LocalizedMethodFault" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegKeyRecordSpec", propOrder = {
    "key",
    "fault"
})
public class GuestRegKeyRecordSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected GuestRegKeySpec key;
    protected LocalizedMethodFault fault;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GuestRegKeySpec }
     *     
     */
    public GuestRegKeySpec getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRegKeySpec }
     *     
     */
    public void setKey(GuestRegKeySpec value) {
        this.key = value;
    }

    /**
     * Gets the value of the fault property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public LocalizedMethodFault getFault() {
        return fault;
    }

    /**
     * Sets the value of the fault property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public void setFault(LocalizedMethodFault value) {
        this.fault = value;
    }

}
