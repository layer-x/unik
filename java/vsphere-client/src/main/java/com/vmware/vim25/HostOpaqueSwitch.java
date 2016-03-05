
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostOpaqueSwitch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostOpaqueSwitch">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pnic" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pnicZone" type="{urn:vim25}HostOpaqueSwitchPhysicalNicZone" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vtep" type="{urn:vim25}HostVirtualNic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostOpaqueSwitch", propOrder = {
    "key",
    "name",
    "pnic",
    "pnicZone",
    "status",
    "vtep"
})
public class HostOpaqueSwitch
    extends DynamicData
{

    @XmlElement(required = true)
    protected String key;
    protected String name;
    protected List<String> pnic;
    protected List<HostOpaqueSwitchPhysicalNicZone> pnicZone;
    protected String status;
    protected List<HostVirtualNic> vtep;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the pnic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pnic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPnic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPnic() {
        if (pnic == null) {
            pnic = new ArrayList<String>();
        }
        return this.pnic;
    }

    /**
     * Gets the value of the pnicZone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pnicZone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPnicZone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostOpaqueSwitchPhysicalNicZone }
     * 
     * 
     */
    public List<HostOpaqueSwitchPhysicalNicZone> getPnicZone() {
        if (pnicZone == null) {
            pnicZone = new ArrayList<HostOpaqueSwitchPhysicalNicZone>();
        }
        return this.pnicZone;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the vtep property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vtep property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVtep().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostVirtualNic }
     * 
     * 
     */
    public List<HostVirtualNic> getVtep() {
        if (vtep == null) {
            vtep = new ArrayList<HostVirtualNic>();
        }
        return this.vtep;
    }

}
