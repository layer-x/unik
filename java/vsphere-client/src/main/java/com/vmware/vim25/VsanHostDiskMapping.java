
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostDiskMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostDiskMapping">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="ssd" type="{urn:vim25}HostScsiDisk"/>
 *         &lt;element name="nonSsd" type="{urn:vim25}HostScsiDisk" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostDiskMapping", propOrder = {
    "ssd",
    "nonSsd"
})
public class VsanHostDiskMapping
    extends DynamicData
{

    @XmlElement(required = true)
    protected HostScsiDisk ssd;
    @XmlElement(required = true)
    protected List<HostScsiDisk> nonSsd;

    /**
     * Gets the value of the ssd property.
     * 
     * @return
     *     possible object is
     *     {@link HostScsiDisk }
     *     
     */
    public HostScsiDisk getSsd() {
        return ssd;
    }

    /**
     * Sets the value of the ssd property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostScsiDisk }
     *     
     */
    public void setSsd(HostScsiDisk value) {
        this.ssd = value;
    }

    /**
     * Gets the value of the nonSsd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonSsd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonSsd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostScsiDisk }
     * 
     * 
     */
    public List<HostScsiDisk> getNonSsd() {
        if (nonSsd == null) {
            nonSsd = new ArrayList<HostScsiDisk>();
        }
        return this.nonSsd;
    }

}
