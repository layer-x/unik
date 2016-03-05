
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostFileSystemVolumeFileSystemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostFileSystemVolumeFileSystemType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VMFS"/>
 *     &lt;enumeration value="NFS"/>
 *     &lt;enumeration value="NFS41"/>
 *     &lt;enumeration value="CIFS"/>
 *     &lt;enumeration value="vsan"/>
 *     &lt;enumeration value="VFFS"/>
 *     &lt;enumeration value="VVOL"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostFileSystemVolumeFileSystemType")
@XmlEnum
public enum HostFileSystemVolumeFileSystemType {

    VMFS("VMFS"),
    NFS("NFS"),
    @XmlEnumValue("NFS41")
    NFS_41("NFS41"),
    CIFS("CIFS"),
    @XmlEnumValue("vsan")
    VSAN("vsan"),
    VFFS("VFFS"),
    VVOL("VVOL"),
    OTHER("OTHER");
    private final String value;

    HostFileSystemVolumeFileSystemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostFileSystemVolumeFileSystemType fromValue(String v) {
        for (HostFileSystemVolumeFileSystemType c: HostFileSystemVolumeFileSystemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
