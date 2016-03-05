
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineBootOptionsNetworkBootProtocolType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualMachineBootOptionsNetworkBootProtocolType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ipv4"/>
 *     &lt;enumeration value="ipv6"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualMachineBootOptionsNetworkBootProtocolType")
@XmlEnum
public enum VirtualMachineBootOptionsNetworkBootProtocolType {

    @XmlEnumValue("ipv4")
    IPV_4("ipv4"),
    @XmlEnumValue("ipv6")
    IPV_6("ipv6");
    private final String value;

    VirtualMachineBootOptionsNetworkBootProtocolType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualMachineBootOptionsNetworkBootProtocolType fromValue(String v) {
        for (VirtualMachineBootOptionsNetworkBootProtocolType c: VirtualMachineBootOptionsNetworkBootProtocolType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
