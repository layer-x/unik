
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceLocator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLocator">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="instanceUuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="credential" type="{urn:vim25}ServiceLocatorCredential"/>
 *         &lt;element name="sslThumbprint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLocator", propOrder = {
    "instanceUuid",
    "url",
    "credential",
    "sslThumbprint"
})
public class ServiceLocator
    extends DynamicData
{

    @XmlElement(required = true)
    protected String instanceUuid;
    @XmlElement(required = true)
    protected String url;
    @XmlElement(required = true)
    protected ServiceLocatorCredential credential;
    protected String sslThumbprint;

    /**
     * Gets the value of the instanceUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstanceUuid() {
        return instanceUuid;
    }

    /**
     * Sets the value of the instanceUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstanceUuid(String value) {
        this.instanceUuid = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the credential property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLocatorCredential }
     *     
     */
    public ServiceLocatorCredential getCredential() {
        return credential;
    }

    /**
     * Sets the value of the credential property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLocatorCredential }
     *     
     */
    public void setCredential(ServiceLocatorCredential value) {
        this.credential = value;
    }

    /**
     * Gets the value of the sslThumbprint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSslThumbprint() {
        return sslThumbprint;
    }

    /**
     * Sets the value of the sslThumbprint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSslThumbprint(String value) {
        this.sslThumbprint = value;
    }

}
