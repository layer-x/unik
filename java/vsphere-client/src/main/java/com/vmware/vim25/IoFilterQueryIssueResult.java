
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IoFilterQueryIssueResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IoFilterQueryIssueResult">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="opType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostIssue" type="{urn:vim25}IoFilterHostIssue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IoFilterQueryIssueResult", propOrder = {
    "opType",
    "hostIssue"
})
public class IoFilterQueryIssueResult
    extends DynamicData
{

    @XmlElement(required = true)
    protected String opType;
    protected List<IoFilterHostIssue> hostIssue;

    /**
     * Gets the value of the opType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpType() {
        return opType;
    }

    /**
     * Sets the value of the opType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpType(String value) {
        this.opType = value;
    }

    /**
     * Gets the value of the hostIssue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostIssue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostIssue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IoFilterHostIssue }
     * 
     * 
     */
    public List<IoFilterHostIssue> getHostIssue() {
        if (hostIssue == null) {
            hostIssue = new ArrayList<IoFilterHostIssue>();
        }
        return this.hostIssue;
    }

}
