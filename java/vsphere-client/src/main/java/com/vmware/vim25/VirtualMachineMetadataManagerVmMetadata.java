
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineMetadataManagerVmMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineMetadataManagerVmMetadata">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vmId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="metadata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineMetadataManagerVmMetadata", propOrder = {
    "vmId",
    "metadata"
})
public class VirtualMachineMetadataManagerVmMetadata
    extends DynamicData
{

    @XmlElement(required = true)
    protected String vmId;
    protected String metadata;

    /**
     * Gets the value of the vmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * Sets the value of the vmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmId(String value) {
        this.vmId = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadata(String value) {
        this.metadata = value;
    }

}
