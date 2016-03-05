
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsFilterConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsFilterConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}InheritablePolicy">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="slotNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameters" type="{urn:vim25}DvsFilterParameter" minOccurs="0"/>
 *         &lt;element name="onFailure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsFilterConfig", propOrder = {
    "key",
    "agentName",
    "slotNumber",
    "parameters",
    "onFailure"
})
@XmlSeeAlso({
    DvsTrafficFilterConfig.class,
    DvsFilterConfigSpec.class
})
public class DvsFilterConfig
    extends InheritablePolicy
{

    protected String key;
    protected String agentName;
    protected String slotNumber;
    protected DvsFilterParameter parameters;
    protected String onFailure;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the slotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSlotNumber() {
        return slotNumber;
    }

    /**
     * Sets the value of the slotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSlotNumber(String value) {
        this.slotNumber = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link DvsFilterParameter }
     *     
     */
    public DvsFilterParameter getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link DvsFilterParameter }
     *     
     */
    public void setParameters(DvsFilterParameter value) {
        this.parameters = value;
    }

    /**
     * Gets the value of the onFailure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnFailure() {
        return onFailure;
    }

    /**
     * Sets the value of the onFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnFailure(String value) {
        this.onFailure = value;
    }

}
