
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDisk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualDisk">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualDevice">
 *       &lt;sequence>
 *         &lt;element name="capacityInKB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="capacityInBytes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="shares" type="{urn:vim25}SharesInfo" minOccurs="0"/>
 *         &lt;element name="storageIOAllocation" type="{urn:vim25}StorageIOAllocationInfo" minOccurs="0"/>
 *         &lt;element name="diskObjectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vFlashCacheConfigInfo" type="{urn:vim25}VirtualDiskVFlashCacheConfigInfo" minOccurs="0"/>
 *         &lt;element name="iofilter" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualDisk", propOrder = {
    "capacityInKB",
    "capacityInBytes",
    "shares",
    "storageIOAllocation",
    "diskObjectId",
    "vFlashCacheConfigInfo",
    "iofilter"
})
public class VirtualDisk
    extends VirtualDevice
{

    protected long capacityInKB;
    protected Long capacityInBytes;
    protected SharesInfo shares;
    protected StorageIOAllocationInfo storageIOAllocation;
    protected String diskObjectId;
    protected VirtualDiskVFlashCacheConfigInfo vFlashCacheConfigInfo;
    protected List<String> iofilter;

    /**
     * Gets the value of the capacityInKB property.
     * 
     */
    public long getCapacityInKB() {
        return capacityInKB;
    }

    /**
     * Sets the value of the capacityInKB property.
     * 
     */
    public void setCapacityInKB(long value) {
        this.capacityInKB = value;
    }

    /**
     * Gets the value of the capacityInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCapacityInBytes() {
        return capacityInBytes;
    }

    /**
     * Sets the value of the capacityInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCapacityInBytes(Long value) {
        this.capacityInBytes = value;
    }

    /**
     * Gets the value of the shares property.
     * 
     * @return
     *     possible object is
     *     {@link SharesInfo }
     *     
     */
    public SharesInfo getShares() {
        return shares;
    }

    /**
     * Sets the value of the shares property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharesInfo }
     *     
     */
    public void setShares(SharesInfo value) {
        this.shares = value;
    }

    /**
     * Gets the value of the storageIOAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link StorageIOAllocationInfo }
     *     
     */
    public StorageIOAllocationInfo getStorageIOAllocation() {
        return storageIOAllocation;
    }

    /**
     * Sets the value of the storageIOAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StorageIOAllocationInfo }
     *     
     */
    public void setStorageIOAllocation(StorageIOAllocationInfo value) {
        this.storageIOAllocation = value;
    }

    /**
     * Gets the value of the diskObjectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskObjectId() {
        return diskObjectId;
    }

    /**
     * Sets the value of the diskObjectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskObjectId(String value) {
        this.diskObjectId = value;
    }

    /**
     * Gets the value of the vFlashCacheConfigInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualDiskVFlashCacheConfigInfo }
     *     
     */
    public VirtualDiskVFlashCacheConfigInfo getVFlashCacheConfigInfo() {
        return vFlashCacheConfigInfo;
    }

    /**
     * Sets the value of the vFlashCacheConfigInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualDiskVFlashCacheConfigInfo }
     *     
     */
    public void setVFlashCacheConfigInfo(VirtualDiskVFlashCacheConfigInfo value) {
        this.vFlashCacheConfigInfo = value;
    }

    /**
     * Gets the value of the iofilter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iofilter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIofilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIofilter() {
        if (iofilter == null) {
            iofilter = new ArrayList<String>();
        }
        return this.iofilter;
    }

}
