
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VFlashModuleNotSupportedReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VFlashModuleNotSupportedReason">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CacheModeNotSupported"/>
 *     &lt;enumeration value="CacheConsistencyTypeNotSupported"/>
 *     &lt;enumeration value="CacheBlockSizeNotSupported"/>
 *     &lt;enumeration value="CacheReservationNotSupported"/>
 *     &lt;enumeration value="DiskSizeNotSupported"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VFlashModuleNotSupportedReason")
@XmlEnum
public enum VFlashModuleNotSupportedReason {

    @XmlEnumValue("CacheModeNotSupported")
    CACHE_MODE_NOT_SUPPORTED("CacheModeNotSupported"),
    @XmlEnumValue("CacheConsistencyTypeNotSupported")
    CACHE_CONSISTENCY_TYPE_NOT_SUPPORTED("CacheConsistencyTypeNotSupported"),
    @XmlEnumValue("CacheBlockSizeNotSupported")
    CACHE_BLOCK_SIZE_NOT_SUPPORTED("CacheBlockSizeNotSupported"),
    @XmlEnumValue("CacheReservationNotSupported")
    CACHE_RESERVATION_NOT_SUPPORTED("CacheReservationNotSupported"),
    @XmlEnumValue("DiskSizeNotSupported")
    DISK_SIZE_NOT_SUPPORTED("DiskSizeNotSupported");
    private final String value;

    VFlashModuleNotSupportedReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VFlashModuleNotSupportedReason fromValue(String v) {
        for (VFlashModuleNotSupportedReason c: VFlashModuleNotSupportedReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
