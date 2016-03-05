
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VsanHostClusterStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VsanHostClusterStatus">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nodeUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="health" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nodeState" type="{urn:vim25}VsanHostClusterStatusState"/>
 *         &lt;element name="memberUuid" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VsanHostClusterStatus", propOrder = {
    "uuid",
    "nodeUuid",
    "health",
    "nodeState",
    "memberUuid"
})
public class VsanHostClusterStatus
    extends DynamicData
{

    protected String uuid;
    protected String nodeUuid;
    @XmlElement(required = true)
    protected String health;
    @XmlElement(required = true)
    protected VsanHostClusterStatusState nodeState;
    protected List<String> memberUuid;

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the nodeUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeUuid() {
        return nodeUuid;
    }

    /**
     * Sets the value of the nodeUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeUuid(String value) {
        this.nodeUuid = value;
    }

    /**
     * Gets the value of the health property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealth() {
        return health;
    }

    /**
     * Sets the value of the health property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealth(String value) {
        this.health = value;
    }

    /**
     * Gets the value of the nodeState property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostClusterStatusState }
     *     
     */
    public VsanHostClusterStatusState getNodeState() {
        return nodeState;
    }

    /**
     * Sets the value of the nodeState property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostClusterStatusState }
     *     
     */
    public void setNodeState(VsanHostClusterStatusState value) {
        this.nodeState = value;
    }

    /**
     * Gets the value of the memberUuid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the memberUuid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemberUuid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMemberUuid() {
        if (memberUuid == null) {
            memberUuid = new ArrayList<String>();
        }
        return this.memberUuid;
    }

}
