
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDeviceConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualDeviceConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="operation" type="{urn:vim25}VirtualDeviceConfigSpecOperation" minOccurs="0"/>
 *         &lt;element name="fileOperation" type="{urn:vim25}VirtualDeviceConfigSpecFileOperation" minOccurs="0"/>
 *         &lt;element name="device" type="{urn:vim25}VirtualDevice"/>
 *         &lt;element name="profile" type="{urn:vim25}VirtualMachineProfileSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualDeviceConfigSpec", propOrder = {
    "operation",
    "fileOperation",
    "device",
    "profile"
})
@XmlSeeAlso({
    VirtualDiskConfigSpec.class
})
public class VirtualDeviceConfigSpec
    extends DynamicData
{

    protected VirtualDeviceConfigSpecOperation operation;
    protected VirtualDeviceConfigSpecFileOperation fileOperation;
    @XmlElement(required = true)
    protected VirtualDevice device;
    protected List<VirtualMachineProfileSpec> profile;

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualDeviceConfigSpecOperation }
     *     
     */
    public VirtualDeviceConfigSpecOperation getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualDeviceConfigSpecOperation }
     *     
     */
    public void setOperation(VirtualDeviceConfigSpecOperation value) {
        this.operation = value;
    }

    /**
     * Gets the value of the fileOperation property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualDeviceConfigSpecFileOperation }
     *     
     */
    public VirtualDeviceConfigSpecFileOperation getFileOperation() {
        return fileOperation;
    }

    /**
     * Sets the value of the fileOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualDeviceConfigSpecFileOperation }
     *     
     */
    public void setFileOperation(VirtualDeviceConfigSpecFileOperation value) {
        this.fileOperation = value;
    }

    /**
     * Gets the value of the device property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualDevice }
     *     
     */
    public VirtualDevice getDevice() {
        return device;
    }

    /**
     * Sets the value of the device property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualDevice }
     *     
     */
    public void setDevice(VirtualDevice value) {
        this.device = value;
    }

    /**
     * Gets the value of the profile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualMachineProfileSpec }
     * 
     * 
     */
    public List<VirtualMachineProfileSpec> getProfile() {
        if (profile == null) {
            profile = new ArrayList<VirtualMachineProfileSpec>();
        }
        return this.profile;
    }

}
