
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDvsTrafficRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDvsTrafficRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DvsTrafficRule" type="{urn:vim25}DvsTrafficRule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDvsTrafficRule", propOrder = {
    "dvsTrafficRule"
})
public class ArrayOfDvsTrafficRule {

    @XmlElement(name = "DvsTrafficRule")
    protected List<DvsTrafficRule> dvsTrafficRule;

    /**
     * Gets the value of the dvsTrafficRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsTrafficRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsTrafficRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DvsTrafficRule }
     * 
     * 
     */
    public List<DvsTrafficRule> getDvsTrafficRule() {
        if (dvsTrafficRule == null) {
            dvsTrafficRule = new ArrayList<DvsTrafficRule>();
        }
        return this.dvsTrafficRule;
    }

}
