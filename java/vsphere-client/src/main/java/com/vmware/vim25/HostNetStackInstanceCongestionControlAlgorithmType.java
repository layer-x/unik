
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostNetStackInstanceCongestionControlAlgorithmType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostNetStackInstanceCongestionControlAlgorithmType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="newreno"/>
 *     &lt;enumeration value="cubic"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostNetStackInstanceCongestionControlAlgorithmType")
@XmlEnum
public enum HostNetStackInstanceCongestionControlAlgorithmType {

    @XmlEnumValue("newreno")
    NEWRENO("newreno"),
    @XmlEnumValue("cubic")
    CUBIC("cubic");
    private final String value;

    HostNetStackInstanceCongestionControlAlgorithmType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostNetStackInstanceCongestionControlAlgorithmType fromValue(String v) {
        for (HostNetStackInstanceCongestionControlAlgorithmType c: HostNetStackInstanceCongestionControlAlgorithmType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
