
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostVsanDiskInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostVsanDiskInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vsanUuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostVsanDiskInfo", propOrder = {
    "vsanUuid",
    "formatVersion"
})
public class VsanHostVsanDiskInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected String vsanUuid;
    protected int formatVersion;

    /**
     * Gets the value of the vsanUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVsanUuid() {
        return vsanUuid;
    }

    /**
     * Sets the value of the vsanUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVsanUuid(String value) {
        this.vsanUuid = value;
    }

    /**
     * Gets the value of the formatVersion property.
     * 
     */
    public int getFormatVersion() {
        return formatVersion;
    }

    /**
     * Sets the value of the formatVersion property.
     * 
     */
    public void setFormatVersion(int value) {
        this.formatVersion = value;
    }

}
