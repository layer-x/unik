
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterVmComponentProtectionSettingsStorageVmReaction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClusterVmComponentProtectionSettingsStorageVmReaction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="disabled"/>
 *     &lt;enumeration value="warning"/>
 *     &lt;enumeration value="restartConservative"/>
 *     &lt;enumeration value="restartAggressive"/>
 *     &lt;enumeration value="clusterDefault"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClusterVmComponentProtectionSettingsStorageVmReaction")
@XmlEnum
public enum ClusterVmComponentProtectionSettingsStorageVmReaction {

    @XmlEnumValue("disabled")
    DISABLED("disabled"),
    @XmlEnumValue("warning")
    WARNING("warning"),
    @XmlEnumValue("restartConservative")
    RESTART_CONSERVATIVE("restartConservative"),
    @XmlEnumValue("restartAggressive")
    RESTART_AGGRESSIVE("restartAggressive"),
    @XmlEnumValue("clusterDefault")
    CLUSTER_DEFAULT("clusterDefault");
    private final String value;

    ClusterVmComponentProtectionSettingsStorageVmReaction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClusterVmComponentProtectionSettingsStorageVmReaction fromValue(String v) {
        for (ClusterVmComponentProtectionSettingsStorageVmReaction c: ClusterVmComponentProtectionSettingsStorageVmReaction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
