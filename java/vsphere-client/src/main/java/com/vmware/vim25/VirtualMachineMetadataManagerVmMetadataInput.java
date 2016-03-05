
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineMetadataManagerVmMetadataInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineMetadataManagerVmMetadataInput">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vmMetadata" type="{urn:vim25}VirtualMachineMetadataManagerVmMetadata"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineMetadataManagerVmMetadataInput", propOrder = {
    "operation",
    "vmMetadata"
})
public class VirtualMachineMetadataManagerVmMetadataInput
    extends DynamicData
{

    @XmlElement(required = true)
    protected String operation;
    @XmlElement(required = true)
    protected VirtualMachineMetadataManagerVmMetadata vmMetadata;

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the vmMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineMetadataManagerVmMetadata }
     *     
     */
    public VirtualMachineMetadataManagerVmMetadata getVmMetadata() {
        return vmMetadata;
    }

    /**
     * Sets the value of the vmMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineMetadataManagerVmMetadata }
     *     
     */
    public void setVmMetadata(VirtualMachineMetadataManagerVmMetadata value) {
        this.vmMetadata = value;
    }

}
