
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDiskDeltaDiskFormatVariant.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualDiskDeltaDiskFormatVariant">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="vmfsSparseVariant"/>
 *     &lt;enumeration value="vsanSparseVariant"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualDiskDeltaDiskFormatVariant")
@XmlEnum
public enum VirtualDiskDeltaDiskFormatVariant {

    @XmlEnumValue("vmfsSparseVariant")
    VMFS_SPARSE_VARIANT("vmfsSparseVariant"),
    @XmlEnumValue("vsanSparseVariant")
    VSAN_SPARSE_VARIANT("vsanSparseVariant");
    private final String value;

    VirtualDiskDeltaDiskFormatVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualDiskDeltaDiskFormatVariant fromValue(String v) {
        for (VirtualDiskDeltaDiskFormatVariant c: VirtualDiskDeltaDiskFormatVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
