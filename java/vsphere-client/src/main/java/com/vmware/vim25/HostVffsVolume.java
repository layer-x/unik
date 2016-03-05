
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostVffsVolume complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostVffsVolume">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostFileSystemVolume">
 *       &lt;sequence>
 *         &lt;element name="majorVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extent" type="{urn:vim25}HostScsiDiskPartition" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostVffsVolume", propOrder = {
    "majorVersion",
    "version",
    "uuid",
    "extent"
})
public class HostVffsVolume
    extends HostFileSystemVolume
{

    protected int majorVersion;
    @XmlElement(required = true)
    protected String version;
    @XmlElement(required = true)
    protected String uuid;
    @XmlElement(required = true)
    protected List<HostScsiDiskPartition> extent;

    /**
     * Gets the value of the majorVersion property.
     * 
     */
    public int getMajorVersion() {
        return majorVersion;
    }

    /**
     * Sets the value of the majorVersion property.
     * 
     */
    public void setMajorVersion(int value) {
        this.majorVersion = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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

    /**
     * Gets the value of the extent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostScsiDiskPartition }
     * 
     * 
     */
    public List<HostScsiDiskPartition> getExtent() {
        if (extent == null) {
            extent = new ArrayList<HostScsiDiskPartition>();
        }
        return this.extent;
    }

}
