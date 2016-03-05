
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IncompatibleHostForVmReplicationIncompatibleReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IncompatibleHostForVmReplicationIncompatibleReason">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="rpo"/>
 *     &lt;enumeration value="netCompression"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IncompatibleHostForVmReplicationIncompatibleReason")
@XmlEnum
public enum IncompatibleHostForVmReplicationIncompatibleReason {

    @XmlEnumValue("rpo")
    RPO("rpo"),
    @XmlEnumValue("netCompression")
    NET_COMPRESSION("netCompression");
    private final String value;

    IncompatibleHostForVmReplicationIncompatibleReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IncompatibleHostForVmReplicationIncompatibleReason fromValue(String v) {
        for (IncompatibleHostForVmReplicationIncompatibleReason c: IncompatibleHostForVmReplicationIncompatibleReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
