
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProxySwitchHostLagConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostProxySwitchHostLagConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="lagKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uplinkPort" type="{urn:vim25}KeyValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostProxySwitchHostLagConfig", propOrder = {
    "lagKey",
    "lagName",
    "uplinkPort"
})
public class HostProxySwitchHostLagConfig
    extends DynamicData
{

    @XmlElement(required = true)
    protected String lagKey;
    protected String lagName;
    protected List<KeyValue> uplinkPort;

    /**
     * Gets the value of the lagKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLagKey() {
        return lagKey;
    }

    /**
     * Sets the value of the lagKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLagKey(String value) {
        this.lagKey = value;
    }

    /**
     * Gets the value of the lagName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLagName() {
        return lagName;
    }

    /**
     * Sets the value of the lagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLagName(String value) {
        this.lagName = value;
    }

    /**
     * Gets the value of the uplinkPort property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uplinkPort property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUplinkPort().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValue }
     * 
     * 
     */
    public List<KeyValue> getUplinkPort() {
        if (uplinkPort == null) {
            uplinkPort = new ArrayList<KeyValue>();
        }
        return this.uplinkPort;
    }

}
