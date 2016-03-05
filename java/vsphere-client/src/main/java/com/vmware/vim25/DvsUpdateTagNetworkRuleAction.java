
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsUpdateTagNetworkRuleAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsUpdateTagNetworkRuleAction">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsNetworkRuleAction">
 *       &lt;sequence>
 *         &lt;element name="qosTag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dscpTag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsUpdateTagNetworkRuleAction", propOrder = {
    "qosTag",
    "dscpTag"
})
public class DvsUpdateTagNetworkRuleAction
    extends DvsNetworkRuleAction
{

    protected Integer qosTag;
    protected Integer dscpTag;

    /**
     * Gets the value of the qosTag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQosTag() {
        return qosTag;
    }

    /**
     * Sets the value of the qosTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQosTag(Integer value) {
        this.qosTag = value;
    }

    /**
     * Gets the value of the dscpTag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDscpTag() {
        return dscpTag;
    }

    /**
     * Sets the value of the dscpTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDscpTag(Integer value) {
        this.dscpTag = value;
    }

}
