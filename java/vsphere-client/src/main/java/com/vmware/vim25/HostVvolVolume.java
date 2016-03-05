
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVvolVolume complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVvolVolume">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostFileSystemVolume">
 *       &lt;sequence>
 *         &lt;element name="scId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostPE" type="{urn:vim25}VVolHostPE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vasaProviderInfo" type="{urn:vim25}VimVasaProviderInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="storageArray" type="{urn:vim25}VASAStorageArray" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVvolVolume", propOrder = {
    "scId",
    "hostPE",
    "vasaProviderInfo",
    "storageArray"
})
public class HostVvolVolume
    extends HostFileSystemVolume
{

    @XmlElement(required = true)
    protected String scId;
    protected List<VVolHostPE> hostPE;
    protected List<VimVasaProviderInfo> vasaProviderInfo;
    protected List<VASAStorageArray> storageArray;

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
     * Gets the value of the hostPE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostPE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostPE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VVolHostPE }
     * 
     * 
     */
    public List<VVolHostPE> getHostPE() {
        if (hostPE == null) {
            hostPE = new ArrayList<VVolHostPE>();
        }
        return this.hostPE;
    }

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
     * Gets the value of the storageArray property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storageArray property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStorageArray().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VASAStorageArray }
     * 
     * 
     */
    public List<VASAStorageArray> getStorageArray() {
        if (storageArray == null) {
            storageArray = new ArrayList<VASAStorageArray>();
        }
        return this.storageArray;
    }

}
