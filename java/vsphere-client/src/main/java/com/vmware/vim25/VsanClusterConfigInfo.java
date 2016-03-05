
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanClusterConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanClusterConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="defaultConfig" type="{urn:vim25}VsanClusterConfigInfoHostDefaultInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanClusterConfigInfo", propOrder = {
    "enabled",
    "defaultConfig"
})
public class VsanClusterConfigInfo
    extends DynamicData
{

    protected Boolean enabled;
    protected VsanClusterConfigInfoHostDefaultInfo defaultConfig;

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the defaultConfig property.
     * 
     * @return
     *     possible object is
     *     {@link VsanClusterConfigInfoHostDefaultInfo }
     *     
     */
    public VsanClusterConfigInfoHostDefaultInfo getDefaultConfig() {
        return defaultConfig;
    }

    /**
     * Sets the value of the defaultConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanClusterConfigInfoHostDefaultInfo }
     *     
     */
    public void setDefaultConfig(VsanClusterConfigInfoHostDefaultInfo value) {
        this.defaultConfig = value;
    }

}
