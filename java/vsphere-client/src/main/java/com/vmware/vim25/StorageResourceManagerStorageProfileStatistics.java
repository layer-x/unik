
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorageResourceManagerStorageProfileStatistics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StorageResourceManagerStorageProfileStatistics">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="profileId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalSpaceMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="usedSpaceMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageResourceManagerStorageProfileStatistics", propOrder = {
    "profileId",
    "totalSpaceMB",
    "usedSpaceMB"
})
public class StorageResourceManagerStorageProfileStatistics
    extends DynamicData
{

    @XmlElement(required = true)
    protected String profileId;
    protected long totalSpaceMB;
    protected long usedSpaceMB;

    /**
     * Gets the value of the profileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Sets the value of the profileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileId(String value) {
        this.profileId = value;
    }

    /**
     * Gets the value of the totalSpaceMB property.
     * 
     */
    public long getTotalSpaceMB() {
        return totalSpaceMB;
    }

    /**
     * Sets the value of the totalSpaceMB property.
     * 
     */
    public void setTotalSpaceMB(long value) {
        this.totalSpaceMB = value;
    }

    /**
     * Gets the value of the usedSpaceMB property.
     * 
     */
    public long getUsedSpaceMB() {
        return usedSpaceMB;
    }

    /**
     * Sets the value of the usedSpaceMB property.
     * 
     */
    public void setUsedSpaceMB(long value) {
        this.usedSpaceMB = value;
    }

}
