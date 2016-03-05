
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlacementRankResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlacementRankResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="candidate" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="reservedSpaceMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="usedSpaceMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="totalSpaceMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="utilization" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="faults" type="{urn:vim25}LocalizedMethodFault" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlacementRankResult", propOrder = {
    "key",
    "candidate",
    "reservedSpaceMB",
    "usedSpaceMB",
    "totalSpaceMB",
    "utilization",
    "faults"
})
public class PlacementRankResult
    extends DynamicData
{

    @XmlElement(required = true)
    protected String key;
    @XmlElement(required = true)
    protected ManagedObjectReference candidate;
    protected long reservedSpaceMB;
    protected long usedSpaceMB;
    protected long totalSpaceMB;
    protected double utilization;
    protected List<LocalizedMethodFault> faults;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the candidate property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getCandidate() {
        return candidate;
    }

    /**
     * Sets the value of the candidate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setCandidate(ManagedObjectReference value) {
        this.candidate = value;
    }

    /**
     * Gets the value of the reservedSpaceMB property.
     * 
     */
    public long getReservedSpaceMB() {
        return reservedSpaceMB;
    }

    /**
     * Sets the value of the reservedSpaceMB property.
     * 
     */
    public void setReservedSpaceMB(long value) {
        this.reservedSpaceMB = value;
    }

    /**
     * Gets the value of the usedSpaceMB property.
     * 
     */
    public long getUsedSpaceMB() {
        return usedSpaceMB;
    }

    /**
     * Sets the value of the usedSpaceMB property.
     * 
     */
    public void setUsedSpaceMB(long value) {
        this.usedSpaceMB = value;
    }

    /**
     * Gets the value of the totalSpaceMB property.
     * 
     */
    public long getTotalSpaceMB() {
        return totalSpaceMB;
    }

    /**
     * Sets the value of the totalSpaceMB property.
     * 
     */
    public void setTotalSpaceMB(long value) {
        this.totalSpaceMB = value;
    }

    /**
     * Gets the value of the utilization property.
     * 
     */
    public double getUtilization() {
        return utilization;
    }

    /**
     * Sets the value of the utilization property.
     * 
     */
    public void setUtilization(double value) {
        this.utilization = value;
    }

    /**
     * Gets the value of the faults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the faults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFaults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizedMethodFault }
     * 
     * 
     */
    public List<LocalizedMethodFault> getFaults() {
        if (faults == null) {
            faults = new ArrayList<LocalizedMethodFault>();
        }
        return this.faults;
    }

}
