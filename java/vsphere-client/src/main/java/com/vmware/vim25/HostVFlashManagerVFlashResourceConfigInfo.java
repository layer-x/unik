
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVFlashManagerVFlashResourceConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVFlashManagerVFlashResourceConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vffs" type="{urn:vim25}HostVffsVolume" minOccurs="0"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVFlashManagerVFlashResourceConfigInfo", propOrder = {
    "vffs",
    "capacity"
})
public class HostVFlashManagerVFlashResourceConfigInfo
    extends DynamicData
{

    protected HostVffsVolume vffs;
    protected long capacity;

    /**
     * Gets the value of the vffs property.
     * 
     * @return
     *     possible object is
     *     {@link HostVffsVolume }
     *     
     */
    public HostVffsVolume getVffs() {
        return vffs;
    }

    /**
     * Sets the value of the vffs property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVffsVolume }
     *     
     */
    public void setVffs(HostVffsVolume value) {
        this.vffs = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(long value) {
        this.capacity = value;
    }

}
