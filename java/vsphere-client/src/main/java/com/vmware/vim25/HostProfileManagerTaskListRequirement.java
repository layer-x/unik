
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProfileManagerTaskListRequirement.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostProfileManagerTaskListRequirement">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="maintenanceModeRequired"/>
 *     &lt;enumeration value="rebootRequired"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostProfileManagerTaskListRequirement")
@XmlEnum
public enum HostProfileManagerTaskListRequirement {

    @XmlEnumValue("maintenanceModeRequired")
    MAINTENANCE_MODE_REQUIRED("maintenanceModeRequired"),
    @XmlEnumValue("rebootRequired")
    REBOOT_REQUIRED("rebootRequired");
    private final String value;

    HostProfileManagerTaskListRequirement(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostProfileManagerTaskListRequirement fromValue(String v) {
        for (HostProfileManagerTaskListRequirement c: HostProfileManagerTaskListRequirement.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
