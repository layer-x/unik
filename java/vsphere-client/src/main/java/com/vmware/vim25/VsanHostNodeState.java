
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostNodeState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VsanHostNodeState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="error"/>
 *     &lt;enumeration value="disabled"/>
 *     &lt;enumeration value="agent"/>
 *     &lt;enumeration value="master"/>
 *     &lt;enumeration value="backup"/>
 *     &lt;enumeration value="starting"/>
 *     &lt;enumeration value="stopping"/>
 *     &lt;enumeration value="enteringMaintenanceMode"/>
 *     &lt;enumeration value="exitingMaintenanceMode"/>
 *     &lt;enumeration value="decommissioning"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VsanHostNodeState")
@XmlEnum
public enum VsanHostNodeState {

    @XmlEnumValue("error")
    ERROR("error"),
    @XmlEnumValue("disabled")
    DISABLED("disabled"),
    @XmlEnumValue("agent")
    AGENT("agent"),
    @XmlEnumValue("master")
    MASTER("master"),
    @XmlEnumValue("backup")
    BACKUP("backup"),
    @XmlEnumValue("starting")
    STARTING("starting"),
    @XmlEnumValue("stopping")
    STOPPING("stopping"),
    @XmlEnumValue("enteringMaintenanceMode")
    ENTERING_MAINTENANCE_MODE("enteringMaintenanceMode"),
    @XmlEnumValue("exitingMaintenanceMode")
    EXITING_MAINTENANCE_MODE("exitingMaintenanceMode"),
    @XmlEnumValue("decommissioning")
    DECOMMISSIONING("decommissioning");
    private final String value;

    VsanHostNodeState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VsanHostNodeState fromValue(String v) {
        for (VsanHostNodeState c: VsanHostNodeState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
