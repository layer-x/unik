
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDVSVmVnicNetworkResourcePool complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDVSVmVnicNetworkResourcePool">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DVSVmVnicNetworkResourcePool" type="{urn:vim25}DVSVmVnicNetworkResourcePool" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDVSVmVnicNetworkResourcePool", propOrder = {
    "dvsVmVnicNetworkResourcePool"
})
public class ArrayOfDVSVmVnicNetworkResourcePool {

    @XmlElement(name = "DVSVmVnicNetworkResourcePool")
    protected List<DVSVmVnicNetworkResourcePool> dvsVmVnicNetworkResourcePool;

    /**
     * Gets the value of the dvsVmVnicNetworkResourcePool property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsVmVnicNetworkResourcePool property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDVSVmVnicNetworkResourcePool().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DVSVmVnicNetworkResourcePool }
     * 
     * 
     */
    public List<DVSVmVnicNetworkResourcePool> getDVSVmVnicNetworkResourcePool() {
        if (dvsVmVnicNetworkResourcePool == null) {
            dvsVmVnicNetworkResourcePool = new ArrayList<DVSVmVnicNetworkResourcePool>();
        }
        return this.dvsVmVnicNetworkResourcePool;
    }

}
