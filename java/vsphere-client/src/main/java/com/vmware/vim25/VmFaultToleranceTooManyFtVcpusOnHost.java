
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmFaultToleranceTooManyFtVcpusOnHost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmFaultToleranceTooManyFtVcpusOnHost">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}InsufficientResourcesFault">
 *       &lt;sequence>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxNumFtVcpus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmFaultToleranceTooManyFtVcpusOnHost", propOrder = {
    "hostName",
    "maxNumFtVcpus"
})
public class VmFaultToleranceTooManyFtVcpusOnHost
    extends InsufficientResourcesFault
{

    protected String hostName;
    protected int maxNumFtVcpus;

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
     * Gets the value of the maxNumFtVcpus property.
     * 
     */
    public int getMaxNumFtVcpus() {
        return maxNumFtVcpus;
    }

    /**
     * Sets the value of the maxNumFtVcpus property.
     * 
     */
    public void setMaxNumFtVcpus(int value) {
        this.maxNumFtVcpus = value;
    }

}
