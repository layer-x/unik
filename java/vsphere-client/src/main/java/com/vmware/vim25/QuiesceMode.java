
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuiesceMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QuiesceMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="application"/>
 *     &lt;enumeration value="filesystem"/>
 *     &lt;enumeration value="none"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QuiesceMode")
@XmlEnum
public enum QuiesceMode {

    @XmlEnumValue("application")
    APPLICATION("application"),
    @XmlEnumValue("filesystem")
    FILESYSTEM("filesystem"),
    @XmlEnumValue("none")
    NONE("none");
    private final String value;

    QuiesceMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QuiesceMode fromValue(String v) {
        for (QuiesceMode c: QuiesceMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
