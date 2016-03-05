
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VasaProviderContainerSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VasaProviderContainerSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vasaProviderInfo" type="{urn:vim25}VimVasaProviderInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VasaProviderContainerSpec", propOrder = {
    "vasaProviderInfo",
    "scId",
    "deleted"
})
public class VasaProviderContainerSpec
    extends DynamicData
{

    protected List<VimVasaProviderInfo> vasaProviderInfo;
    @XmlElement(required = true)
    protected String scId;
    protected boolean deleted;

    /**
     * Gets the value of the vasaProviderInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vasaProviderInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVasaProviderInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VimVasaProviderInfo }
     * 
     * 
     */
    public List<VimVasaProviderInfo> getVasaProviderInfo() {
        if (vasaProviderInfo == null) {
            vasaProviderInfo = new ArrayList<VimVasaProviderInfo>();
        }
        return this.vasaProviderInfo;
    }

    /**
     * Gets the value of the scId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScId() {
        return scId;
    }

    /**
     * Sets the value of the scId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScId(String value) {
        this.scId = value;
    }

    /**
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

}
