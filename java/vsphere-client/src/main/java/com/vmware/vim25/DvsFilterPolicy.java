
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsFilterPolicy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsFilterPolicy">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}InheritablePolicy">
 *       &lt;sequence>
 *         &lt;element name="filterConfig" type="{urn:vim25}DvsFilterConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsFilterPolicy", propOrder = {
    "filterConfig"
})
public class DvsFilterPolicy
    extends InheritablePolicy
{

    protected List<DvsFilterConfig> filterConfig;

    /**
     * Gets the value of the filterConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DvsFilterConfig }
     * 
     * 
     */
    public List<DvsFilterConfig> getFilterConfig() {
        if (filterConfig == null) {
            filterConfig = new ArrayList<DvsFilterConfig>();
        }
        return this.filterConfig;
    }

}
