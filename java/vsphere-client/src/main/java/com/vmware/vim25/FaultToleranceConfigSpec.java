
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultToleranceConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultToleranceConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="metaDataPath" type="{urn:vim25}FaultToleranceMetaSpec" minOccurs="0"/>
 *         &lt;element name="secondaryVmSpec" type="{urn:vim25}FaultToleranceVMConfigSpec" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultToleranceConfigSpec", propOrder = {
    "metaDataPath",
    "secondaryVmSpec"
})
public class FaultToleranceConfigSpec
    extends DynamicData
{

    protected FaultToleranceMetaSpec metaDataPath;
    protected FaultToleranceVMConfigSpec secondaryVmSpec;

    /**
     * Gets the value of the metaDataPath property.
     * 
     * @return
     *     possible object is
     *     {@link FaultToleranceMetaSpec }
     *     
     */
    public FaultToleranceMetaSpec getMetaDataPath() {
        return metaDataPath;
    }

    /**
     * Sets the value of the metaDataPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultToleranceMetaSpec }
     *     
     */
    public void setMetaDataPath(FaultToleranceMetaSpec value) {
        this.metaDataPath = value;
    }

    /**
     * Gets the value of the secondaryVmSpec property.
     * 
     * @return
     *     possible object is
     *     {@link FaultToleranceVMConfigSpec }
     *     
     */
    public FaultToleranceVMConfigSpec getSecondaryVmSpec() {
        return secondaryVmSpec;
    }

    /**
     * Sets the value of the secondaryVmSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultToleranceVMConfigSpec }
     *     
     */
    public void setSecondaryVmSpec(FaultToleranceVMConfigSpec value) {
        this.secondaryVmSpec = value;
    }

}
