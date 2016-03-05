
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlacementAffinityRuleRuleScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlacementAffinityRuleRuleScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cluster"/>
 *     &lt;enumeration value="host"/>
 *     &lt;enumeration value="storagePod"/>
 *     &lt;enumeration value="datastore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PlacementAffinityRuleRuleScope")
@XmlEnum
public enum PlacementAffinityRuleRuleScope {

    @XmlEnumValue("cluster")
    CLUSTER("cluster"),
    @XmlEnumValue("host")
    HOST("host"),
    @XmlEnumValue("storagePod")
    STORAGE_POD("storagePod"),
    @XmlEnumValue("datastore")
    DATASTORE("datastore");
    private final String value;

    PlacementAffinityRuleRuleScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlacementAffinityRuleRuleScope fromValue(String v) {
        for (PlacementAffinityRuleRuleScope c: PlacementAffinityRuleRuleScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
