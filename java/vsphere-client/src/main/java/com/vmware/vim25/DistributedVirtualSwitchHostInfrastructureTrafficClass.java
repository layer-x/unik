
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistributedVirtualSwitchHostInfrastructureTrafficClass.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DistributedVirtualSwitchHostInfrastructureTrafficClass">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="management"/>
 *     &lt;enumeration value="faultTolerance"/>
 *     &lt;enumeration value="vmotion"/>
 *     &lt;enumeration value="virtualMachine"/>
 *     &lt;enumeration value="iSCSI"/>
 *     &lt;enumeration value="nfs"/>
 *     &lt;enumeration value="hbr"/>
 *     &lt;enumeration value="vsan"/>
 *     &lt;enumeration value="vdp"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DistributedVirtualSwitchHostInfrastructureTrafficClass")
@XmlEnum
public enum DistributedVirtualSwitchHostInfrastructureTrafficClass {

    @XmlEnumValue("management")
    MANAGEMENT("management"),
    @XmlEnumValue("faultTolerance")
    FAULT_TOLERANCE("faultTolerance"),
    @XmlEnumValue("vmotion")
    VMOTION("vmotion"),
    @XmlEnumValue("virtualMachine")
    VIRTUAL_MACHINE("virtualMachine"),
    @XmlEnumValue("iSCSI")
    I_SCSI("iSCSI"),
    @XmlEnumValue("nfs")
    NFS("nfs"),
    @XmlEnumValue("hbr")
    HBR("hbr"),
    @XmlEnumValue("vsan")
    VSAN("vsan"),
    @XmlEnumValue("vdp")
    VDP("vdp");
    private final String value;

    DistributedVirtualSwitchHostInfrastructureTrafficClass(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistributedVirtualSwitchHostInfrastructureTrafficClass fromValue(String v) {
        for (DistributedVirtualSwitchHostInfrastructureTrafficClass c: DistributedVirtualSwitchHostInfrastructureTrafficClass.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
