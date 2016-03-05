
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDiskConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualDiskConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualDeviceConfigSpec">
 *       &lt;sequence>
 *         &lt;element name="diskMoveType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="migrateCache" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualDiskConfigSpec", propOrder = {
    "diskMoveType",
    "migrateCache"
})
public class VirtualDiskConfigSpec
    extends VirtualDeviceConfigSpec
{

    protected String diskMoveType;
    protected Boolean migrateCache;

    /**
     * Gets the value of the diskMoveType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskMoveType() {
        return diskMoveType;
    }

    /**
     * Sets the value of the diskMoveType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskMoveType(String value) {
        this.diskMoveType = value;
    }

    /**
     * Gets the value of the migrateCache property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMigrateCache() {
        return migrateCache;
    }

    /**
     * Sets the value of the migrateCache property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMigrateCache(Boolean value) {
        this.migrateCache = value;
    }

}
