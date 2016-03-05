
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostDecommissionMode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostDecommissionMode">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="objectAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostDecommissionMode", propOrder = {
    "objectAction"
})
public class VsanHostDecommissionMode
    extends DynamicData
{

    @XmlElement(required = true)
    protected String objectAction;

    /**
     * Gets the value of the objectAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectAction() {
        return objectAction;
    }

    /**
     * Sets the value of the objectAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectAction(String value) {
        this.objectAction = value;
    }

}
