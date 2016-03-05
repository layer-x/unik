
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDiskVFlashCacheConfigInfoCacheMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualDiskVFlashCacheConfigInfoCacheMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="write_thru"/>
 *     &lt;enumeration value="write_back"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualDiskVFlashCacheConfigInfoCacheMode")
@XmlEnum
public enum VirtualDiskVFlashCacheConfigInfoCacheMode {

    @XmlEnumValue("write_thru")
    WRITE_THRU("write_thru"),
    @XmlEnumValue("write_back")
    WRITE_BACK("write_back");
    private final String value;

    VirtualDiskVFlashCacheConfigInfoCacheMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualDiskVFlashCacheConfigInfoCacheMode fromValue(String v) {
        for (VirtualDiskVFlashCacheConfigInfoCacheMode c: VirtualDiskVFlashCacheConfigInfoCacheMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
