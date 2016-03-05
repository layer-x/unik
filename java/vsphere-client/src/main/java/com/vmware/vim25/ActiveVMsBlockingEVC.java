
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActiveVMsBlockingEVC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActiveVMsBlockingEVC">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}EVCConfigFault">
 *       &lt;sequence>
 *         &lt;element name="evcMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="host" type="{urn:vim25}ManagedObjectReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActiveVMsBlockingEVC", propOrder = {
    "evcMode",
    "host",
    "hostName"
})
public class ActiveVMsBlockingEVC
    extends EVCConfigFault
{

    protected String evcMode;
    protected List<ManagedObjectReference> host;
    protected List<String> hostName;

    /**
     * Gets the value of the evcMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvcMode() {
        return evcMode;
    }

    /**
     * Sets the value of the evcMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvcMode(String value) {
        this.evcMode = value;
    }

    /**
     * Gets the value of the host property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the host property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHost().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManagedObjectReference }
     * 
     * 
     */
    public List<ManagedObjectReference> getHost() {
        if (host == null) {
            host = new ArrayList<ManagedObjectReference>();
        }
        return this.host;
    }

    /**
     * Gets the value of the hostName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHostName() {
        if (hostName == null) {
            hostName = new ArrayList<String>();
        }
        return this.hostName;
    }

}
