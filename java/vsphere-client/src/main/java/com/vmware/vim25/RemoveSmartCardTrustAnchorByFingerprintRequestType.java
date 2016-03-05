
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemoveSmartCardTrustAnchorByFingerprintRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemoveSmartCardTrustAnchorByFingerprintRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="fingerprint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="digest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveSmartCardTrustAnchorByFingerprintRequestType", propOrder = {
    "_this",
    "fingerprint",
    "digest"
})
public class RemoveSmartCardTrustAnchorByFingerprintRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected String fingerprint;
    @XmlElement(required = true)
    protected String digest;

    /**
     * Gets the value of the this property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getThis() {
        return _this;
    }

    /**
     * Sets the value of the this property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setThis(ManagedObjectReference value) {
        this._this = value;
    }

    /**
     * Gets the value of the fingerprint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * Sets the value of the fingerprint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFingerprint(String value) {
        this.fingerprint = value;
    }

    /**
     * Gets the value of the digest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Sets the value of the digest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigest(String value) {
        this.digest = value;
    }

}
