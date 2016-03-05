
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlacementRankSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlacementRankSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="specs" type="{urn:vim25}PlacementSpec" maxOccurs="unbounded"/>
 *         &lt;element name="clusters" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded"/>
 *         &lt;element name="rules" type="{urn:vim25}PlacementAffinityRule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="placementRankByVm" type="{urn:vim25}StorageDrsPlacementRankVmSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlacementRankSpec", propOrder = {
    "specs",
    "clusters",
    "rules",
    "placementRankByVm"
})
public class PlacementRankSpec
    extends DynamicData
{

    @XmlElement(required = true)
    protected List<PlacementSpec> specs;
    @XmlElement(required = true)
    protected List<ManagedObjectReference> clusters;
    protected List<PlacementAffinityRule> rules;
    protected List<StorageDrsPlacementRankVmSpec> placementRankByVm;

    /**
     * Gets the value of the specs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlacementSpec }
     * 
     * 
     */
    public List<PlacementSpec> getSpecs() {
        if (specs == null) {
            specs = new ArrayList<PlacementSpec>();
        }
        return this.specs;
    }

    /**
     * Gets the value of the clusters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getClusters() {
        if (clusters == null) {
            clusters = new ArrayList<ManagedObjectReference>();
        }
        return this.clusters;
    }

    /**
     * Gets the value of the rules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlacementAffinityRule }
     * 
     * 
     */
    public List<PlacementAffinityRule> getRules() {
        if (rules == null) {
            rules = new ArrayList<PlacementAffinityRule>();
        }
        return this.rules;
    }

    /**
     * Gets the value of the placementRankByVm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placementRankByVm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacementRankByVm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StorageDrsPlacementRankVmSpec }
     * 
     * 
     */
    public List<StorageDrsPlacementRankVmSpec> getPlacementRankByVm() {
        if (placementRankByVm == null) {
            placementRankByVm = new ArrayList<StorageDrsPlacementRankVmSpec>();
        }
        return this.placementRankByVm;
    }

}
