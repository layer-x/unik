
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostPnicNetworkResourceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostPnicNetworkResourceInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="pnicDevice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="availableBandwidthForVMTraffic" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unusedBandwidthForVMTraffic" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="placedVirtualNics" type="{urn:vim25}HostPlacedVirtualNicIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostPnicNetworkResourceInfo", propOrder = {
    "pnicDevice",
    "availableBandwidthForVMTraffic",
    "unusedBandwidthForVMTraffic",
    "placedVirtualNics"
})
public class HostPnicNetworkResourceInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected String pnicDevice;
    protected Long availableBandwidthForVMTraffic;
    protected Long unusedBandwidthForVMTraffic;
    protected List<HostPlacedVirtualNicIdentifier> placedVirtualNics;

    /**
     * Gets the value of the pnicDevice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnicDevice() {
        return pnicDevice;
    }

    /**
     * Sets the value of the pnicDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnicDevice(String value) {
        this.pnicDevice = value;
    }

    /**
     * Gets the value of the availableBandwidthForVMTraffic property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAvailableBandwidthForVMTraffic() {
        return availableBandwidthForVMTraffic;
    }

    /**
     * Sets the value of the availableBandwidthForVMTraffic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAvailableBandwidthForVMTraffic(Long value) {
        this.availableBandwidthForVMTraffic = value;
    }

    /**
     * Gets the value of the unusedBandwidthForVMTraffic property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUnusedBandwidthForVMTraffic() {
        return unusedBandwidthForVMTraffic;
    }

    /**
     * Sets the value of the unusedBandwidthForVMTraffic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUnusedBandwidthForVMTraffic(Long value) {
        this.unusedBandwidthForVMTraffic = value;
    }

    /**
     * Gets the value of the placedVirtualNics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placedVirtualNics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacedVirtualNics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostPlacedVirtualNicIdentifier }
     * 
     * 
     */
    public List<HostPlacedVirtualNicIdentifier> getPlacedVirtualNics() {
        if (placedVirtualNics == null) {
            placedVirtualNics = new ArrayList<HostPlacedVirtualNicIdentifier>();
        }
        return this.placedVirtualNics;
    }

}
