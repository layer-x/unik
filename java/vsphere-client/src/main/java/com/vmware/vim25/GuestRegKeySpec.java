
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GuestRegKeySpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegKeySpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="keyName" type="{urn:vim25}GuestRegKeyNameSpec"/>
 *         &lt;element name="classType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastWritten" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegKeySpec", propOrder = {
    "keyName",
    "classType",
    "lastWritten"
})
public class GuestRegKeySpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected GuestRegKeyNameSpec keyName;
    @XmlElement(required = true)
    protected String classType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastWritten;

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link GuestRegKeyNameSpec }
     *     
     */
    public GuestRegKeyNameSpec getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRegKeyNameSpec }
     *     
     */
    public void setKeyName(GuestRegKeyNameSpec value) {
        this.keyName = value;
    }

    /**
     * Gets the value of the classType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassType() {
        return classType;
    }

    /**
     * Sets the value of the classType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassType(String value) {
        this.classType = value;
    }

    /**
     * Gets the value of the lastWritten property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastWritten() {
        return lastWritten;
    }

    /**
     * Sets the value of the lastWritten property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastWritten(XMLGregorianCalendar value) {
        this.lastWritten = value;
    }

}
