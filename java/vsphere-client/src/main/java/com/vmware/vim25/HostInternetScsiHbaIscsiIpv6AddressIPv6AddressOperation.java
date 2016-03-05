
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="add"/>
 *     &lt;enumeration value="remove"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation")
@XmlEnum
public enum HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation {

    @XmlEnumValue("add")
    ADD("add"),
    @XmlEnumValue("remove")
    REMOVE("remove");
    private final String value;

    HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation fromValue(String v) {
        for (HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation c: HostInternetScsiHbaIscsiIpv6AddressIPv6AddressOperation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
