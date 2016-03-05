
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVFlashManagerVFlashCacheConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVFlashManagerVFlashCacheConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="defaultVFlashModule" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="swapCacheReservationInGB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVFlashManagerVFlashCacheConfigSpec", propOrder = {
    "defaultVFlashModule",
    "swapCacheReservationInGB"
})
public class HostVFlashManagerVFlashCacheConfigSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected String defaultVFlashModule;
    protected long swapCacheReservationInGB;

    /**
     * Gets the value of the defaultVFlashModule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultVFlashModule() {
        return defaultVFlashModule;
    }

    /**
     * Sets the value of the defaultVFlashModule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultVFlashModule(String value) {
        this.defaultVFlashModule = value;
    }

    /**
     * Gets the value of the swapCacheReservationInGB property.
     * 
     */
    public long getSwapCacheReservationInGB() {
        return swapCacheReservationInGB;
    }

    /**
     * Sets the value of the swapCacheReservationInGB property.
     * 
     */
    public void setSwapCacheReservationInGB(long value) {
        this.swapCacheReservationInGB = value;
    }

}
