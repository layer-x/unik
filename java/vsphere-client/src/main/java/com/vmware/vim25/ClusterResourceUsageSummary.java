
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterResourceUsageSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterResourceUsageSummary">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="cpuUsedMHz" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cpuCapacityMHz" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="memUsedMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="memCapacityMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="storageUsedMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="storageCapacityMB" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterResourceUsageSummary", propOrder = {
    "cpuUsedMHz",
    "cpuCapacityMHz",
    "memUsedMB",
    "memCapacityMB",
    "storageUsedMB",
    "storageCapacityMB"
})
public class ClusterResourceUsageSummary
    extends DynamicData
{

    protected int cpuUsedMHz;
    protected int cpuCapacityMHz;
    protected int memUsedMB;
    protected int memCapacityMB;
    protected long storageUsedMB;
    protected long storageCapacityMB;

    /**
     * Gets the value of the cpuUsedMHz property.
     * 
     */
    public int getCpuUsedMHz() {
        return cpuUsedMHz;
    }

    /**
     * Sets the value of the cpuUsedMHz property.
     * 
     */
    public void setCpuUsedMHz(int value) {
        this.cpuUsedMHz = value;
    }

    /**
     * Gets the value of the cpuCapacityMHz property.
     * 
     */
    public int getCpuCapacityMHz() {
        return cpuCapacityMHz;
    }

    /**
     * Sets the value of the cpuCapacityMHz property.
     * 
     */
    public void setCpuCapacityMHz(int value) {
        this.cpuCapacityMHz = value;
    }

    /**
     * Gets the value of the memUsedMB property.
     * 
     */
    public int getMemUsedMB() {
        return memUsedMB;
    }

    /**
     * Sets the value of the memUsedMB property.
     * 
     */
    public void setMemUsedMB(int value) {
        this.memUsedMB = value;
    }

    /**
     * Gets the value of the memCapacityMB property.
     * 
     */
    public int getMemCapacityMB() {
        return memCapacityMB;
    }

    /**
     * Sets the value of the memCapacityMB property.
     * 
     */
    public void setMemCapacityMB(int value) {
        this.memCapacityMB = value;
    }

    /**
     * Gets the value of the storageUsedMB property.
     * 
     */
    public long getStorageUsedMB() {
        return storageUsedMB;
    }

    /**
     * Sets the value of the storageUsedMB property.
     * 
     */
    public void setStorageUsedMB(long value) {
        this.storageUsedMB = value;
    }

    /**
     * Gets the value of the storageCapacityMB property.
     * 
     */
    public long getStorageCapacityMB() {
        return storageCapacityMB;
    }

    /**
     * Sets the value of the storageCapacityMB property.
     * 
     */
    public void setStorageCapacityMB(long value) {
        this.storageCapacityMB = value;
    }

}
