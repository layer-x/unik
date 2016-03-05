
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConflictingConfigurationConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConflictingConfigurationConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *         &lt;element name="propertyPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConflictingConfigurationConfig", propOrder = {
    "entity",
    "propertyPath"
})
public class ConflictingConfigurationConfig
    extends DynamicData
{

    protected ManagedObjectReference entity;
    @XmlElement(required = true)
    protected String propertyPath;

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setEntity(ManagedObjectReference value) {
        this.entity = value;
    }

    /**
     * Gets the value of the propertyPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyPath() {
        return propertyPath;
    }

    /**
     * Sets the value of the propertyPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyPath(String value) {
        this.propertyPath = value;
    }

}
