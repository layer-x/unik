
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostNasVolume complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostNasVolume">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostFileSystemVolume">
 *       &lt;sequence>
 *         &lt;element name="remoteHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remotePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteHostNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="securityType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protocolEndpoint" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostNasVolume", propOrder = {
    "remoteHost",
    "remotePath",
    "userName",
    "remoteHostNames",
    "securityType",
    "protocolEndpoint"
})
public class HostNasVolume
    extends HostFileSystemVolume
{

    @XmlElement(required = true)
    protected String remoteHost;
    @XmlElement(required = true)
    protected String remotePath;
    protected String userName;
    protected List<String> remoteHostNames;
    protected String securityType;
    protected Boolean protocolEndpoint;

    /**
     * Gets the value of the remoteHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * Sets the value of the remoteHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteHost(String value) {
        this.remoteHost = value;
    }

    /**
     * Gets the value of the remotePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * Sets the value of the remotePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemotePath(String value) {
        this.remotePath = value;
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
     * Gets the value of the remoteHostNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remoteHostNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoteHostNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRemoteHostNames() {
        if (remoteHostNames == null) {
            remoteHostNames = new ArrayList<String>();
        }
        return this.remoteHostNames;
    }

    /**
     * Gets the value of the securityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityType() {
        return securityType;
    }

    /**
     * Sets the value of the securityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityType(String value) {
        this.securityType = value;
    }

    /**
     * Gets the value of the protocolEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProtocolEndpoint() {
        return protocolEndpoint;
    }

    /**
     * Sets the value of the protocolEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProtocolEndpoint(Boolean value) {
        this.protocolEndpoint = value;
    }

}
