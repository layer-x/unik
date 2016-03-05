
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistributedVirtualSwitchNetworkResourceControlVersion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DistributedVirtualSwitchNetworkResourceControlVersion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="version2"/>
 *     &lt;enumeration value="version3"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DistributedVirtualSwitchNetworkResourceControlVersion")
@XmlEnum
public enum DistributedVirtualSwitchNetworkResourceControlVersion {

    @XmlEnumValue("version2")
    VERSION_2("version2"),
    @XmlEnumValue("version3")
    VERSION_3("version3");
    private final String value;

    DistributedVirtualSwitchNetworkResourceControlVersion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistributedVirtualSwitchNetworkResourceControlVersion fromValue(String v) {
        for (DistributedVirtualSwitchNetworkResourceControlVersion c: DistributedVirtualSwitchNetworkResourceControlVersion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
