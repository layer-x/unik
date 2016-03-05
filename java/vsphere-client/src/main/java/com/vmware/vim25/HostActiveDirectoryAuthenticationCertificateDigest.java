
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostActiveDirectoryAuthenticationCertificateDigest.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostActiveDirectoryAuthenticationCertificateDigest">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SHA1"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostActiveDirectoryAuthenticationCertificateDigest")
@XmlEnum
public enum HostActiveDirectoryAuthenticationCertificateDigest {

    @XmlEnumValue("SHA1")
    SHA_1("SHA1");
    private final String value;

    HostActiveDirectoryAuthenticationCertificateDigest(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostActiveDirectoryAuthenticationCertificateDigest fromValue(String v) {
        for (HostActiveDirectoryAuthenticationCertificateDigest c: HostActiveDirectoryAuthenticationCertificateDigest.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
