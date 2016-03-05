
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVASAStorageArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVASAStorageArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VASAStorageArray" type="{urn:vim25}VASAStorageArray" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVASAStorageArray", propOrder = {
    "vasaStorageArray"
})
public class ArrayOfVASAStorageArray {

    @XmlElement(name = "VASAStorageArray")
    protected List<VASAStorageArray> vasaStorageArray;

    /**
     * Gets the value of the vasaStorageArray property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vasaStorageArray property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVASAStorageArray().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VASAStorageArray }
     * 
     * 
     */
    public List<VASAStorageArray> getVASAStorageArray() {
        if (vasaStorageArray == null) {
            vasaStorageArray = new ArrayList<VASAStorageArray>();
        }
        return this.vasaStorageArray;
    }

}
