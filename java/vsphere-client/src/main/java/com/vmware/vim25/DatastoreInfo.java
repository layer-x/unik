
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DatastoreInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatastoreInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeSpace" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxFileSize" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxVirtualDiskCapacity" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="maxMemoryFileSize" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="containerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatastoreInfo", propOrder = {
    "name",
    "url",
    "freeSpace",
    "maxFileSize",
    "maxVirtualDiskCapacity",
    "maxMemoryFileSize",
    "timestamp",
    "containerId"
})
@XmlSeeAlso({
    VmfsDatastoreInfo.class,
    VvolDatastoreInfo.class,
    LocalDatastoreInfo.class,
    NasDatastoreInfo.class
})
public class DatastoreInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String url;
    protected long freeSpace;
    protected long maxFileSize;
    protected Long maxVirtualDiskCapacity;
    protected Long maxMemoryFileSize;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    protected String containerId;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the freeSpace property.
     * 
     */
    public long getFreeSpace() {
        return freeSpace;
    }

    /**
     * Sets the value of the freeSpace property.
     * 
     */
    public void setFreeSpace(long value) {
        this.freeSpace = value;
    }

    /**
     * Gets the value of the maxFileSize property.
     * 
     */
    public long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * Sets the value of the maxFileSize property.
     * 
     */
    public void setMaxFileSize(long value) {
        this.maxFileSize = value;
    }

    /**
     * Gets the value of the maxVirtualDiskCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxVirtualDiskCapacity() {
        return maxVirtualDiskCapacity;
    }

    /**
     * Sets the value of the maxVirtualDiskCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxVirtualDiskCapacity(Long value) {
        this.maxVirtualDiskCapacity = value;
    }

    /**
     * Gets the value of the maxMemoryFileSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxMemoryFileSize() {
        return maxMemoryFileSize;
    }

    /**
     * Sets the value of the maxMemoryFileSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxMemoryFileSize(Long value) {
        this.maxMemoryFileSize = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the containerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerId() {
        return containerId;
    }

    /**
     * Sets the value of the containerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerId(String value) {
        this.containerId = value;
    }

}
