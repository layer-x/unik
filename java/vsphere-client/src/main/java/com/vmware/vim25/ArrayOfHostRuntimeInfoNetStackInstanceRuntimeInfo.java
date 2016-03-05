
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostRuntimeInfoNetStackInstanceRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostRuntimeInfoNetStackInstanceRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostRuntimeInfoNetStackInstanceRuntimeInfo" type="{urn:vim25}HostRuntimeInfoNetStackInstanceRuntimeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostRuntimeInfoNetStackInstanceRuntimeInfo", propOrder = {
    "hostRuntimeInfoNetStackInstanceRuntimeInfo"
})
public class ArrayOfHostRuntimeInfoNetStackInstanceRuntimeInfo {

    @XmlElement(name = "HostRuntimeInfoNetStackInstanceRuntimeInfo")
    protected List<HostRuntimeInfoNetStackInstanceRuntimeInfo> hostRuntimeInfoNetStackInstanceRuntimeInfo;

    /**
     * Gets the value of the hostRuntimeInfoNetStackInstanceRuntimeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostRuntimeInfoNetStackInstanceRuntimeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostRuntimeInfoNetStackInstanceRuntimeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostRuntimeInfoNetStackInstanceRuntimeInfo }
     * 
     * 
     */
    public List<HostRuntimeInfoNetStackInstanceRuntimeInfo> getHostRuntimeInfoNetStackInstanceRuntimeInfo() {
        if (hostRuntimeInfoNetStackInstanceRuntimeInfo == null) {
            hostRuntimeInfoNetStackInstanceRuntimeInfo = new ArrayList<HostRuntimeInfoNetStackInstanceRuntimeInfo>();
        }
        return this.hostRuntimeInfoNetStackInstanceRuntimeInfo;
    }

}
