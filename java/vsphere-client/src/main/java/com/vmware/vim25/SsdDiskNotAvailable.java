
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SsdDiskNotAvailable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SsdDiskNotAvailable">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VimFault">
 *       &lt;sequence>
 *         &lt;element name="devicePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SsdDiskNotAvailable", propOrder = {
    "devicePath"
})
public class SsdDiskNotAvailable
    extends VimFault
{

    @XmlElement(required = true)
    protected String devicePath;

    /**
     * Gets the value of the devicePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevicePath() {
        return devicePath;
    }

    /**
     * Sets the value of the devicePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevicePath(String value) {
        this.devicePath = value;
    }

}
