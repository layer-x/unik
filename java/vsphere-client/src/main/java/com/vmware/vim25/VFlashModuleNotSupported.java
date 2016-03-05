
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VFlashModuleNotSupported complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VFlashModuleNotSupported">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VmConfigFault">
 *       &lt;sequence>
 *         &lt;element name="vmName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="moduleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VFlashModuleNotSupported", propOrder = {
    "vmName",
    "moduleName",
    "reason",
    "hostName"
})
public class VFlashModuleNotSupported
    extends VmConfigFault
{

    @XmlElement(required = true)
    protected String vmName;
    @XmlElement(required = true)
    protected String moduleName;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String hostName;

    /**
     * Gets the value of the vmName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * Sets the value of the vmName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmName(String value) {
        this.vmName = value;
    }

    /**
     * Gets the value of the moduleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the value of the moduleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleName(String value) {
        this.moduleName = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

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

}
