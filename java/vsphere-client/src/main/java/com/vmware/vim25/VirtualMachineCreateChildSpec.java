
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineCreateChildSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineCreateChildSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="location" type="{urn:vim25}VirtualMachineRelocateSpec" minOccurs="0"/>
 *         &lt;element name="persistent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="configParams" type="{urn:vim25}OptionValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineCreateChildSpec", propOrder = {
    "location",
    "persistent",
    "configParams"
})
public class VirtualMachineCreateChildSpec
    extends DynamicData
{

    protected VirtualMachineRelocateSpec location;
    protected boolean persistent;
    protected List<OptionValue> configParams;

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineRelocateSpec }
     *     
     */
    public VirtualMachineRelocateSpec getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineRelocateSpec }
     *     
     */
    public void setLocation(VirtualMachineRelocateSpec value) {
        this.location = value;
    }

    /**
     * Gets the value of the persistent property.
     * 
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Sets the value of the persistent property.
     * 
     */
    public void setPersistent(boolean value) {
        this.persistent = value;
    }

    /**
     * Gets the value of the configParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OptionValue }
     * 
     * 
     */
    public List<OptionValue> getConfigParams() {
        if (configParams == null) {
            configParams = new ArrayList<OptionValue>();
        }
        return this.configParams;
    }

}
