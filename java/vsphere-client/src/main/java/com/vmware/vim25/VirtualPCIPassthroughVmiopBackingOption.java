
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualPCIPassthroughVmiopBackingOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualPCIPassthroughVmiopBackingOption">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualPCIPassthroughPluginBackingOption">
 *       &lt;sequence>
 *         &lt;element name="vgpu" type="{urn:vim25}StringOption"/>
 *         &lt;element name="maxInstances" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualPCIPassthroughVmiopBackingOption", propOrder = {
    "vgpu",
    "maxInstances"
})
public class VirtualPCIPassthroughVmiopBackingOption
    extends VirtualPCIPassthroughPluginBackingOption
{

    @XmlElement(required = true)
    protected StringOption vgpu;
    protected int maxInstances;

    /**
     * Gets the value of the vgpu property.
     * 
     * @return
     *     possible object is
     *     {@link StringOption }
     *     
     */
    public StringOption getVgpu() {
        return vgpu;
    }

    /**
     * Sets the value of the vgpu property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringOption }
     *     
     */
    public void setVgpu(StringOption value) {
        this.vgpu = value;
    }

    /**
     * Gets the value of the maxInstances property.
     * 
     */
    public int getMaxInstances() {
        return maxInstances;
    }

    /**
     * Sets the value of the maxInstances property.
     * 
     */
    public void setMaxInstances(int value) {
        this.maxInstances = value;
    }

}
