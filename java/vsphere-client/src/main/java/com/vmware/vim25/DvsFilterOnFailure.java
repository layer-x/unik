
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsFilterOnFailure.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DvsFilterOnFailure">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="failOpen"/>
 *     &lt;enumeration value="failClosed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DvsFilterOnFailure")
@XmlEnum
public enum DvsFilterOnFailure {

    @XmlEnumValue("failOpen")
    FAIL_OPEN("failOpen"),
    @XmlEnumValue("failClosed")
    FAIL_CLOSED("failClosed");
    private final String value;

    DvsFilterOnFailure(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DvsFilterOnFailure fromValue(String v) {
        for (DvsFilterOnFailure c: DvsFilterOnFailure.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
