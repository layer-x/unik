
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterInUse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterInUse">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}ResourceInUse">
 *       &lt;sequence>
 *         &lt;element name="disk" type="{urn:vim25}VirtualDiskId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterInUse", propOrder = {
    "disk"
})
public class FilterInUse
    extends ResourceInUse
{

    protected List<VirtualDiskId> disk;

    /**
     * Gets the value of the disk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisk().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualDiskId }
     * 
     * 
     */
    public List<VirtualDiskId> getDisk() {
        if (disk == null) {
            disk = new ArrayList<VirtualDiskId>();
        }
        return this.disk;
    }

}
