
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultToleranceDiskSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultToleranceDiskSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="disk" type="{urn:vim25}VirtualDevice"/>
 *         &lt;element name="datastore" type="{urn:vim25}ManagedObjectReference"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultToleranceDiskSpec", propOrder = {
    "disk",
    "datastore"
})
public class FaultToleranceDiskSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected VirtualDevice disk;
    @XmlElement(required = true)
    protected ManagedObjectReference datastore;

    /**
     * Gets the value of the disk property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualDevice }
     *     
     */
    public VirtualDevice getDisk() {
        return disk;
    }

    /**
     * Sets the value of the disk property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualDevice }
     *     
     */
    public void setDisk(VirtualDevice value) {
        this.disk = value;
    }

    /**
     * Gets the value of the datastore property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getDatastore() {
        return datastore;
    }

    /**
     * Sets the value of the datastore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setDatastore(ManagedObjectReference value) {
        this.datastore = value;
    }

}
