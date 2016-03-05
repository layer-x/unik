
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHostProxySwitchHostLagConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHostProxySwitchHostLagConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HostProxySwitchHostLagConfig" type="{urn:vim25}HostProxySwitchHostLagConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHostProxySwitchHostLagConfig", propOrder = {
    "hostProxySwitchHostLagConfig"
})
public class ArrayOfHostProxySwitchHostLagConfig {

    @XmlElement(name = "HostProxySwitchHostLagConfig")
    protected List<HostProxySwitchHostLagConfig> hostProxySwitchHostLagConfig;

    /**
     * Gets the value of the hostProxySwitchHostLagConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostProxySwitchHostLagConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostProxySwitchHostLagConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostProxySwitchHostLagConfig }
     * 
     * 
     */
    public List<HostProxySwitchHostLagConfig> getHostProxySwitchHostLagConfig() {
        if (hostProxySwitchHostLagConfig == null) {
            hostProxySwitchHostLagConfig = new ArrayList<HostProxySwitchHostLagConfig>();
        }
        return this.hostProxySwitchHostLagConfig;
    }

}
