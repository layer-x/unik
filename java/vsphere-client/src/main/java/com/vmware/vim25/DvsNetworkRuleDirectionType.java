
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsNetworkRuleDirectionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DvsNetworkRuleDirectionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="incomingPackets"/>
 *     &lt;enumeration value="outgoingPackets"/>
 *     &lt;enumeration value="both"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DvsNetworkRuleDirectionType")
@XmlEnum
public enum DvsNetworkRuleDirectionType {

    @XmlEnumValue("incomingPackets")
    INCOMING_PACKETS("incomingPackets"),
    @XmlEnumValue("outgoingPackets")
    OUTGOING_PACKETS("outgoingPackets"),
    @XmlEnumValue("both")
    BOTH("both");
    private final String value;

    DvsNetworkRuleDirectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DvsNetworkRuleDirectionType fromValue(String v) {
        for (DvsNetworkRuleDirectionType c: DvsNetworkRuleDirectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
