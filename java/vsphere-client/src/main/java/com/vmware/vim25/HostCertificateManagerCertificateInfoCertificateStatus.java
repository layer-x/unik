
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostCertificateManagerCertificateInfoCertificateStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostCertificateManagerCertificateInfoCertificateStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="unknown"/>
 *     &lt;enumeration value="expired"/>
 *     &lt;enumeration value="expiring"/>
 *     &lt;enumeration value="expiringShortly"/>
 *     &lt;enumeration value="expirationImminent"/>
 *     &lt;enumeration value="good"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostCertificateManagerCertificateInfoCertificateStatus")
@XmlEnum
public enum HostCertificateManagerCertificateInfoCertificateStatus {

    @XmlEnumValue("unknown")
    UNKNOWN("unknown"),
    @XmlEnumValue("expired")
    EXPIRED("expired"),
    @XmlEnumValue("expiring")
    EXPIRING("expiring"),
    @XmlEnumValue("expiringShortly")
    EXPIRING_SHORTLY("expiringShortly"),
    @XmlEnumValue("expirationImminent")
    EXPIRATION_IMMINENT("expirationImminent"),
    @XmlEnumValue("good")
    GOOD("good");
    private final String value;

    HostCertificateManagerCertificateInfoCertificateStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostCertificateManagerCertificateInfoCertificateStatus fromValue(String v) {
        for (HostCertificateManagerCertificateInfoCertificateStatus c: HostCertificateManagerCertificateInfoCertificateStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
