
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplicationConfigSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplicationConfigSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="generation" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vmReplicationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rpo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="quiesceGuestEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="paused" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="oppUpdatesEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="netCompressionEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="disk" type="{urn:vim25}ReplicationInfoDiskSettings" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReplicationConfigSpec", propOrder = {
    "generation",
    "vmReplicationId",
    "destination",
    "port",
    "rpo",
    "quiesceGuestEnabled",
    "paused",
    "oppUpdatesEnabled",
    "netCompressionEnabled",
    "disk"
})
public class ReplicationConfigSpec
    extends DynamicData
{

    protected long generation;
    @XmlElement(required = true)
    protected String vmReplicationId;
    @XmlElement(required = true)
    protected String destination;
    protected int port;
    protected long rpo;
    protected boolean quiesceGuestEnabled;
    protected boolean paused;
    protected boolean oppUpdatesEnabled;
    protected Boolean netCompressionEnabled;
    protected List<ReplicationInfoDiskSettings> disk;

    /**
     * Gets the value of the generation property.
     * 
     */
    public long getGeneration() {
        return generation;
    }

    /**
     * Sets the value of the generation property.
     * 
     */
    public void setGeneration(long value) {
        this.generation = value;
    }

    /**
     * Gets the value of the vmReplicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmReplicationId() {
        return vmReplicationId;
    }

    /**
     * Sets the value of the vmReplicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmReplicationId(String value) {
        this.vmReplicationId = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the port property.
     * 
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     * 
     */
    public void setPort(int value) {
        this.port = value;
    }

    /**
     * Gets the value of the rpo property.
     * 
     */
    public long getRpo() {
        return rpo;
    }

    /**
     * Sets the value of the rpo property.
     * 
     */
    public void setRpo(long value) {
        this.rpo = value;
    }

    /**
     * Gets the value of the quiesceGuestEnabled property.
     * 
     */
    public boolean isQuiesceGuestEnabled() {
        return quiesceGuestEnabled;
    }

    /**
     * Sets the value of the quiesceGuestEnabled property.
     * 
     */
    public void setQuiesceGuestEnabled(boolean value) {
        this.quiesceGuestEnabled = value;
    }

    /**
     * Gets the value of the paused property.
     * 
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the value of the paused property.
     * 
     */
    public void setPaused(boolean value) {
        this.paused = value;
    }

    /**
     * Gets the value of the oppUpdatesEnabled property.
     * 
     */
    public boolean isOppUpdatesEnabled() {
        return oppUpdatesEnabled;
    }

    /**
     * Sets the value of the oppUpdatesEnabled property.
     * 
     */
    public void setOppUpdatesEnabled(boolean value) {
        this.oppUpdatesEnabled = value;
    }

    /**
     * Gets the value of the netCompressionEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNetCompressionEnabled() {
        return netCompressionEnabled;
    }

    /**
     * Sets the value of the netCompressionEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNetCompressionEnabled(Boolean value) {
        this.netCompressionEnabled = value;
    }

    /**
     * Gets the value of the disk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisk().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReplicationInfoDiskSettings }
     * 
     * 
     */
    public List<ReplicationInfoDiskSettings> getDisk() {
        if (disk == null) {
            disk = new ArrayList<ReplicationInfoDiskSettings>();
        }
        return this.disk;
    }

}
