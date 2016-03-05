
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorageDrsPlacementRankVmSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StorageDrsPlacementRankVmSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="vmPlacementSpec" type="{urn:vim25}PlacementSpec"/>
 *         &lt;element name="vmClusters" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageDrsPlacementRankVmSpec", propOrder = {
    "vmPlacementSpec",
    "vmClusters"
})
public class StorageDrsPlacementRankVmSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected PlacementSpec vmPlacementSpec;
    @XmlElement(required = true)
    protected List<ManagedObjectReference> vmClusters;

    /**
     * Gets the value of the vmPlacementSpec property.
     * 
     * @return
     *     possible object is
     *     {@link PlacementSpec }
     *     
     */
    public PlacementSpec getVmPlacementSpec() {
        return vmPlacementSpec;
    }

    /**
     * Sets the value of the vmPlacementSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlacementSpec }
     *     
     */
    public void setVmPlacementSpec(PlacementSpec value) {
        this.vmPlacementSpec = value;
    }

    /**
     * Gets the value of the vmClusters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vmClusters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVmClusters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getVmClusters() {
        if (vmClusters == null) {
            vmClusters = new ArrayList<ManagedObjectReference>();
        }
        return this.vmClusters;
    }

}
