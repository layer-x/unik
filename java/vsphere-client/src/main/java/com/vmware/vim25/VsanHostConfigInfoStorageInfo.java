
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostConfigInfoStorageInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostConfigInfoStorageInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="autoClaimStorage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="diskMapping" type="{urn:vim25}VsanHostDiskMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="diskMapInfo" type="{urn:vim25}VsanHostDiskMapInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="checksumEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostConfigInfoStorageInfo", propOrder = {
    "autoClaimStorage",
    "diskMapping",
    "diskMapInfo",
    "checksumEnabled"
})
public class VsanHostConfigInfoStorageInfo
    extends DynamicData
{

    protected Boolean autoClaimStorage;
    protected List<VsanHostDiskMapping> diskMapping;
    protected List<VsanHostDiskMapInfo> diskMapInfo;
    protected Boolean checksumEnabled;

    /**
     * Gets the value of the autoClaimStorage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoClaimStorage() {
        return autoClaimStorage;
    }

    /**
     * Sets the value of the autoClaimStorage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoClaimStorage(Boolean value) {
        this.autoClaimStorage = value;
    }

    /**
     * Gets the value of the diskMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diskMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiskMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskMapping }
     * 
     * 
     */
    public List<VsanHostDiskMapping> getDiskMapping() {
        if (diskMapping == null) {
            diskMapping = new ArrayList<VsanHostDiskMapping>();
        }
        return this.diskMapping;
    }

    /**
     * Gets the value of the diskMapInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diskMapInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiskMapInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskMapInfo }
     * 
     * 
     */
    public List<VsanHostDiskMapInfo> getDiskMapInfo() {
        if (diskMapInfo == null) {
            diskMapInfo = new ArrayList<VsanHostDiskMapInfo>();
        }
        return this.diskMapInfo;
    }

    /**
     * Gets the value of the checksumEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChecksumEnabled() {
        return checksumEnabled;
    }

    /**
     * Sets the value of the checksumEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChecksumEnabled(Boolean value) {
        this.checksumEnabled = value;
    }

}
