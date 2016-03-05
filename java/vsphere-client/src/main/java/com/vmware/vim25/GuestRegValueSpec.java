
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestRegValueSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegValueSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="name" type="{urn:vim25}GuestRegValueNameSpec"/>
 *         &lt;element name="data" type="{urn:vim25}GuestRegValueDataSpec"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegValueSpec", propOrder = {
    "name",
    "data"
})
public class GuestRegValueSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected GuestRegValueNameSpec name;
    @XmlElement(required = true)
    protected GuestRegValueDataSpec data;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link GuestRegValueNameSpec }
     *     
     */
    public GuestRegValueNameSpec getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRegValueNameSpec }
     *     
     */
    public void setName(GuestRegValueNameSpec value) {
        this.name = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link GuestRegValueDataSpec }
     *     
     */
    public GuestRegValueDataSpec getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRegValueDataSpec }
     *     
     */
    public void setData(GuestRegValueDataSpec value) {
        this.data = value;
    }

}
