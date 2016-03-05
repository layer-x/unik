
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmVnicPoolReservationViolationClearEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmVnicPoolReservationViolationClearEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsEvent">
 *       &lt;sequence>
 *         &lt;element name="vmVnicResourcePoolKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vmVnicResourcePoolName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmVnicPoolReservationViolationClearEvent", propOrder = {
    "vmVnicResourcePoolKey",
    "vmVnicResourcePoolName"
})
public class VmVnicPoolReservationViolationClearEvent
    extends DvsEvent
{

    @XmlElement(required = true)
    protected String vmVnicResourcePoolKey;
    protected String vmVnicResourcePoolName;

    /**
     * Gets the value of the vmVnicResourcePoolKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmVnicResourcePoolKey() {
        return vmVnicResourcePoolKey;
    }

    /**
     * Sets the value of the vmVnicResourcePoolKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmVnicResourcePoolKey(String value) {
        this.vmVnicResourcePoolKey = value;
    }

    /**
     * Gets the value of the vmVnicResourcePoolName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmVnicResourcePoolName() {
        return vmVnicResourcePoolName;
    }

    /**
     * Sets the value of the vmVnicResourcePoolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmVnicResourcePoolName(String value) {
        this.vmVnicResourcePoolName = value;
    }

}
