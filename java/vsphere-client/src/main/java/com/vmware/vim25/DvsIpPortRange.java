
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsIpPortRange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsIpPortRange">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsIpPort">
 *       &lt;sequence>
 *         &lt;element name="startPortNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endPortNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsIpPortRange", propOrder = {
    "startPortNumber",
    "endPortNumber"
})
public class DvsIpPortRange
    extends DvsIpPort
{

    protected int startPortNumber;
    protected int endPortNumber;

    /**
     * Gets the value of the startPortNumber property.
     * 
     */
    public int getStartPortNumber() {
        return startPortNumber;
    }

    /**
     * Sets the value of the startPortNumber property.
     * 
     */
    public void setStartPortNumber(int value) {
        this.startPortNumber = value;
    }

    /**
     * Gets the value of the endPortNumber property.
     * 
     */
    public int getEndPortNumber() {
        return endPortNumber;
    }

    /**
     * Sets the value of the endPortNumber property.
     * 
     */
    public void setEndPortNumber(int value) {
        this.endPortNumber = value;
    }

}
