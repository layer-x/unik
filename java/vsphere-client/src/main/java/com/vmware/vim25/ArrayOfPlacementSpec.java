
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPlacementSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPlacementSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PlacementSpec" type="{urn:vim25}PlacementSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPlacementSpec", propOrder = {
    "placementSpec"
})
public class ArrayOfPlacementSpec {

    @XmlElement(name = "PlacementSpec")
    protected List<PlacementSpec> placementSpec;

    /**
     * Gets the value of the placementSpec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placementSpec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacementSpec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlacementSpec }
     * 
     * 
     */
    public List<PlacementSpec> getPlacementSpec() {
        if (placementSpec == null) {
            placementSpec = new ArrayList<PlacementSpec>();
        }
        return this.placementSpec;
    }

}
