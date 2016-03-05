
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestInfoAppStateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GuestInfoAppStateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="appStateOk"/>
 *     &lt;enumeration value="appStateNeedReset"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GuestInfoAppStateType")
@XmlEnum
public enum GuestInfoAppStateType {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("appStateOk")
    APP_STATE_OK("appStateOk"),
    @XmlEnumValue("appStateNeedReset")
    APP_STATE_NEED_RESET("appStateNeedReset");
    private final String value;

    GuestInfoAppStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GuestInfoAppStateType fromValue(String v) {
        for (GuestInfoAppStateType c: GuestInfoAppStateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
