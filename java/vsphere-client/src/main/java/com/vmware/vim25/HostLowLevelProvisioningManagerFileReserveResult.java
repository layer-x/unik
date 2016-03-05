
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostLowLevelProvisioningManagerFileReserveResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostLowLevelProvisioningManagerFileReserveResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="baseName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentDir" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reservedName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostLowLevelProvisioningManagerFileReserveResult", propOrder = {
    "baseName",
    "parentDir",
    "reservedName"
})
public class HostLowLevelProvisioningManagerFileReserveResult
    extends DynamicData
{

    @XmlElement(required = true)
    protected String baseName;
    @XmlElement(required = true)
    protected String parentDir;
    @XmlElement(required = true)
    protected String reservedName;

    /**
     * Gets the value of the baseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * Sets the value of the baseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseName(String value) {
        this.baseName = value;
    }

    /**
     * Gets the value of the parentDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentDir() {
        return parentDir;
    }

    /**
     * Sets the value of the parentDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentDir(String value) {
        this.parentDir = value;
    }

    /**
     * Gets the value of the reservedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservedName() {
        return reservedName;
    }

    /**
     * Sets the value of the reservedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservedName(String value) {
        this.reservedName = value;
    }

}
