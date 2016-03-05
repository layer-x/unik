
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDvsFilterConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDvsFilterConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DvsFilterConfig" type="{urn:vim25}DvsFilterConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDvsFilterConfig", propOrder = {
    "dvsFilterConfig"
})
public class ArrayOfDvsFilterConfig {

    @XmlElement(name = "DvsFilterConfig")
    protected List<DvsFilterConfig> dvsFilterConfig;

    /**
     * Gets the value of the dvsFilterConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsFilterConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsFilterConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DvsFilterConfig }
     * 
     * 
     */
    public List<DvsFilterConfig> getDvsFilterConfig() {
        if (dvsFilterConfig == null) {
            dvsFilterConfig = new ArrayList<DvsFilterConfig>();
        }
        return this.dvsFilterConfig;
    }

}
