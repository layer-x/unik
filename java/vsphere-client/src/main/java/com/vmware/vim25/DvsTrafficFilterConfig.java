
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsTrafficFilterConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsTrafficFilterConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsFilterConfig">
 *       &lt;sequence>
 *         &lt;element name="trafficRuleset" type="{urn:vim25}DvsTrafficRuleset" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsTrafficFilterConfig", propOrder = {
    "trafficRuleset"
})
@XmlSeeAlso({
    DvsTrafficFilterConfigSpec.class
})
public class DvsTrafficFilterConfig
    extends DvsFilterConfig
{

    protected DvsTrafficRuleset trafficRuleset;

    /**
     * Gets the value of the trafficRuleset property.
     * 
     * @return
     *     possible object is
     *     {@link DvsTrafficRuleset }
     *     
     */
    public DvsTrafficRuleset getTrafficRuleset() {
        return trafficRuleset;
    }

    /**
     * Sets the value of the trafficRuleset property.
     * 
     * @param value
     *     allowed object is
     *     {@link DvsTrafficRuleset }
     *     
     */
    public void setTrafficRuleset(DvsTrafficRuleset value) {
        this.trafficRuleset = value;
    }

}
