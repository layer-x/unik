
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DVSRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DVSRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="hostMemberRuntime" type="{urn:vim25}HostMemberRuntimeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resourceRuntimeInfo" type="{urn:vim25}DvsResourceRuntimeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DVSRuntimeInfo", propOrder = {
    "hostMemberRuntime",
    "resourceRuntimeInfo"
})
public class DVSRuntimeInfo
    extends DynamicData
{

    protected List<HostMemberRuntimeInfo> hostMemberRuntime;
    protected DvsResourceRuntimeInfo resourceRuntimeInfo;

    /**
     * Gets the value of the hostMemberRuntime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostMemberRuntime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostMemberRuntime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostMemberRuntimeInfo }
     * 
     * 
     */
    public List<HostMemberRuntimeInfo> getHostMemberRuntime() {
        if (hostMemberRuntime == null) {
            hostMemberRuntime = new ArrayList<HostMemberRuntimeInfo>();
        }
        return this.hostMemberRuntime;
    }

    /**
     * Gets the value of the resourceRuntimeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DvsResourceRuntimeInfo }
     *     
     */
    public DvsResourceRuntimeInfo getResourceRuntimeInfo() {
        return resourceRuntimeInfo;
    }

    /**
     * Sets the value of the resourceRuntimeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DvsResourceRuntimeInfo }
     *     
     */
    public void setResourceRuntimeInfo(DvsResourceRuntimeInfo value) {
        this.resourceRuntimeInfo = value;
    }

}
