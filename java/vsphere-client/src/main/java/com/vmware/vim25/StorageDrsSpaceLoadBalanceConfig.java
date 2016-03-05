
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorageDrsSpaceLoadBalanceConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StorageDrsSpaceLoadBalanceConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="spaceThresholdMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spaceUtilizationThreshold" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="freeSpaceThresholdGB" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minSpaceUtilizationDifference" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageDrsSpaceLoadBalanceConfig", propOrder = {
    "spaceThresholdMode",
    "spaceUtilizationThreshold",
    "freeSpaceThresholdGB",
    "minSpaceUtilizationDifference"
})
public class StorageDrsSpaceLoadBalanceConfig
    extends DynamicData
{

    protected String spaceThresholdMode;
    protected Integer spaceUtilizationThreshold;
    protected Integer freeSpaceThresholdGB;
    protected Integer minSpaceUtilizationDifference;

    /**
     * Gets the value of the spaceThresholdMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpaceThresholdMode() {
        return spaceThresholdMode;
    }

    /**
     * Sets the value of the spaceThresholdMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpaceThresholdMode(String value) {
        this.spaceThresholdMode = value;
    }

    /**
     * Gets the value of the spaceUtilizationThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSpaceUtilizationThreshold() {
        return spaceUtilizationThreshold;
    }

    /**
     * Sets the value of the spaceUtilizationThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpaceUtilizationThreshold(Integer value) {
        this.spaceUtilizationThreshold = value;
    }

    /**
     * Gets the value of the freeSpaceThresholdGB property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFreeSpaceThresholdGB() {
        return freeSpaceThresholdGB;
    }

    /**
     * Sets the value of the freeSpaceThresholdGB property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFreeSpaceThresholdGB(Integer value) {
        this.freeSpaceThresholdGB = value;
    }

    /**
     * Gets the value of the minSpaceUtilizationDifference property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinSpaceUtilizationDifference() {
        return minSpaceUtilizationDifference;
    }

    /**
     * Sets the value of the minSpaceUtilizationDifference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinSpaceUtilizationDifference(Integer value) {
        this.minSpaceUtilizationDifference = value;
    }

}
