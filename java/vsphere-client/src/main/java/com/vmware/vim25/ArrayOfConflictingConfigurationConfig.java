
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfConflictingConfigurationConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConflictingConfigurationConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConflictingConfigurationConfig" type="{urn:vim25}ConflictingConfigurationConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConflictingConfigurationConfig", propOrder = {
    "conflictingConfigurationConfig"
})
public class ArrayOfConflictingConfigurationConfig {

    @XmlElement(name = "ConflictingConfigurationConfig")
    protected List<ConflictingConfigurationConfig> conflictingConfigurationConfig;

    /**
     * Gets the value of the conflictingConfigurationConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conflictingConfigurationConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConflictingConfigurationConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConflictingConfigurationConfig }
     * 
     * 
     */
    public List<ConflictingConfigurationConfig> getConflictingConfigurationConfig() {
        if (conflictingConfigurationConfig == null) {
            conflictingConfigurationConfig = new ArrayList<ConflictingConfigurationConfig>();
        }
        return this.conflictingConfigurationConfig;
    }

}
