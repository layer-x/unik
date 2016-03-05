
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostDiskMapResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostDiskMapResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="mapping" type="{urn:vim25}VsanHostDiskMapping"/>
 *         &lt;element name="diskResult" type="{urn:vim25}VsanHostDiskResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="error" type="{urn:vim25}LocalizedMethodFault" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostDiskMapResult", propOrder = {
    "mapping",
    "diskResult",
    "error"
})
public class VsanHostDiskMapResult
    extends DynamicData
{

    @XmlElement(required = true)
    protected VsanHostDiskMapping mapping;
    protected List<VsanHostDiskResult> diskResult;
    protected LocalizedMethodFault error;

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
     * Gets the value of the diskResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diskResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiskResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskResult }
     * 
     * 
     */
    public List<VsanHostDiskResult> getDiskResult() {
        if (diskResult == null) {
            diskResult = new ArrayList<VsanHostDiskResult>();
        }
        return this.diskResult;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public LocalizedMethodFault getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public void setError(LocalizedMethodFault value) {
        this.error = value;
    }

}
