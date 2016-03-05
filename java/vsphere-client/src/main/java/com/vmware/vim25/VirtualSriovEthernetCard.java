
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualSriovEthernetCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualSriovEthernetCard">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualEthernetCard">
 *       &lt;sequence>
 *         &lt;element name="allowGuestOSMtuChange" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sriovBacking" type="{urn:vim25}VirtualSriovEthernetCardSriovBackingInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualSriovEthernetCard", propOrder = {
    "allowGuestOSMtuChange",
    "sriovBacking"
})
public class VirtualSriovEthernetCard
    extends VirtualEthernetCard
{

    protected Boolean allowGuestOSMtuChange;
    protected VirtualSriovEthernetCardSriovBackingInfo sriovBacking;

    /**
     * Gets the value of the allowGuestOSMtuChange property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowGuestOSMtuChange() {
        return allowGuestOSMtuChange;
    }

    /**
     * Sets the value of the allowGuestOSMtuChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowGuestOSMtuChange(Boolean value) {
        this.allowGuestOSMtuChange = value;
    }

    /**
     * Gets the value of the sriovBacking property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualSriovEthernetCardSriovBackingInfo }
     *     
     */
    public VirtualSriovEthernetCardSriovBackingInfo getSriovBacking() {
        return sriovBacking;
    }

    /**
     * Sets the value of the sriovBacking property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualSriovEthernetCardSriovBackingInfo }
     *     
     */
    public void setSriovBacking(VirtualSriovEthernetCardSriovBackingInfo value) {
        this.sriovBacking = value;
    }

}
