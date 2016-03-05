
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineForkConfigInfoChildType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualMachineForkConfigInfoChildType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="persistent"/>
 *     &lt;enumeration value="nonpersistent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualMachineForkConfigInfoChildType")
@XmlEnum
public enum VirtualMachineForkConfigInfoChildType {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("persistent")
    PERSISTENT("persistent"),
    @XmlEnumValue("nonpersistent")
    NONPERSISTENT("nonpersistent");
    private final String value;

    VirtualMachineForkConfigInfoChildType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualMachineForkConfigInfoChildType fromValue(String v) {
        for (VirtualMachineForkConfigInfoChildType c: VirtualMachineForkConfigInfoChildType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
