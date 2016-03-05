
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemUpgradeHistoryDiskGroupOp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemUpgradeHistoryDiskGroupOp">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VsanUpgradeSystemUpgradeHistoryItem">
 *       &lt;sequence>
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="diskMapping" type="{urn:vim25}VsanHostDiskMapping"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemUpgradeHistoryDiskGroupOp", propOrder = {
    "operation",
    "diskMapping"
})
public class VsanUpgradeSystemUpgradeHistoryDiskGroupOp
    extends VsanUpgradeSystemUpgradeHistoryItem
{

    @XmlElement(required = true)
    protected String operation;
    @XmlElement(required = true)
    protected VsanHostDiskMapping diskMapping;

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the diskMapping property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public VsanHostDiskMapping getDiskMapping() {
        return diskMapping;
    }

    /**
     * Sets the value of the diskMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public void setDiskMapping(VsanHostDiskMapping value) {
        this.diskMapping = value;
    }

}
