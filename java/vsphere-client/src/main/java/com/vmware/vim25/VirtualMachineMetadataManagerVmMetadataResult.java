
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineMetadataManagerVmMetadataResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineMetadataManagerVmMetadataResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vmMetadata" type="{urn:vim25}VirtualMachineMetadataManagerVmMetadata"/>
 *         &lt;element name="error" type="{urn:vim25}LocalizedMethodFault" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineMetadataManagerVmMetadataResult", propOrder = {
    "vmMetadata",
    "error"
})
public class VirtualMachineMetadataManagerVmMetadataResult
    extends DynamicData
{

    @XmlElement(required = true)
    protected VirtualMachineMetadataManagerVmMetadata vmMetadata;
    protected LocalizedMethodFault error;

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

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public LocalizedMethodFault getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public void setError(LocalizedMethodFault value) {
        this.error = value;
    }

}
