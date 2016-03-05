
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineVMCIDeviceDirection.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualMachineVMCIDeviceDirection">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="guest"/>
 *     &lt;enumeration value="host"/>
 *     &lt;enumeration value="anyDirection"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualMachineVMCIDeviceDirection")
@XmlEnum
public enum VirtualMachineVMCIDeviceDirection {

    @XmlEnumValue("guest")
    GUEST("guest"),
    @XmlEnumValue("host")
    HOST("host"),
    @XmlEnumValue("anyDirection")
    ANY_DIRECTION("anyDirection");
    private final String value;

    VirtualMachineVMCIDeviceDirection(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualMachineVMCIDeviceDirection fromValue(String v) {
        for (VirtualMachineVMCIDeviceDirection c: VirtualMachineVMCIDeviceDirection.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
