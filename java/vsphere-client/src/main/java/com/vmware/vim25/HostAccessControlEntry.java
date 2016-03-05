
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostAccessControlEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostAccessControlEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="principal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="accessMode" type="{urn:vim25}HostAccessMode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostAccessControlEntry", propOrder = {
    "principal",
    "group",
    "accessMode"
})
public class HostAccessControlEntry
    extends DynamicData
{

    @XmlElement(required = true)
    protected String principal;
    protected boolean group;
    @XmlElement(required = true)
    protected HostAccessMode accessMode;

    /**
     * Gets the value of the principal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Sets the value of the principal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrincipal(String value) {
        this.principal = value;
    }

    /**
     * Gets the value of the group property.
     * 
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     */
    public void setGroup(boolean value) {
        this.group = value;
    }

    /**
     * Gets the value of the accessMode property.
     * 
     * @return
     *     possible object is
     *     {@link HostAccessMode }
     *     
     */
    public HostAccessMode getAccessMode() {
        return accessMode;
    }

    /**
     * Sets the value of the accessMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostAccessMode }
     *     
     */
    public void setAccessMode(HostAccessMode value) {
        this.accessMode = value;
    }

}
