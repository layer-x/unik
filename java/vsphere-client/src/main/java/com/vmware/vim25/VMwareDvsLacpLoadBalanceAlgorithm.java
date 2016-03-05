
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VMwareDvsLacpLoadBalanceAlgorithm.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VMwareDvsLacpLoadBalanceAlgorithm">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="srcMac"/>
 *     &lt;enumeration value="destMac"/>
 *     &lt;enumeration value="srcDestMac"/>
 *     &lt;enumeration value="destIpVlan"/>
 *     &lt;enumeration value="srcIpVlan"/>
 *     &lt;enumeration value="srcDestIpVlan"/>
 *     &lt;enumeration value="destTcpUdpPort"/>
 *     &lt;enumeration value="srcTcpUdpPort"/>
 *     &lt;enumeration value="srcDestTcpUdpPort"/>
 *     &lt;enumeration value="destIpTcpUdpPort"/>
 *     &lt;enumeration value="srcIpTcpUdpPort"/>
 *     &lt;enumeration value="srcDestIpTcpUdpPort"/>
 *     &lt;enumeration value="destIpTcpUdpPortVlan"/>
 *     &lt;enumeration value="srcIpTcpUdpPortVlan"/>
 *     &lt;enumeration value="srcDestIpTcpUdpPortVlan"/>
 *     &lt;enumeration value="destIp"/>
 *     &lt;enumeration value="srcIp"/>
 *     &lt;enumeration value="srcDestIp"/>
 *     &lt;enumeration value="vlan"/>
 *     &lt;enumeration value="srcPortId"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VMwareDvsLacpLoadBalanceAlgorithm")
@XmlEnum
public enum VMwareDvsLacpLoadBalanceAlgorithm {

    @XmlEnumValue("srcMac")
    SRC_MAC("srcMac"),
    @XmlEnumValue("destMac")
    DEST_MAC("destMac"),
    @XmlEnumValue("srcDestMac")
    SRC_DEST_MAC("srcDestMac"),
    @XmlEnumValue("destIpVlan")
    DEST_IP_VLAN("destIpVlan"),
    @XmlEnumValue("srcIpVlan")
    SRC_IP_VLAN("srcIpVlan"),
    @XmlEnumValue("srcDestIpVlan")
    SRC_DEST_IP_VLAN("srcDestIpVlan"),
    @XmlEnumValue("destTcpUdpPort")
    DEST_TCP_UDP_PORT("destTcpUdpPort"),
    @XmlEnumValue("srcTcpUdpPort")
    SRC_TCP_UDP_PORT("srcTcpUdpPort"),
    @XmlEnumValue("srcDestTcpUdpPort")
    SRC_DEST_TCP_UDP_PORT("srcDestTcpUdpPort"),
    @XmlEnumValue("destIpTcpUdpPort")
    DEST_IP_TCP_UDP_PORT("destIpTcpUdpPort"),
    @XmlEnumValue("srcIpTcpUdpPort")
    SRC_IP_TCP_UDP_PORT("srcIpTcpUdpPort"),
    @XmlEnumValue("srcDestIpTcpUdpPort")
    SRC_DEST_IP_TCP_UDP_PORT("srcDestIpTcpUdpPort"),
    @XmlEnumValue("destIpTcpUdpPortVlan")
    DEST_IP_TCP_UDP_PORT_VLAN("destIpTcpUdpPortVlan"),
    @XmlEnumValue("srcIpTcpUdpPortVlan")
    SRC_IP_TCP_UDP_PORT_VLAN("srcIpTcpUdpPortVlan"),
    @XmlEnumValue("srcDestIpTcpUdpPortVlan")
    SRC_DEST_IP_TCP_UDP_PORT_VLAN("srcDestIpTcpUdpPortVlan"),
    @XmlEnumValue("destIp")
    DEST_IP("destIp"),
    @XmlEnumValue("srcIp")
    SRC_IP("srcIp"),
    @XmlEnumValue("srcDestIp")
    SRC_DEST_IP("srcDestIp"),
    @XmlEnumValue("vlan")
    VLAN("vlan"),
    @XmlEnumValue("srcPortId")
    SRC_PORT_ID("srcPortId");
    private final String value;

    VMwareDvsLacpLoadBalanceAlgorithm(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VMwareDvsLacpLoadBalanceAlgorithm fromValue(String v) {
        for (VMwareDvsLacpLoadBalanceAlgorithm c: VMwareDvsLacpLoadBalanceAlgorithm.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
