
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProtocolEndpointPEType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostProtocolEndpointPEType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="block"/>
 *     &lt;enumeration value="nas"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostProtocolEndpointPEType")
@XmlEnum
public enum HostProtocolEndpointPEType {

    @XmlEnumValue("block")
    BLOCK("block"),
    @XmlEnumValue("nas")
    NAS("nas");
    private final String value;

    HostProtocolEndpointPEType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostProtocolEndpointPEType fromValue(String v) {
        for (HostProtocolEndpointPEType c: HostProtocolEndpointPEType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
