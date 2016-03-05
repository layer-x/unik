
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostDecommissionModeObjectAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VsanHostDecommissionModeObjectAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="noAction"/>
 *     &lt;enumeration value="ensureObjectAccessibility"/>
 *     &lt;enumeration value="evacuateAllData"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VsanHostDecommissionModeObjectAction")
@XmlEnum
public enum VsanHostDecommissionModeObjectAction {

    @XmlEnumValue("noAction")
    NO_ACTION("noAction"),
    @XmlEnumValue("ensureObjectAccessibility")
    ENSURE_OBJECT_ACCESSIBILITY("ensureObjectAccessibility"),
    @XmlEnumValue("evacuateAllData")
    EVACUATE_ALL_DATA("evacuateAllData");
    private final String value;

    VsanHostDecommissionModeObjectAction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VsanHostDecommissionModeObjectAction fromValue(String v) {
        for (VsanHostDecommissionModeObjectAction c: VsanHostDecommissionModeObjectAction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
