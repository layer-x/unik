
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnSupportedDatastoreForVFlash complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnSupportedDatastoreForVFlash">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}UnsupportedDatastore">
 *       &lt;sequence>
 *         &lt;element name="datastoreName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnSupportedDatastoreForVFlash", propOrder = {
    "datastoreName",
    "type"
})
public class UnSupportedDatastoreForVFlash
    extends UnsupportedDatastore
{

    @XmlElement(required = true)
    protected String datastoreName;
    @XmlElement(required = true)
    protected String type;

    /**
     * Gets the value of the datastoreName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatastoreName() {
        return datastoreName;
    }

    /**
     * Sets the value of the datastoreName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatastoreName(String value) {
        this.datastoreName = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
