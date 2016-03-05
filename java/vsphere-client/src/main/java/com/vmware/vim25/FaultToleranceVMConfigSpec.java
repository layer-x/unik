
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultToleranceVMConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultToleranceVMConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vmConfig" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *         &lt;element name="disks" type="{urn:vim25}FaultToleranceDiskSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultToleranceVMConfigSpec", propOrder = {
    "vmConfig",
    "disks"
})
public class FaultToleranceVMConfigSpec
    extends DynamicData
{

    protected ManagedObjectReference vmConfig;
    protected List<FaultToleranceDiskSpec> disks;

    /**
     * Gets the value of the vmConfig property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getVmConfig() {
        return vmConfig;
    }

    /**
     * Sets the value of the vmConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setVmConfig(ManagedObjectReference value) {
        this.vmConfig = value;
    }

    /**
     * Gets the value of the disks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FaultToleranceDiskSpec }
     * 
     * 
     */
    public List<FaultToleranceDiskSpec> getDisks() {
        if (disks == null) {
            disks = new ArrayList<FaultToleranceDiskSpec>();
        }
        return this.disks;
    }

}
