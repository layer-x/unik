
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CannotUseNetworkReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CannotUseNetworkReason">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NetworkReservationNotSupported"/>
 *     &lt;enumeration value="MismatchedNetworkPolicies"/>
 *     &lt;enumeration value="MismatchedDvsVersionOrVendor"/>
 *     &lt;enumeration value="VMotionToUnsupportedNetworkType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CannotUseNetworkReason")
@XmlEnum
public enum CannotUseNetworkReason {

    @XmlEnumValue("NetworkReservationNotSupported")
    NETWORK_RESERVATION_NOT_SUPPORTED("NetworkReservationNotSupported"),
    @XmlEnumValue("MismatchedNetworkPolicies")
    MISMATCHED_NETWORK_POLICIES("MismatchedNetworkPolicies"),
    @XmlEnumValue("MismatchedDvsVersionOrVendor")
    MISMATCHED_DVS_VERSION_OR_VENDOR("MismatchedDvsVersionOrVendor"),
    @XmlEnumValue("VMotionToUnsupportedNetworkType")
    V_MOTION_TO_UNSUPPORTED_NETWORK_TYPE("VMotionToUnsupportedNetworkType");
    private final String value;

    CannotUseNetworkReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CannotUseNetworkReason fromValue(String v) {
        for (CannotUseNetworkReason c: CannotUseNetworkReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
