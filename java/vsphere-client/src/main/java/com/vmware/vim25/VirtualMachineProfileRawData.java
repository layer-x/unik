
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineProfileRawData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineProfileRawData">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="extensionKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineProfileRawData", propOrder = {
    "extensionKey",
    "objectData"
})
public class VirtualMachineProfileRawData
    extends DynamicData
{

    @XmlElement(required = true)
    protected String extensionKey;
    protected String objectData;

    /**
     * Gets the value of the extensionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtensionKey() {
        return extensionKey;
    }

    /**
     * Sets the value of the extensionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtensionKey(String value) {
        this.extensionKey = value;
    }

    /**
     * Gets the value of the objectData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectData() {
        return objectData;
    }

    /**
     * Sets the value of the objectData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectData(String value) {
        this.objectData = value;
    }

}
