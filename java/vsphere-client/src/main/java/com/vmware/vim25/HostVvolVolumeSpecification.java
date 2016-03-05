
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVvolVolumeSpecification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVvolVolumeSpecification">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="maxSizeInMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="volumeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vasaProviderInfo" type="{urn:vim25}VimVasaProviderInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="storageArray" type="{urn:vim25}VASAStorageArray" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVvolVolumeSpecification", propOrder = {
    "maxSizeInMB",
    "volumeName",
    "vasaProviderInfo",
    "storageArray",
    "uuid"
})
public class HostVvolVolumeSpecification
    extends DynamicData
{

    protected long maxSizeInMB;
    @XmlElement(required = true)
    protected String volumeName;
    protected List<VimVasaProviderInfo> vasaProviderInfo;
    protected List<VASAStorageArray> storageArray;
    @XmlElement(required = true)
    protected String uuid;

    /**
     * Gets the value of the maxSizeInMB property.
     * 
     */
    public long getMaxSizeInMB() {
        return maxSizeInMB;
    }

    /**
     * Sets the value of the maxSizeInMB property.
     * 
     */
    public void setMaxSizeInMB(long value) {
        this.maxSizeInMB = value;
    }

    /**
     * Gets the value of the volumeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolumeName() {
        return volumeName;
    }

    /**
     * Sets the value of the volumeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolumeName(String value) {
        this.volumeName = value;
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

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
