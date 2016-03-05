
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostOpaqueSwitchOpaqueSwitchState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostOpaqueSwitchOpaqueSwitchState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="up"/>
 *     &lt;enumeration value="warning"/>
 *     &lt;enumeration value="down"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostOpaqueSwitchOpaqueSwitchState")
@XmlEnum
public enum HostOpaqueSwitchOpaqueSwitchState {

    @XmlEnumValue("up")
    UP("up"),
    @XmlEnumValue("warning")
    WARNING("warning"),
    @XmlEnumValue("down")
    DOWN("down");
    private final String value;

    HostOpaqueSwitchOpaqueSwitchState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostOpaqueSwitchOpaqueSwitchState fromValue(String v) {
        for (HostOpaqueSwitchOpaqueSwitchState c: HostOpaqueSwitchOpaqueSwitchState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
