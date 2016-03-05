
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsHostInfrastructureTrafficResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsHostInfrastructureTrafficResource">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allocationInfo" type="{urn:vim25}DvsHostInfrastructureTrafficResourceAllocation"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsHostInfrastructureTrafficResource", propOrder = {
    "key",
    "description",
    "allocationInfo"
})
public class DvsHostInfrastructureTrafficResource
    extends DynamicData
{

    @XmlElement(required = true)
    protected String key;
    protected String description;
    @XmlElement(required = true)
    protected DvsHostInfrastructureTrafficResourceAllocation allocationInfo;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the allocationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DvsHostInfrastructureTrafficResourceAllocation }
     *     
     */
    public DvsHostInfrastructureTrafficResourceAllocation getAllocationInfo() {
        return allocationInfo;
    }

    /**
     * Sets the value of the allocationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DvsHostInfrastructureTrafficResourceAllocation }
     *     
     */
    public void setAllocationInfo(DvsHostInfrastructureTrafficResourceAllocation value) {
        this.allocationInfo = value;
    }

}
