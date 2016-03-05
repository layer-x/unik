
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineDefinedProfileSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineDefinedProfileSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualMachineProfileSpec">
 *       &lt;sequence>
 *         &lt;element name="profileId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="profileData" type="{urn:vim25}VirtualMachineProfileRawData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineDefinedProfileSpec", propOrder = {
    "profileId",
    "profileData"
})
public class VirtualMachineDefinedProfileSpec
    extends VirtualMachineProfileSpec
{

    @XmlElement(required = true)
    protected String profileId;
    protected VirtualMachineProfileRawData profileData;

    /**
     * Gets the value of the profileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Sets the value of the profileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileId(String value) {
        this.profileId = value;
    }

    /**
     * Gets the value of the profileData property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineProfileRawData }
     *     
     */
    public VirtualMachineProfileRawData getProfileData() {
        return profileData;
    }

    /**
     * Sets the value of the profileData property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineProfileRawData }
     *     
     */
    public void setProfileData(VirtualMachineProfileRawData value) {
        this.profileData = value;
    }

}
