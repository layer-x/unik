
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineSriovInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineSriovInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualMachinePciPassthroughInfo">
 *       &lt;sequence>
 *         &lt;element name="virtualFunction" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pnic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineSriovInfo", propOrder = {
    "virtualFunction",
    "pnic"
})
public class VirtualMachineSriovInfo
    extends VirtualMachinePciPassthroughInfo
{

    protected boolean virtualFunction;
    protected String pnic;

    /**
     * Gets the value of the virtualFunction property.
     * 
     */
    public boolean isVirtualFunction() {
        return virtualFunction;
    }

    /**
     * Sets the value of the virtualFunction property.
     * 
     */
    public void setVirtualFunction(boolean value) {
        this.virtualFunction = value;
    }

    /**
     * Gets the value of the pnic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnic() {
        return pnic;
    }

    /**
     * Sets the value of the pnic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnic(String value) {
        this.pnic = value;
    }

}
