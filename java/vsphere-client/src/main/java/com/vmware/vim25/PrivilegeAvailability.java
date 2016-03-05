
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrivilegeAvailability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrivilegeAvailability">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="privId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isGranted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrivilegeAvailability", propOrder = {
    "privId",
    "isGranted"
})
public class PrivilegeAvailability
    extends DynamicData
{

    @XmlElement(required = true)
    protected String privId;
    protected boolean isGranted;

    /**
     * Gets the value of the privId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivId() {
        return privId;
    }

    /**
     * Sets the value of the privId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivId(String value) {
        this.privId = value;
    }

    /**
     * Gets the value of the isGranted property.
     * 
     */
    public boolean isIsGranted() {
        return isGranted;
    }

    /**
     * Sets the value of the isGranted property.
     * 
     */
    public void setIsGranted(boolean value) {
        this.isGranted = value;
    }

}
