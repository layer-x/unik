
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineVMCIDeviceAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VirtualMachineVMCIDeviceAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="allow"/>
 *     &lt;enumeration value="deny"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VirtualMachineVMCIDeviceAction")
@XmlEnum
public enum VirtualMachineVMCIDeviceAction {

    @XmlEnumValue("allow")
    ALLOW("allow"),
    @XmlEnumValue("deny")
    DENY("deny");
    private final String value;

    VirtualMachineVMCIDeviceAction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VirtualMachineVMCIDeviceAction fromValue(String v) {
        for (VirtualMachineVMCIDeviceAction c: VirtualMachineVMCIDeviceAction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
