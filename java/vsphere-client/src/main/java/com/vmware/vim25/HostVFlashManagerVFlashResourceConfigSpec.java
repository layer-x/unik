
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVFlashManagerVFlashResourceConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVFlashManagerVFlashResourceConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vffsUuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVFlashManagerVFlashResourceConfigSpec", propOrder = {
    "vffsUuid"
})
public class HostVFlashManagerVFlashResourceConfigSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected String vffsUuid;

    /**
     * Gets the value of the vffsUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVffsUuid() {
        return vffsUuid;
    }

    /**
     * Sets the value of the vffsUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVffsUuid(String value) {
        this.vffsUuid = value;
    }

}
