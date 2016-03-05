
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VimVasaProviderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VimVasaProviderInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="provider" type="{urn:vim25}VimVasaProvider"/>
 *         &lt;element name="arrayState" type="{urn:vim25}VimVasaProviderStatePerArray" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VimVasaProviderInfo", propOrder = {
    "provider",
    "arrayState"
})
public class VimVasaProviderInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected VimVasaProvider provider;
    protected List<VimVasaProviderStatePerArray> arrayState;

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link VimVasaProvider }
     *     
     */
    public VimVasaProvider getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link VimVasaProvider }
     *     
     */
    public void setProvider(VimVasaProvider value) {
        this.provider = value;
    }

    /**
     * Gets the value of the arrayState property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrayState property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrayState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VimVasaProviderStatePerArray }
     * 
     * 
     */
    public List<VimVasaProviderStatePerArray> getArrayState() {
        if (arrayState == null) {
            arrayState = new ArrayList<VimVasaProviderStatePerArray>();
        }
        return this.arrayState;
    }

}
