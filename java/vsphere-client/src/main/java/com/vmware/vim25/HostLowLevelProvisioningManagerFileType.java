
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostLowLevelProvisioningManagerFileType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostLowLevelProvisioningManagerFileType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="File"/>
 *     &lt;enumeration value="VirtualDisk"/>
 *     &lt;enumeration value="Directory"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostLowLevelProvisioningManagerFileType")
@XmlEnum
public enum HostLowLevelProvisioningManagerFileType {

    @XmlEnumValue("File")
    FILE("File"),
    @XmlEnumValue("VirtualDisk")
    VIRTUAL_DISK("VirtualDisk"),
    @XmlEnumValue("Directory")
    DIRECTORY("Directory");
    private final String value;

    HostLowLevelProvisioningManagerFileType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostLowLevelProvisioningManagerFileType fromValue(String v) {
        for (HostLowLevelProvisioningManagerFileType c: HostLowLevelProvisioningManagerFileType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
