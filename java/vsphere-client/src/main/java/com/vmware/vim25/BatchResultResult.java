
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchResultResult.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BatchResultResult">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="success"/>
 *     &lt;enumeration value="fail"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BatchResultResult")
@XmlEnum
public enum BatchResultResult {

    @XmlEnumValue("success")
    SUCCESS("success"),
    @XmlEnumValue("fail")
    FAIL("fail");
    private final String value;

    BatchResultResult(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BatchResultResult fromValue(String v) {
        for (BatchResultResult c: BatchResultResult.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
