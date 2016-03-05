
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVirtualMachineMetadataManagerVmMetadataInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVirtualMachineMetadataManagerVmMetadataInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VirtualMachineMetadataManagerVmMetadataInput" type="{urn:vim25}VirtualMachineMetadataManagerVmMetadataInput" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVirtualMachineMetadataManagerVmMetadataInput", propOrder = {
    "virtualMachineMetadataManagerVmMetadataInput"
})
public class ArrayOfVirtualMachineMetadataManagerVmMetadataInput {

    @XmlElement(name = "VirtualMachineMetadataManagerVmMetadataInput")
    protected List<VirtualMachineMetadataManagerVmMetadataInput> virtualMachineMetadataManagerVmMetadataInput;

    /**
     * Gets the value of the virtualMachineMetadataManagerVmMetadataInput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualMachineMetadataManagerVmMetadataInput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualMachineMetadataManagerVmMetadataInput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualMachineMetadataManagerVmMetadataInput }
     * 
     * 
     */
    public List<VirtualMachineMetadataManagerVmMetadataInput> getVirtualMachineMetadataManagerVmMetadataInput() {
        if (virtualMachineMetadataManagerVmMetadataInput == null) {
            virtualMachineMetadataManagerVmMetadataInput = new ArrayList<VirtualMachineMetadataManagerVmMetadataInput>();
        }
        return this.virtualMachineMetadataManagerVmMetadataInput;
    }

}
