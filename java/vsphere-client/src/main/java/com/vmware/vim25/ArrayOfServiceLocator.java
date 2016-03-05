
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfServiceLocator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfServiceLocator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceLocator" type="{urn:vim25}ServiceLocator" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfServiceLocator", propOrder = {
    "serviceLocator"
})
public class ArrayOfServiceLocator {

    @XmlElement(name = "ServiceLocator")
    protected List<ServiceLocator> serviceLocator;

    /**
     * Gets the value of the serviceLocator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceLocator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceLocator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceLocator }
     * 
     * 
     */
    public List<ServiceLocator> getServiceLocator() {
        if (serviceLocator == null) {
            serviceLocator = new ArrayList<ServiceLocator>();
        }
        return this.serviceLocator;
    }

}
