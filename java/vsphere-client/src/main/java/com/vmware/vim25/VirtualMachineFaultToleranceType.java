
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineFaultToleranceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualMachineFaultToleranceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="unset"/>
 *     &lt;enumeration value="recordReplay"/>
 *     &lt;enumeration value="checkpointing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualMachineFaultToleranceType")
@XmlEnum
public enum VirtualMachineFaultToleranceType {

    @XmlEnumValue("unset")
    UNSET("unset"),
    @XmlEnumValue("recordReplay")
    RECORD_REPLAY("recordReplay"),
    @XmlEnumValue("checkpointing")
    CHECKPOINTING("checkpointing");
    private final String value;

    VirtualMachineFaultToleranceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualMachineFaultToleranceType fromValue(String v) {
        for (VirtualMachineFaultToleranceType c: VirtualMachineFaultToleranceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
