
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostDiskMapInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostDiskMapInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="mapping" type="{urn:vim25}VsanHostDiskMapping"/>
 *         &lt;element name="mounted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostDiskMapInfo", propOrder = {
    "mapping",
    "mounted"
})
public class VsanHostDiskMapInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected VsanHostDiskMapping mapping;
    protected boolean mounted;

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public VsanHostDiskMapping getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostDiskMapping }
     *     
     */
    public void setMapping(VsanHostDiskMapping value) {
        this.mapping = value;
    }

    /**
     * Gets the value of the mounted property.
     * 
     */
    public boolean isMounted() {
        return mounted;
    }

    /**
     * Sets the value of the mounted property.
     * 
     */
    public void setMounted(boolean value) {
        this.mounted = value;
    }

}
