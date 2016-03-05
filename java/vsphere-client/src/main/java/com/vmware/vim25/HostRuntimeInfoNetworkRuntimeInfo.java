
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostRuntimeInfoNetworkRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostRuntimeInfoNetworkRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="netStackInstanceRuntimeInfo" type="{urn:vim25}HostRuntimeInfoNetStackInstanceRuntimeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="networkResourceRuntime" type="{urn:vim25}HostNetworkResourceRuntime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostRuntimeInfoNetworkRuntimeInfo", propOrder = {
    "netStackInstanceRuntimeInfo",
    "networkResourceRuntime"
})
public class HostRuntimeInfoNetworkRuntimeInfo
    extends DynamicData
{

    protected List<HostRuntimeInfoNetStackInstanceRuntimeInfo> netStackInstanceRuntimeInfo;
    protected HostNetworkResourceRuntime networkResourceRuntime;

    /**
     * Gets the value of the netStackInstanceRuntimeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the netStackInstanceRuntimeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNetStackInstanceRuntimeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostRuntimeInfoNetStackInstanceRuntimeInfo }
     * 
     * 
     */
    public List<HostRuntimeInfoNetStackInstanceRuntimeInfo> getNetStackInstanceRuntimeInfo() {
        if (netStackInstanceRuntimeInfo == null) {
            netStackInstanceRuntimeInfo = new ArrayList<HostRuntimeInfoNetStackInstanceRuntimeInfo>();
        }
        return this.netStackInstanceRuntimeInfo;
    }

    /**
     * Gets the value of the networkResourceRuntime property.
     * 
     * @return
     *     possible object is
     *     {@link HostNetworkResourceRuntime }
     *     
     */
    public HostNetworkResourceRuntime getNetworkResourceRuntime() {
        return networkResourceRuntime;
    }

    /**
     * Sets the value of the networkResourceRuntime property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostNetworkResourceRuntime }
     *     
     */
    public void setNetworkResourceRuntime(HostNetworkResourceRuntime value) {
        this.networkResourceRuntime = value;
    }

}
