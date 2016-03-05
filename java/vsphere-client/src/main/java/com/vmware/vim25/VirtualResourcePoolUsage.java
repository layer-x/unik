
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualResourcePoolUsage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualResourcePoolUsage">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vrpId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cpuReservationMhz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="memReservationMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cpuReservationUsedMhz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="memReservationUsedMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualResourcePoolUsage", propOrder = {
    "vrpId",
    "cpuReservationMhz",
    "memReservationMB",
    "cpuReservationUsedMhz",
    "memReservationUsedMB"
})
public class VirtualResourcePoolUsage
    extends DynamicData
{

    @XmlElement(required = true)
    protected String vrpId;
    protected long cpuReservationMhz;
    protected long memReservationMB;
    protected long cpuReservationUsedMhz;
    protected long memReservationUsedMB;

    /**
     * Gets the value of the vrpId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVrpId() {
        return vrpId;
    }

    /**
     * Sets the value of the vrpId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVrpId(String value) {
        this.vrpId = value;
    }

    /**
     * Gets the value of the cpuReservationMhz property.
     * 
     */
    public long getCpuReservationMhz() {
        return cpuReservationMhz;
    }

    /**
     * Sets the value of the cpuReservationMhz property.
     * 
     */
    public void setCpuReservationMhz(long value) {
        this.cpuReservationMhz = value;
    }

    /**
     * Gets the value of the memReservationMB property.
     * 
     */
    public long getMemReservationMB() {
        return memReservationMB;
    }

    /**
     * Sets the value of the memReservationMB property.
     * 
     */
    public void setMemReservationMB(long value) {
        this.memReservationMB = value;
    }

    /**
     * Gets the value of the cpuReservationUsedMhz property.
     * 
     */
    public long getCpuReservationUsedMhz() {
        return cpuReservationUsedMhz;
    }

    /**
     * Sets the value of the cpuReservationUsedMhz property.
     * 
     */
    public void setCpuReservationUsedMhz(long value) {
        this.cpuReservationUsedMhz = value;
    }

    /**
     * Gets the value of the memReservationUsedMB property.
     * 
     */
    public long getMemReservationUsedMB() {
        return memReservationUsedMB;
    }

    /**
     * Sets the value of the memReservationUsedMB property.
     * 
     */
    public void setMemReservationUsedMB(long value) {
        this.memReservationUsedMB = value;
    }

}
