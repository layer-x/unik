
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostRuntimeInfoNetStackInstanceRuntimeInfoState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostRuntimeInfoNetStackInstanceRuntimeInfoState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="inactive"/>
 *     &lt;enumeration value="active"/>
 *     &lt;enumeration value="deactivating"/>
 *     &lt;enumeration value="activating"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostRuntimeInfoNetStackInstanceRuntimeInfoState")
@XmlEnum
public enum HostRuntimeInfoNetStackInstanceRuntimeInfoState {

    @XmlEnumValue("inactive")
    INACTIVE("inactive"),
    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("deactivating")
    DEACTIVATING("deactivating"),
    @XmlEnumValue("activating")
    ACTIVATING("activating");
    private final String value;

    HostRuntimeInfoNetStackInstanceRuntimeInfoState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostRuntimeInfoNetStackInstanceRuntimeInfoState fromValue(String v) {
        for (HostRuntimeInfoNetStackInstanceRuntimeInfoState c: HostRuntimeInfoNetStackInstanceRuntimeInfoState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
