
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVsanHostConfigInfoNetworkInfoPortConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVsanHostConfigInfoNetworkInfoPortConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VsanHostConfigInfoNetworkInfoPortConfig" type="{urn:vim25}VsanHostConfigInfoNetworkInfoPortConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVsanHostConfigInfoNetworkInfoPortConfig", propOrder = {
    "vsanHostConfigInfoNetworkInfoPortConfig"
})
public class ArrayOfVsanHostConfigInfoNetworkInfoPortConfig {

    @XmlElement(name = "VsanHostConfigInfoNetworkInfoPortConfig")
    protected List<VsanHostConfigInfoNetworkInfoPortConfig> vsanHostConfigInfoNetworkInfoPortConfig;

    /**
     * Gets the value of the vsanHostConfigInfoNetworkInfoPortConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vsanHostConfigInfoNetworkInfoPortConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVsanHostConfigInfoNetworkInfoPortConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostConfigInfoNetworkInfoPortConfig }
     * 
     * 
     */
    public List<VsanHostConfigInfoNetworkInfoPortConfig> getVsanHostConfigInfoNetworkInfoPortConfig() {
        if (vsanHostConfigInfoNetworkInfoPortConfig == null) {
            vsanHostConfigInfoNetworkInfoPortConfig = new ArrayList<VsanHostConfigInfoNetworkInfoPortConfig>();
        }
        return this.vsanHostConfigInfoNetworkInfoPortConfig;
    }

}
