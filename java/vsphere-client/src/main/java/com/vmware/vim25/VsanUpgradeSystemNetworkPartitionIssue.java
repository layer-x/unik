
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanUpgradeSystemNetworkPartitionIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanUpgradeSystemNetworkPartitionIssue">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VsanUpgradeSystemPreflightCheckIssue">
 *       &lt;sequence>
 *         &lt;element name="partitions" type="{urn:vim25}VsanUpgradeSystemNetworkPartitionInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanUpgradeSystemNetworkPartitionIssue", propOrder = {
    "partitions"
})
public class VsanUpgradeSystemNetworkPartitionIssue
    extends VsanUpgradeSystemPreflightCheckIssue
{

    @XmlElement(required = true)
    protected List<VsanUpgradeSystemNetworkPartitionInfo> partitions;

    /**
     * Gets the value of the partitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VsanUpgradeSystemNetworkPartitionInfo }
     * 
     * 
     */
    public List<VsanUpgradeSystemNetworkPartitionInfo> getPartitions() {
        if (partitions == null) {
            partitions = new ArrayList<VsanUpgradeSystemNetworkPartitionInfo>();
        }
        return this.partitions;
    }

}
