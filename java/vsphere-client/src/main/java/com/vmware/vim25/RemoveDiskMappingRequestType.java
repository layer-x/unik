
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemoveDiskMappingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemoveDiskMappingRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_this" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="mapping" type="{urn:vim25}VsanHostDiskMapping" maxOccurs="unbounded"/>
 *         &lt;element name="maintenanceSpec" type="{urn:vim25}HostMaintenanceSpec" minOccurs="0"/>
 *         &lt;element name="timeout" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveDiskMappingRequestType", propOrder = {
    "_this",
    "mapping",
    "maintenanceSpec",
    "timeout"
})
public class RemoveDiskMappingRequestType {

    @XmlElement(required = true)
    protected ManagedObjectReference _this;
    @XmlElement(required = true)
    protected List<VsanHostDiskMapping> mapping;
    protected HostMaintenanceSpec maintenanceSpec;
    protected Integer timeout;

    /**
     * Gets the value of the this property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getThis() {
        return _this;
    }

    /**
     * Sets the value of the this property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setThis(ManagedObjectReference value) {
        this._this = value;
    }

    /**
     * Gets the value of the mapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanHostDiskMapping }
     * 
     * 
     */
    public List<VsanHostDiskMapping> getMapping() {
        if (mapping == null) {
            mapping = new ArrayList<VsanHostDiskMapping>();
        }
        return this.mapping;
    }

    /**
     * Gets the value of the maintenanceSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostMaintenanceSpec }
     *     
     */
    public HostMaintenanceSpec getMaintenanceSpec() {
        return maintenanceSpec;
    }

    /**
     * Sets the value of the maintenanceSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostMaintenanceSpec }
     *     
     */
    public void setMaintenanceSpec(HostMaintenanceSpec value) {
        this.maintenanceSpec = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeout(Integer value) {
        this.timeout = value;
    }

}
