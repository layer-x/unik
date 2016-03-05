
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDiskVFlashCacheConfigInfoCacheConsistencyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualDiskVFlashCacheConfigInfoCacheConsistencyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="strong"/>
 *     &lt;enumeration value="weak"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualDiskVFlashCacheConfigInfoCacheConsistencyType")
@XmlEnum
public enum VirtualDiskVFlashCacheConfigInfoCacheConsistencyType {

    @XmlEnumValue("strong")
    STRONG("strong"),
    @XmlEnumValue("weak")
    WEAK("weak");
    private final String value;

    VirtualDiskVFlashCacheConfigInfoCacheConsistencyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualDiskVFlashCacheConfigInfoCacheConsistencyType fromValue(String v) {
        for (VirtualDiskVFlashCacheConfigInfoCacheConsistencyType c: VirtualDiskVFlashCacheConfigInfoCacheConsistencyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
