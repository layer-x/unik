
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualSriovEthernetCardSriovBackingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualSriovEthernetCardSriovBackingInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualDeviceBackingInfo">
 *       &lt;sequence>
 *         &lt;element name="physicalFunctionBacking" type="{urn:vim25}VirtualPCIPassthroughDeviceBackingInfo" minOccurs="0"/>
 *         &lt;element name="virtualFunctionBacking" type="{urn:vim25}VirtualPCIPassthroughDeviceBackingInfo" minOccurs="0"/>
 *         &lt;element name="virtualFunctionIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualSriovEthernetCardSriovBackingInfo", propOrder = {
    "physicalFunctionBacking",
    "virtualFunctionBacking",
    "virtualFunctionIndex"
})
public class VirtualSriovEthernetCardSriovBackingInfo
    extends VirtualDeviceBackingInfo
{

    protected VirtualPCIPassthroughDeviceBackingInfo physicalFunctionBacking;
    protected VirtualPCIPassthroughDeviceBackingInfo virtualFunctionBacking;
    protected Integer virtualFunctionIndex;

    /**
     * Gets the value of the physicalFunctionBacking property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualPCIPassthroughDeviceBackingInfo }
     *     
     */
    public VirtualPCIPassthroughDeviceBackingInfo getPhysicalFunctionBacking() {
        return physicalFunctionBacking;
    }

    /**
     * Sets the value of the physicalFunctionBacking property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualPCIPassthroughDeviceBackingInfo }
     *     
     */
    public void setPhysicalFunctionBacking(VirtualPCIPassthroughDeviceBackingInfo value) {
        this.physicalFunctionBacking = value;
    }

    /**
     * Gets the value of the virtualFunctionBacking property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualPCIPassthroughDeviceBackingInfo }
     *     
     */
    public VirtualPCIPassthroughDeviceBackingInfo getVirtualFunctionBacking() {
        return virtualFunctionBacking;
    }

    /**
     * Sets the value of the virtualFunctionBacking property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualPCIPassthroughDeviceBackingInfo }
     *     
     */
    public void setVirtualFunctionBacking(VirtualPCIPassthroughDeviceBackingInfo value) {
        this.virtualFunctionBacking = value;
    }

    /**
     * Gets the value of the virtualFunctionIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVirtualFunctionIndex() {
        return virtualFunctionIndex;
    }

    /**
     * Sets the value of the virtualFunctionIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVirtualFunctionIndex(Integer value) {
        this.virtualFunctionIndex = value;
    }

}
