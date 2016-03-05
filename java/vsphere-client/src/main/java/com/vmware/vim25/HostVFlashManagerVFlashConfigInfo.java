
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVFlashManagerVFlashConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVFlashManagerVFlashConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vFlashResourceConfigInfo" type="{urn:vim25}HostVFlashManagerVFlashResourceConfigInfo" minOccurs="0"/>
 *         &lt;element name="vFlashCacheConfigInfo" type="{urn:vim25}HostVFlashManagerVFlashCacheConfigInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVFlashManagerVFlashConfigInfo", propOrder = {
    "vFlashResourceConfigInfo",
    "vFlashCacheConfigInfo"
})
public class HostVFlashManagerVFlashConfigInfo
    extends DynamicData
{

    protected HostVFlashManagerVFlashResourceConfigInfo vFlashResourceConfigInfo;
    protected HostVFlashManagerVFlashCacheConfigInfo vFlashCacheConfigInfo;

    /**
     * Gets the value of the vFlashResourceConfigInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostVFlashManagerVFlashResourceConfigInfo }
     *     
     */
    public HostVFlashManagerVFlashResourceConfigInfo getVFlashResourceConfigInfo() {
        return vFlashResourceConfigInfo;
    }

    /**
     * Sets the value of the vFlashResourceConfigInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVFlashManagerVFlashResourceConfigInfo }
     *     
     */
    public void setVFlashResourceConfigInfo(HostVFlashManagerVFlashResourceConfigInfo value) {
        this.vFlashResourceConfigInfo = value;
    }

    /**
     * Gets the value of the vFlashCacheConfigInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostVFlashManagerVFlashCacheConfigInfo }
     *     
     */
    public HostVFlashManagerVFlashCacheConfigInfo getVFlashCacheConfigInfo() {
        return vFlashCacheConfigInfo;
    }

    /**
     * Sets the value of the vFlashCacheConfigInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVFlashManagerVFlashCacheConfigInfo }
     *     
     */
    public void setVFlashCacheConfigInfo(HostVFlashManagerVFlashCacheConfigInfo value) {
        this.vFlashCacheConfigInfo = value;
    }

}
