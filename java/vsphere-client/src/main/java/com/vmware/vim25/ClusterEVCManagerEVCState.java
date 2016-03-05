
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterEVCManagerEVCState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterEVCManagerEVCState">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="supportedEVCMode" type="{urn:vim25}EVCMode" maxOccurs="unbounded"/>
 *         &lt;element name="currentEVCModeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="guaranteedCPUFeatures" type="{urn:vim25}HostCpuIdInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="featureCapability" type="{urn:vim25}HostFeatureCapability" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="featureMask" type="{urn:vim25}HostFeatureMask" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="featureRequirement" type="{urn:vim25}VirtualMachineFeatureRequirement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterEVCManagerEVCState", propOrder = {
    "supportedEVCMode",
    "currentEVCModeKey",
    "guaranteedCPUFeatures",
    "featureCapability",
    "featureMask",
    "featureRequirement"
})
public class ClusterEVCManagerEVCState
    extends DynamicData
{

    @XmlElement(required = true)
    protected List<EVCMode> supportedEVCMode;
    protected String currentEVCModeKey;
    protected List<HostCpuIdInfo> guaranteedCPUFeatures;
    protected List<HostFeatureCapability> featureCapability;
    protected List<HostFeatureMask> featureMask;
    protected List<VirtualMachineFeatureRequirement> featureRequirement;

    /**
     * Gets the value of the supportedEVCMode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedEVCMode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedEVCMode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVCMode }
     * 
     * 
     */
    public List<EVCMode> getSupportedEVCMode() {
        if (supportedEVCMode == null) {
            supportedEVCMode = new ArrayList<EVCMode>();
        }
        return this.supportedEVCMode;
    }

    /**
     * Gets the value of the currentEVCModeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEVCModeKey() {
        return currentEVCModeKey;
    }

    /**
     * Sets the value of the currentEVCModeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEVCModeKey(String value) {
        this.currentEVCModeKey = value;
    }

    /**
     * Gets the value of the guaranteedCPUFeatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guaranteedCPUFeatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuaranteedCPUFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostCpuIdInfo }
     * 
     * 
     */
    public List<HostCpuIdInfo> getGuaranteedCPUFeatures() {
        if (guaranteedCPUFeatures == null) {
            guaranteedCPUFeatures = new ArrayList<HostCpuIdInfo>();
        }
        return this.guaranteedCPUFeatures;
    }

    /**
     * Gets the value of the featureCapability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureCapability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureCapability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostFeatureCapability }
     * 
     * 
     */
    public List<HostFeatureCapability> getFeatureCapability() {
        if (featureCapability == null) {
            featureCapability = new ArrayList<HostFeatureCapability>();
        }
        return this.featureCapability;
    }

    /**
     * Gets the value of the featureMask property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureMask property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureMask().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostFeatureMask }
     * 
     * 
     */
    public List<HostFeatureMask> getFeatureMask() {
        if (featureMask == null) {
            featureMask = new ArrayList<HostFeatureMask>();
        }
        return this.featureMask;
    }

    /**
     * Gets the value of the featureRequirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureRequirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualMachineFeatureRequirement }
     * 
     * 
     */
    public List<VirtualMachineFeatureRequirement> getFeatureRequirement() {
        if (featureRequirement == null) {
            featureRequirement = new ArrayList<VirtualMachineFeatureRequirement>();
        }
        return this.featureRequirement;
    }

}
