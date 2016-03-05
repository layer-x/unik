
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDvsVmVnicResourcePoolConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDvsVmVnicResourcePoolConfigSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DvsVmVnicResourcePoolConfigSpec" type="{urn:vim25}DvsVmVnicResourcePoolConfigSpec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDvsVmVnicResourcePoolConfigSpec", propOrder = {
    "dvsVmVnicResourcePoolConfigSpec"
})
public class ArrayOfDvsVmVnicResourcePoolConfigSpec {

    @XmlElement(name = "DvsVmVnicResourcePoolConfigSpec")
    protected List<DvsVmVnicResourcePoolConfigSpec> dvsVmVnicResourcePoolConfigSpec;

    /**
     * Gets the value of the dvsVmVnicResourcePoolConfigSpec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dvsVmVnicResourcePoolConfigSpec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDvsVmVnicResourcePoolConfigSpec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DvsVmVnicResourcePoolConfigSpec }
     * 
     * 
     */
    public List<DvsVmVnicResourcePoolConfigSpec> getDvsVmVnicResourcePoolConfigSpec() {
        if (dvsVmVnicResourcePoolConfigSpec == null) {
            dvsVmVnicResourcePoolConfigSpec = new ArrayList<DvsVmVnicResourcePoolConfigSpec>();
        }
        return this.dvsVmVnicResourcePoolConfigSpec;
    }

}
