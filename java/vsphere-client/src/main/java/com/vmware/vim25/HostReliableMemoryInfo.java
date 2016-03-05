
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostReliableMemoryInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostReliableMemoryInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="memorySize" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostReliableMemoryInfo", propOrder = {
    "memorySize"
})
public class HostReliableMemoryInfo
    extends DynamicData
{

    protected long memorySize;

    /**
     * Gets the value of the memorySize property.
     * 
     */
    public long getMemorySize() {
        return memorySize;
    }

    /**
     * Sets the value of the memorySize property.
     * 
     */
    public void setMemorySize(long value) {
        this.memorySize = value;
    }

}
