
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostHealthState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VsanHostHealthState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="unknown"/>
 *     &lt;enumeration value="healthy"/>
 *     &lt;enumeration value="unhealthy"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VsanHostHealthState")
@XmlEnum
public enum VsanHostHealthState {

    @XmlEnumValue("unknown")
    UNKNOWN("unknown"),
    @XmlEnumValue("healthy")
    HEALTHY("healthy"),
    @XmlEnumValue("unhealthy")
    UNHEALTHY("unhealthy");
    private final String value;

    VsanHostHealthState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VsanHostHealthState fromValue(String v) {
        for (VsanHostHealthState c: VsanHostHealthState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
