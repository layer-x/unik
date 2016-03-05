
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestRegistryFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestRegistryFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}GuestOperationsFault">
 *       &lt;sequence>
 *         &lt;element name="windowsSystemErrorCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRegistryFault", propOrder = {
    "windowsSystemErrorCode"
})
@XmlSeeAlso({
    GuestRegistryValueFault.class,
    GuestRegistryKeyFault.class
})
public class GuestRegistryFault
    extends GuestOperationsFault
{

    protected long windowsSystemErrorCode;

    /**
     * Gets the value of the windowsSystemErrorCode property.
     * 
     */
    public long getWindowsSystemErrorCode() {
        return windowsSystemErrorCode;
    }

    /**
     * Sets the value of the windowsSystemErrorCode property.
     * 
     */
    public void setWindowsSystemErrorCode(long value) {
        this.windowsSystemErrorCode = value;
    }

}
