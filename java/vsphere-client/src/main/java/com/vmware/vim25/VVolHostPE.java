
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VVolHostPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VVolHostPE">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="key" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="protocolEndpoint" type="{urn:vim25}HostProtocolEndpoint" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VVolHostPE", propOrder = {
    "key",
    "protocolEndpoint"
})
public class VVolHostPE
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference key;
    @XmlElement(required = true)
    protected List<HostProtocolEndpoint> protocolEndpoint;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setKey(ManagedObjectReference value) {
        this.key = value;
    }

    /**
     * Gets the value of the protocolEndpoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolEndpoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolEndpoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostProtocolEndpoint }
     * 
     * 
     */
    public List<HostProtocolEndpoint> getProtocolEndpoint() {
        if (protocolEndpoint == null) {
            protocolEndpoint = new ArrayList<HostProtocolEndpoint>();
        }
        return this.protocolEndpoint;
    }

}
