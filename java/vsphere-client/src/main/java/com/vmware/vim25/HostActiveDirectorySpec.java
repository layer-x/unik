
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostActiveDirectorySpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostActiveDirectorySpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="domainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="camServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thumbprint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smartCardAuthenticationEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="smartCardTrustAnchors" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostActiveDirectorySpec", propOrder = {
    "domainName",
    "userName",
    "password",
    "camServer",
    "thumbprint",
    "smartCardAuthenticationEnabled",
    "smartCardTrustAnchors"
})
public class HostActiveDirectorySpec
    extends DynamicData
{

    protected String domainName;
    protected String userName;
    protected String password;
    protected String camServer;
    protected String thumbprint;
    protected Boolean smartCardAuthenticationEnabled;
    protected List<String> smartCardTrustAnchors;

    /**
     * Gets the value of the domainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Sets the value of the domainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainName(String value) {
        this.domainName = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the camServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCamServer() {
        return camServer;
    }

    /**
     * Sets the value of the camServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCamServer(String value) {
        this.camServer = value;
    }

    /**
     * Gets the value of the thumbprint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbprint() {
        return thumbprint;
    }

    /**
     * Sets the value of the thumbprint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbprint(String value) {
        this.thumbprint = value;
    }

    /**
     * Gets the value of the smartCardAuthenticationEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartCardAuthenticationEnabled() {
        return smartCardAuthenticationEnabled;
    }

    /**
     * Sets the value of the smartCardAuthenticationEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartCardAuthenticationEnabled(Boolean value) {
        this.smartCardAuthenticationEnabled = value;
    }

    /**
     * Gets the value of the smartCardTrustAnchors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the smartCardTrustAnchors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSmartCardTrustAnchors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSmartCardTrustAnchors() {
        if (smartCardTrustAnchors == null) {
            smartCardTrustAnchors = new ArrayList<String>();
        }
        return this.smartCardTrustAnchors;
    }

}
