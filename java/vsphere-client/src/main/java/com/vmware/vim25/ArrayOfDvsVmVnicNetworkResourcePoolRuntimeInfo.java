
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDvsVmVnicNetworkResourcePoolRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDvsVmVnicNetworkResourcePoolRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DvsVmVnicNetworkResourcePoolRuntimeInfo" type="{urn:vim25}DvsVmVnicNetworkResourcePoolRuntimeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDvsVmVnicNetworkResourcePoolRuntimeInfo", propOrder = {
    "dvsVmVnicNetworkResourcePoolRuntimeInfo"
})
public class ArrayOfDvsVmVnicNetworkResourcePoolRuntimeInfo {

    @XmlElement(name = "DvsVmVnicNetworkResourcePoolRuntimeInfo")
    protected List<DvsVmVnicNetworkResourcePoolRuntimeInfo> dvsVmVnicNetworkResourcePoolRuntimeInfo;

    /**
     * Gets the value of the dvsVmVnicNetworkResourcePoolRuntimeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsVmVnicNetworkResourcePoolRuntimeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsVmVnicNetworkResourcePoolRuntimeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DvsVmVnicNetworkResourcePoolRuntimeInfo }
     * 
     * 
     */
    public List<DvsVmVnicNetworkResourcePoolRuntimeInfo> getDvsVmVnicNetworkResourcePoolRuntimeInfo() {
        if (dvsVmVnicNetworkResourcePoolRuntimeInfo == null) {
            dvsVmVnicNetworkResourcePoolRuntimeInfo = new ArrayList<DvsVmVnicNetworkResourcePoolRuntimeInfo>();
        }
        return this.dvsVmVnicNetworkResourcePoolRuntimeInfo;
    }

}
