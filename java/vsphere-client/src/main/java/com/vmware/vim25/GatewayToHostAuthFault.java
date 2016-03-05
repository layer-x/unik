
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GatewayToHostAuthFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GatewayToHostAuthFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}GatewayToHostConnectFault">
 *       &lt;sequence>
 *         &lt;element name="invalidProperties" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="missingProperties" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GatewayToHostAuthFault", propOrder = {
    "invalidProperties",
    "missingProperties"
})
public class GatewayToHostAuthFault
    extends GatewayToHostConnectFault
{

    @XmlElement(required = true)
    protected List<String> invalidProperties;
    @XmlElement(required = true)
    protected List<String> missingProperties;

    /**
     * Gets the value of the invalidProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invalidProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvalidProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInvalidProperties() {
        if (invalidProperties == null) {
            invalidProperties = new ArrayList<String>();
        }
        return this.invalidProperties;
    }

    /**
     * Gets the value of the missingProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the missingProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMissingProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMissingProperties() {
        if (missingProperties == null) {
            missingProperties = new ArrayList<String>();
        }
        return this.missingProperties;
    }

}
