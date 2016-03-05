
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplicationVmInProgressFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplicationVmInProgressFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}ReplicationVmFault">
 *       &lt;sequence>
 *         &lt;element name="requestedActivity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inProgressActivity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReplicationVmInProgressFault", propOrder = {
    "requestedActivity",
    "inProgressActivity"
})
public class ReplicationVmInProgressFault
    extends ReplicationVmFault
{

    @XmlElement(required = true)
    protected String requestedActivity;
    @XmlElement(required = true)
    protected String inProgressActivity;

    /**
     * Gets the value of the requestedActivity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedActivity() {
        return requestedActivity;
    }

    /**
     * Sets the value of the requestedActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedActivity(String value) {
        this.requestedActivity = value;
    }

    /**
     * Gets the value of the inProgressActivity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInProgressActivity() {
        return inProgressActivity;
    }

    /**
     * Sets the value of the inProgressActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInProgressActivity(String value) {
        this.inProgressActivity = value;
    }

}
