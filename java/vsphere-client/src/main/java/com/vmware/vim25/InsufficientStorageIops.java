
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InsufficientStorageIops complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InsufficientStorageIops">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VimFault">
 *       &lt;sequence>
 *         &lt;element name="unreservedIops" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestedIops" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="datastoreName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InsufficientStorageIops", propOrder = {
    "unreservedIops",
    "requestedIops",
    "datastoreName"
})
public class InsufficientStorageIops
    extends VimFault
{

    protected long unreservedIops;
    protected long requestedIops;
    @XmlElement(required = true)
    protected String datastoreName;

    /**
     * Gets the value of the unreservedIops property.
     * 
     */
    public long getUnreservedIops() {
        return unreservedIops;
    }

    /**
     * Sets the value of the unreservedIops property.
     * 
     */
    public void setUnreservedIops(long value) {
        this.unreservedIops = value;
    }

    /**
     * Gets the value of the requestedIops property.
     * 
     */
    public long getRequestedIops() {
        return requestedIops;
    }

    /**
     * Sets the value of the requestedIops property.
     * 
     */
    public void setRequestedIops(long value) {
        this.requestedIops = value;
    }

    /**
     * Gets the value of the datastoreName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatastoreName() {
        return datastoreName;
    }

    /**
     * Sets the value of the datastoreName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatastoreName(String value) {
        this.datastoreName = value;
    }

}
