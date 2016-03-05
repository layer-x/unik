
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VMwareDvsLagIpfixConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VMwareDvsLagIpfixConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="ipfixEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VMwareDvsLagIpfixConfig", propOrder = {
    "ipfixEnabled"
})
public class VMwareDvsLagIpfixConfig
    extends DynamicData
{

    protected Boolean ipfixEnabled;

    /**
     * Gets the value of the ipfixEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpfixEnabled() {
        return ipfixEnabled;
    }

    /**
     * Sets the value of the ipfixEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpfixEnabled(Boolean value) {
        this.ipfixEnabled = value;
    }

}
