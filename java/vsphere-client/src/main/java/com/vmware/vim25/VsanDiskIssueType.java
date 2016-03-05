
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanDiskIssueType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VsanDiskIssueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="nonExist"/>
 *     &lt;enumeration value="stampMismatch"/>
 *     &lt;enumeration value="unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VsanDiskIssueType")
@XmlEnum
public enum VsanDiskIssueType {

    @XmlEnumValue("nonExist")
    NON_EXIST("nonExist"),
    @XmlEnumValue("stampMismatch")
    STAMP_MISMATCH("stampMismatch"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    VsanDiskIssueType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VsanDiskIssueType fromValue(String v) {
        for (VsanDiskIssueType c: VsanDiskIssueType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
