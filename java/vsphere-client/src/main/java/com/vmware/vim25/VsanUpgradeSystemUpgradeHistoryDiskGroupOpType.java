
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemUpgradeHistoryDiskGroupOpType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VsanUpgradeSystemUpgradeHistoryDiskGroupOpType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="add"/>
 *     &lt;enumeration value="remove"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VsanUpgradeSystemUpgradeHistoryDiskGroupOpType")
@XmlEnum
public enum VsanUpgradeSystemUpgradeHistoryDiskGroupOpType {

    @XmlEnumValue("add")
    ADD("add"),
    @XmlEnumValue("remove")
    REMOVE("remove");
    private final String value;

    VsanUpgradeSystemUpgradeHistoryDiskGroupOpType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VsanUpgradeSystemUpgradeHistoryDiskGroupOpType fromValue(String v) {
        for (VsanUpgradeSystemUpgradeHistoryDiskGroupOpType c: VsanUpgradeSystemUpgradeHistoryDiskGroupOpType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
