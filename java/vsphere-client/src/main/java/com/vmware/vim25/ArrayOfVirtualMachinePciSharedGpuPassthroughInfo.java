
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVirtualMachinePciSharedGpuPassthroughInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVirtualMachinePciSharedGpuPassthroughInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VirtualMachinePciSharedGpuPassthroughInfo" type="{urn:vim25}VirtualMachinePciSharedGpuPassthroughInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVirtualMachinePciSharedGpuPassthroughInfo", propOrder = {
    "virtualMachinePciSharedGpuPassthroughInfo"
})
public class ArrayOfVirtualMachinePciSharedGpuPassthroughInfo {

    @XmlElement(name = "VirtualMachinePciSharedGpuPassthroughInfo")
    protected List<VirtualMachinePciSharedGpuPassthroughInfo> virtualMachinePciSharedGpuPassthroughInfo;

    /**
     * Gets the value of the virtualMachinePciSharedGpuPassthroughInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualMachinePciSharedGpuPassthroughInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualMachinePciSharedGpuPassthroughInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualMachinePciSharedGpuPassthroughInfo }
     * 
     * 
     */
    public List<VirtualMachinePciSharedGpuPassthroughInfo> getVirtualMachinePciSharedGpuPassthroughInfo() {
        if (virtualMachinePciSharedGpuPassthroughInfo == null) {
            virtualMachinePciSharedGpuPassthroughInfo = new ArrayList<VirtualMachinePciSharedGpuPassthroughInfo>();
        }
        return this.virtualMachinePciSharedGpuPassthroughInfo;
    }

}
