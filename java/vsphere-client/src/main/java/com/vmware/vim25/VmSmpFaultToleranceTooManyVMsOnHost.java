
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmSmpFaultToleranceTooManyVMsOnHost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmSmpFaultToleranceTooManyVMsOnHost">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}InsufficientResourcesFault">
 *       &lt;sequence>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxNumSmpFtVms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmSmpFaultToleranceTooManyVMsOnHost", propOrder = {
    "hostName",
    "maxNumSmpFtVms"
})
public class VmSmpFaultToleranceTooManyVMsOnHost
    extends InsufficientResourcesFault
{

    protected String hostName;
    protected int maxNumSmpFtVms;

    /**
     * Gets the value of the hostName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the value of the hostName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * Gets the value of the maxNumSmpFtVms property.
     * 
     */
    public int getMaxNumSmpFtVms() {
        return maxNumSmpFtVms;
    }

    /**
     * Sets the value of the maxNumSmpFtVms property.
     * 
     */
    public void setMaxNumSmpFtVms(int value) {
        this.maxNumSmpFtVms = value;
    }

}
