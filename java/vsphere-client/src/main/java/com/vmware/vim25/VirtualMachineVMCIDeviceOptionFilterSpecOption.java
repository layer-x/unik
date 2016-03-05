
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineVMCIDeviceOptionFilterSpecOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineVMCIDeviceOptionFilterSpecOption">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="action" type="{urn:vim25}ChoiceOption"/>
 *         &lt;element name="protocol" type="{urn:vim25}ChoiceOption"/>
 *         &lt;element name="direction" type="{urn:vim25}ChoiceOption"/>
 *         &lt;element name="lowerDstPortBoundary" type="{urn:vim25}LongOption"/>
 *         &lt;element name="upperDstPortBoundary" type="{urn:vim25}LongOption"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineVMCIDeviceOptionFilterSpecOption", propOrder = {
    "action",
    "protocol",
    "direction",
    "lowerDstPortBoundary",
    "upperDstPortBoundary"
})
public class VirtualMachineVMCIDeviceOptionFilterSpecOption
    extends DynamicData
{

    @XmlElement(required = true)
    protected ChoiceOption action;
    @XmlElement(required = true)
    protected ChoiceOption protocol;
    @XmlElement(required = true)
    protected ChoiceOption direction;
    @XmlElement(required = true)
    protected LongOption lowerDstPortBoundary;
    @XmlElement(required = true)
    protected LongOption upperDstPortBoundary;

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link ChoiceOption }
     *     
     */
    public ChoiceOption getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoiceOption }
     *     
     */
    public void setAction(ChoiceOption value) {
        this.action = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * @return
     *     possible object is
     *     {@link ChoiceOption }
     *     
     */
    public ChoiceOption getProtocol() {
        return protocol;
    }

    /**
     * Sets the value of the protocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoiceOption }
     *     
     */
    public void setProtocol(ChoiceOption value) {
        this.protocol = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link ChoiceOption }
     *     
     */
    public ChoiceOption getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoiceOption }
     *     
     */
    public void setDirection(ChoiceOption value) {
        this.direction = value;
    }

    /**
     * Gets the value of the lowerDstPortBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link LongOption }
     *     
     */
    public LongOption getLowerDstPortBoundary() {
        return lowerDstPortBoundary;
    }

    /**
     * Sets the value of the lowerDstPortBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link LongOption }
     *     
     */
    public void setLowerDstPortBoundary(LongOption value) {
        this.lowerDstPortBoundary = value;
    }

    /**
     * Gets the value of the upperDstPortBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link LongOption }
     *     
     */
    public LongOption getUpperDstPortBoundary() {
        return upperDstPortBoundary;
    }

    /**
     * Sets the value of the upperDstPortBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link LongOption }
     *     
     */
    public void setUpperDstPortBoundary(LongOption value) {
        this.upperDstPortBoundary = value;
    }

}
