
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVirtualMachineMetadataManagerVmMetadataResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVirtualMachineMetadataManagerVmMetadataResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VirtualMachineMetadataManagerVmMetadataResult" type="{urn:vim25}VirtualMachineMetadataManagerVmMetadataResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVirtualMachineMetadataManagerVmMetadataResult", propOrder = {
    "virtualMachineMetadataManagerVmMetadataResult"
})
public class ArrayOfVirtualMachineMetadataManagerVmMetadataResult {

    @XmlElement(name = "VirtualMachineMetadataManagerVmMetadataResult")
    protected List<VirtualMachineMetadataManagerVmMetadataResult> virtualMachineMetadataManagerVmMetadataResult;

    /**
     * Gets the value of the virtualMachineMetadataManagerVmMetadataResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualMachineMetadataManagerVmMetadataResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualMachineMetadataManagerVmMetadataResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualMachineMetadataManagerVmMetadataResult }
     * 
     * 
     */
    public List<VirtualMachineMetadataManagerVmMetadataResult> getVirtualMachineMetadataManagerVmMetadataResult() {
        if (virtualMachineMetadataManagerVmMetadataResult == null) {
            virtualMachineMetadataManagerVmMetadataResult = new ArrayList<VirtualMachineMetadataManagerVmMetadataResult>();
        }
        return this.virtualMachineMetadataManagerVmMetadataResult;
    }

}
