
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsSystemTrafficNetworkRuleQualifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsSystemTrafficNetworkRuleQualifier">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsNetworkRuleQualifier">
 *       &lt;sequence>
 *         &lt;element name="typeOfSystemTraffic" type="{urn:vim25}StringExpression" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsSystemTrafficNetworkRuleQualifier", propOrder = {
    "typeOfSystemTraffic"
})
public class DvsSystemTrafficNetworkRuleQualifier
    extends DvsNetworkRuleQualifier
{

    protected StringExpression typeOfSystemTraffic;

    /**
     * Gets the value of the typeOfSystemTraffic property.
     * 
     * @return
     *     possible object is
     *     {@link StringExpression }
     *     
     */
    public StringExpression getTypeOfSystemTraffic() {
        return typeOfSystemTraffic;
    }

    /**
     * Sets the value of the typeOfSystemTraffic property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringExpression }
     *     
     */
    public void setTypeOfSystemTraffic(StringExpression value) {
        this.typeOfSystemTraffic = value;
    }

}
