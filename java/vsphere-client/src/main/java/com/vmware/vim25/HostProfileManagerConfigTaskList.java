
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProfileManagerConfigTaskList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostProfileManagerConfigTaskList">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="configSpec" type="{urn:vim25}HostConfigSpec" minOccurs="0"/>
 *         &lt;element name="taskDescription" type="{urn:vim25}LocalizableMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="taskListRequirement" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostProfileManagerConfigTaskList", propOrder = {
    "configSpec",
    "taskDescription",
    "taskListRequirement"
})
public class HostProfileManagerConfigTaskList
    extends DynamicData
{

    protected HostConfigSpec configSpec;
    protected List<LocalizableMessage> taskDescription;
    protected List<String> taskListRequirement;

    /**
     * Gets the value of the configSpec property.
     * 
     * @return
     *     possible object is
     *     {@link HostConfigSpec }
     *     
     */
    public HostConfigSpec getConfigSpec() {
        return configSpec;
    }

    /**
     * Sets the value of the configSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostConfigSpec }
     *     
     */
    public void setConfigSpec(HostConfigSpec value) {
        this.configSpec = value;
    }

    /**
     * Gets the value of the taskDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizableMessage }
     * 
     * 
     */
    public List<LocalizableMessage> getTaskDescription() {
        if (taskDescription == null) {
            taskDescription = new ArrayList<LocalizableMessage>();
        }
        return this.taskDescription;
    }

    /**
     * Gets the value of the taskListRequirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskListRequirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskListRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTaskListRequirement() {
        if (taskListRequirement == null) {
            taskListRequirement = new ArrayList<String>();
        }
        return this.taskListRequirement;
    }

}
