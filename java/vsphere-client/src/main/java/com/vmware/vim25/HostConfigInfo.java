
package com.vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostConfigInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostConfigInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="host" type="{urn:vim25}ManagedObjectReference"/>
 *         &lt;element name="product" type="{urn:vim25}AboutInfo"/>
 *         &lt;element name="hyperThread" type="{urn:vim25}HostHyperThreadScheduleInfo" minOccurs="0"/>
 *         &lt;element name="consoleReservation" type="{urn:vim25}ServiceConsoleReservationInfo" minOccurs="0"/>
 *         &lt;element name="virtualMachineReservation" type="{urn:vim25}VirtualMachineMemoryReservationInfo" minOccurs="0"/>
 *         &lt;element name="storageDevice" type="{urn:vim25}HostStorageDeviceInfo" minOccurs="0"/>
 *         &lt;element name="multipathState" type="{urn:vim25}HostMultipathStateInfo" minOccurs="0"/>
 *         &lt;element name="fileSystemVolume" type="{urn:vim25}HostFileSystemVolumeInfo" minOccurs="0"/>
 *         &lt;element name="systemFile" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="network" type="{urn:vim25}HostNetworkInfo" minOccurs="0"/>
 *         &lt;element name="vmotion" type="{urn:vim25}HostVMotionInfo" minOccurs="0"/>
 *         &lt;element name="virtualNicManagerInfo" type="{urn:vim25}HostVirtualNicManagerInfo" minOccurs="0"/>
 *         &lt;element name="capabilities" type="{urn:vim25}HostNetCapabilities" minOccurs="0"/>
 *         &lt;element name="datastoreCapabilities" type="{urn:vim25}HostDatastoreSystemCapabilities" minOccurs="0"/>
 *         &lt;element name="offloadCapabilities" type="{urn:vim25}HostNetOffloadCapabilities" minOccurs="0"/>
 *         &lt;element name="service" type="{urn:vim25}HostServiceInfo" minOccurs="0"/>
 *         &lt;element name="firewall" type="{urn:vim25}HostFirewallInfo" minOccurs="0"/>
 *         &lt;element name="autoStart" type="{urn:vim25}HostAutoStartManagerConfig" minOccurs="0"/>
 *         &lt;element name="activeDiagnosticPartition" type="{urn:vim25}HostDiagnosticPartition" minOccurs="0"/>
 *         &lt;element name="option" type="{urn:vim25}OptionValue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="optionDef" type="{urn:vim25}OptionDef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="datastorePrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localSwapDatastore" type="{urn:vim25}ManagedObjectReference" minOccurs="0"/>
 *         &lt;element name="systemSwapConfiguration" type="{urn:vim25}HostSystemSwapConfiguration" minOccurs="0"/>
 *         &lt;element name="systemResources" type="{urn:vim25}HostSystemResourceInfo" minOccurs="0"/>
 *         &lt;element name="dateTimeInfo" type="{urn:vim25}HostDateTimeInfo" minOccurs="0"/>
 *         &lt;element name="flags" type="{urn:vim25}HostFlagInfo" minOccurs="0"/>
 *         &lt;element name="adminDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lockdownMode" type="{urn:vim25}HostLockdownMode" minOccurs="0"/>
 *         &lt;element name="ipmi" type="{urn:vim25}HostIpmiInfo" minOccurs="0"/>
 *         &lt;element name="sslThumbprintInfo" type="{urn:vim25}HostSslThumbprintInfo" minOccurs="0"/>
 *         &lt;element name="sslThumbprintData" type="{urn:vim25}HostSslThumbprintInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}byte" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pciPassthruInfo" type="{urn:vim25}HostPciPassthruInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authenticationManagerInfo" type="{urn:vim25}HostAuthenticationManagerInfo" minOccurs="0"/>
 *         &lt;element name="featureVersion" type="{urn:vim25}HostFeatureVersionInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="powerSystemCapability" type="{urn:vim25}PowerSystemCapability" minOccurs="0"/>
 *         &lt;element name="powerSystemInfo" type="{urn:vim25}PowerSystemInfo" minOccurs="0"/>
 *         &lt;element name="cacheConfigurationInfo" type="{urn:vim25}HostCacheConfigurationInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wakeOnLanCapable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="featureCapability" type="{urn:vim25}HostFeatureCapability" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="maskedFeatureCapability" type="{urn:vim25}HostFeatureCapability" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vFlashConfigInfo" type="{urn:vim25}HostVFlashManagerVFlashConfigInfo" minOccurs="0"/>
 *         &lt;element name="vsanHostConfig" type="{urn:vim25}VsanHostConfigInfo" minOccurs="0"/>
 *         &lt;element name="domainList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scriptCheckSum" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="hostConfigCheckSum" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="graphicsInfo" type="{urn:vim25}HostGraphicsInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sharedPassthruGpuTypes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ioFilterInfo" type="{urn:vim25}HostIoFilterInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostConfigInfo", propOrder = {
    "host",
    "product",
    "hyperThread",
    "consoleReservation",
    "virtualMachineReservation",
    "storageDevice",
    "multipathState",
    "fileSystemVolume",
    "systemFile",
    "network",
    "vmotion",
    "virtualNicManagerInfo",
    "capabilities",
    "datastoreCapabilities",
    "offloadCapabilities",
    "service",
    "firewall",
    "autoStart",
    "activeDiagnosticPartition",
    "option",
    "optionDef",
    "datastorePrincipal",
    "localSwapDatastore",
    "systemSwapConfiguration",
    "systemResources",
    "dateTimeInfo",
    "flags",
    "adminDisabled",
    "lockdownMode",
    "ipmi",
    "sslThumbprintInfo",
    "sslThumbprintData",
    "certificate",
    "pciPassthruInfo",
    "authenticationManagerInfo",
    "featureVersion",
    "powerSystemCapability",
    "powerSystemInfo",
    "cacheConfigurationInfo",
    "wakeOnLanCapable",
    "featureCapability",
    "maskedFeatureCapability",
    "vFlashConfigInfo",
    "vsanHostConfig",
    "domainList",
    "scriptCheckSum",
    "hostConfigCheckSum",
    "graphicsInfo",
    "sharedPassthruGpuTypes",
    "ioFilterInfo"
})
public class HostConfigInfo
    extends DynamicData
{

    @XmlElement(required = true)
    protected ManagedObjectReference host;
    @XmlElement(required = true)
    protected AboutInfo product;
    protected HostHyperThreadScheduleInfo hyperThread;
    protected ServiceConsoleReservationInfo consoleReservation;
    protected VirtualMachineMemoryReservationInfo virtualMachineReservation;
    protected HostStorageDeviceInfo storageDevice;
    protected HostMultipathStateInfo multipathState;
    protected HostFileSystemVolumeInfo fileSystemVolume;
    protected List<String> systemFile;
    protected HostNetworkInfo network;
    protected HostVMotionInfo vmotion;
    protected HostVirtualNicManagerInfo virtualNicManagerInfo;
    protected HostNetCapabilities capabilities;
    protected HostDatastoreSystemCapabilities datastoreCapabilities;
    protected HostNetOffloadCapabilities offloadCapabilities;
    protected HostServiceInfo service;
    protected HostFirewallInfo firewall;
    protected HostAutoStartManagerConfig autoStart;
    protected HostDiagnosticPartition activeDiagnosticPartition;
    protected List<OptionValue> option;
    protected List<OptionDef> optionDef;
    protected String datastorePrincipal;
    protected ManagedObjectReference localSwapDatastore;
    protected HostSystemSwapConfiguration systemSwapConfiguration;
    protected HostSystemResourceInfo systemResources;
    protected HostDateTimeInfo dateTimeInfo;
    protected HostFlagInfo flags;
    protected Boolean adminDisabled;
    protected HostLockdownMode lockdownMode;
    protected HostIpmiInfo ipmi;
    protected HostSslThumbprintInfo sslThumbprintInfo;
    protected List<HostSslThumbprintInfo> sslThumbprintData;
    @XmlElement(type = Byte.class)
    protected List<Byte> certificate;
    protected List<HostPciPassthruInfo> pciPassthruInfo;
    protected HostAuthenticationManagerInfo authenticationManagerInfo;
    protected List<HostFeatureVersionInfo> featureVersion;
    protected PowerSystemCapability powerSystemCapability;
    protected PowerSystemInfo powerSystemInfo;
    protected List<HostCacheConfigurationInfo> cacheConfigurationInfo;
    protected Boolean wakeOnLanCapable;
    protected List<HostFeatureCapability> featureCapability;
    protected List<HostFeatureCapability> maskedFeatureCapability;
    protected HostVFlashManagerVFlashConfigInfo vFlashConfigInfo;
    protected VsanHostConfigInfo vsanHostConfig;
    protected List<String> domainList;
    protected byte[] scriptCheckSum;
    protected byte[] hostConfigCheckSum;
    protected List<HostGraphicsInfo> graphicsInfo;
    protected List<String> sharedPassthruGpuTypes;
    protected List<HostIoFilterInfo> ioFilterInfo;

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setHost(ManagedObjectReference value) {
        this.host = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link AboutInfo }
     *     
     */
    public AboutInfo getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link AboutInfo }
     *     
     */
    public void setProduct(AboutInfo value) {
        this.product = value;
    }

    /**
     * Gets the value of the hyperThread property.
     * 
     * @return
     *     possible object is
     *     {@link HostHyperThreadScheduleInfo }
     *     
     */
    public HostHyperThreadScheduleInfo getHyperThread() {
        return hyperThread;
    }

    /**
     * Sets the value of the hyperThread property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostHyperThreadScheduleInfo }
     *     
     */
    public void setHyperThread(HostHyperThreadScheduleInfo value) {
        this.hyperThread = value;
    }

    /**
     * Gets the value of the consoleReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceConsoleReservationInfo }
     *     
     */
    public ServiceConsoleReservationInfo getConsoleReservation() {
        return consoleReservation;
    }

    /**
     * Sets the value of the consoleReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceConsoleReservationInfo }
     *     
     */
    public void setConsoleReservation(ServiceConsoleReservationInfo value) {
        this.consoleReservation = value;
    }

    /**
     * Gets the value of the virtualMachineReservation property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineMemoryReservationInfo }
     *     
     */
    public VirtualMachineMemoryReservationInfo getVirtualMachineReservation() {
        return virtualMachineReservation;
    }

    /**
     * Sets the value of the virtualMachineReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineMemoryReservationInfo }
     *     
     */
    public void setVirtualMachineReservation(VirtualMachineMemoryReservationInfo value) {
        this.virtualMachineReservation = value;
    }

    /**
     * Gets the value of the storageDevice property.
     * 
     * @return
     *     possible object is
     *     {@link HostStorageDeviceInfo }
     *     
     */
    public HostStorageDeviceInfo getStorageDevice() {
        return storageDevice;
    }

    /**
     * Sets the value of the storageDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostStorageDeviceInfo }
     *     
     */
    public void setStorageDevice(HostStorageDeviceInfo value) {
        this.storageDevice = value;
    }

    /**
     * Gets the value of the multipathState property.
     * 
     * @return
     *     possible object is
     *     {@link HostMultipathStateInfo }
     *     
     */
    public HostMultipathStateInfo getMultipathState() {
        return multipathState;
    }

    /**
     * Sets the value of the multipathState property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostMultipathStateInfo }
     *     
     */
    public void setMultipathState(HostMultipathStateInfo value) {
        this.multipathState = value;
    }

    /**
     * Gets the value of the fileSystemVolume property.
     * 
     * @return
     *     possible object is
     *     {@link HostFileSystemVolumeInfo }
     *     
     */
    public HostFileSystemVolumeInfo getFileSystemVolume() {
        return fileSystemVolume;
    }

    /**
     * Sets the value of the fileSystemVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostFileSystemVolumeInfo }
     *     
     */
    public void setFileSystemVolume(HostFileSystemVolumeInfo value) {
        this.fileSystemVolume = value;
    }

    /**
     * Gets the value of the systemFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the systemFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSystemFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSystemFile() {
        if (systemFile == null) {
            systemFile = new ArrayList<String>();
        }
        return this.systemFile;
    }

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link HostNetworkInfo }
     *     
     */
    public HostNetworkInfo getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostNetworkInfo }
     *     
     */
    public void setNetwork(HostNetworkInfo value) {
        this.network = value;
    }

    /**
     * Gets the value of the vmotion property.
     * 
     * @return
     *     possible object is
     *     {@link HostVMotionInfo }
     *     
     */
    public HostVMotionInfo getVmotion() {
        return vmotion;
    }

    /**
     * Sets the value of the vmotion property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVMotionInfo }
     *     
     */
    public void setVmotion(HostVMotionInfo value) {
        this.vmotion = value;
    }

    /**
     * Gets the value of the virtualNicManagerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostVirtualNicManagerInfo }
     *     
     */
    public HostVirtualNicManagerInfo getVirtualNicManagerInfo() {
        return virtualNicManagerInfo;
    }

    /**
     * Sets the value of the virtualNicManagerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVirtualNicManagerInfo }
     *     
     */
    public void setVirtualNicManagerInfo(HostVirtualNicManagerInfo value) {
        this.virtualNicManagerInfo = value;
    }

    /**
     * Gets the value of the capabilities property.
     * 
     * @return
     *     possible object is
     *     {@link HostNetCapabilities }
     *     
     */
    public HostNetCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the value of the capabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostNetCapabilities }
     *     
     */
    public void setCapabilities(HostNetCapabilities value) {
        this.capabilities = value;
    }

    /**
     * Gets the value of the datastoreCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link HostDatastoreSystemCapabilities }
     *     
     */
    public HostDatastoreSystemCapabilities getDatastoreCapabilities() {
        return datastoreCapabilities;
    }

    /**
     * Sets the value of the datastoreCapabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostDatastoreSystemCapabilities }
     *     
     */
    public void setDatastoreCapabilities(HostDatastoreSystemCapabilities value) {
        this.datastoreCapabilities = value;
    }

    /**
     * Gets the value of the offloadCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link HostNetOffloadCapabilities }
     *     
     */
    public HostNetOffloadCapabilities getOffloadCapabilities() {
        return offloadCapabilities;
    }

    /**
     * Sets the value of the offloadCapabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostNetOffloadCapabilities }
     *     
     */
    public void setOffloadCapabilities(HostNetOffloadCapabilities value) {
        this.offloadCapabilities = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link HostServiceInfo }
     *     
     */
    public HostServiceInfo getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostServiceInfo }
     *     
     */
    public void setService(HostServiceInfo value) {
        this.service = value;
    }

    /**
     * Gets the value of the firewall property.
     * 
     * @return
     *     possible object is
     *     {@link HostFirewallInfo }
     *     
     */
    public HostFirewallInfo getFirewall() {
        return firewall;
    }

    /**
     * Sets the value of the firewall property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostFirewallInfo }
     *     
     */
    public void setFirewall(HostFirewallInfo value) {
        this.firewall = value;
    }

    /**
     * Gets the value of the autoStart property.
     * 
     * @return
     *     possible object is
     *     {@link HostAutoStartManagerConfig }
     *     
     */
    public HostAutoStartManagerConfig getAutoStart() {
        return autoStart;
    }

    /**
     * Sets the value of the autoStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostAutoStartManagerConfig }
     *     
     */
    public void setAutoStart(HostAutoStartManagerConfig value) {
        this.autoStart = value;
    }

    /**
     * Gets the value of the activeDiagnosticPartition property.
     * 
     * @return
     *     possible object is
     *     {@link HostDiagnosticPartition }
     *     
     */
    public HostDiagnosticPartition getActiveDiagnosticPartition() {
        return activeDiagnosticPartition;
    }

    /**
     * Sets the value of the activeDiagnosticPartition property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostDiagnosticPartition }
     *     
     */
    public void setActiveDiagnosticPartition(HostDiagnosticPartition value) {
        this.activeDiagnosticPartition = value;
    }

    /**
     * Gets the value of the option property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the option property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OptionValue }
     * 
     * 
     */
    public List<OptionValue> getOption() {
        if (option == null) {
            option = new ArrayList<OptionValue>();
        }
        return this.option;
    }

    /**
     * Gets the value of the optionDef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optionDef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptionDef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OptionDef }
     * 
     * 
     */
    public List<OptionDef> getOptionDef() {
        if (optionDef == null) {
            optionDef = new ArrayList<OptionDef>();
        }
        return this.optionDef;
    }

    /**
     * Gets the value of the datastorePrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatastorePrincipal() {
        return datastorePrincipal;
    }

    /**
     * Sets the value of the datastorePrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatastorePrincipal(String value) {
        this.datastorePrincipal = value;
    }

    /**
     * Gets the value of the localSwapDatastore property.
     * 
     * @return
     *     possible object is
     *     {@link ManagedObjectReference }
     *     
     */
    public ManagedObjectReference getLocalSwapDatastore() {
        return localSwapDatastore;
    }

    /**
     * Sets the value of the localSwapDatastore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedObjectReference }
     *     
     */
    public void setLocalSwapDatastore(ManagedObjectReference value) {
        this.localSwapDatastore = value;
    }

    /**
     * Gets the value of the systemSwapConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link HostSystemSwapConfiguration }
     *     
     */
    public HostSystemSwapConfiguration getSystemSwapConfiguration() {
        return systemSwapConfiguration;
    }

    /**
     * Sets the value of the systemSwapConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostSystemSwapConfiguration }
     *     
     */
    public void setSystemSwapConfiguration(HostSystemSwapConfiguration value) {
        this.systemSwapConfiguration = value;
    }

    /**
     * Gets the value of the systemResources property.
     * 
     * @return
     *     possible object is
     *     {@link HostSystemResourceInfo }
     *     
     */
    public HostSystemResourceInfo getSystemResources() {
        return systemResources;
    }

    /**
     * Sets the value of the systemResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostSystemResourceInfo }
     *     
     */
    public void setSystemResources(HostSystemResourceInfo value) {
        this.systemResources = value;
    }

    /**
     * Gets the value of the dateTimeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostDateTimeInfo }
     *     
     */
    public HostDateTimeInfo getDateTimeInfo() {
        return dateTimeInfo;
    }

    /**
     * Sets the value of the dateTimeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostDateTimeInfo }
     *     
     */
    public void setDateTimeInfo(HostDateTimeInfo value) {
        this.dateTimeInfo = value;
    }

    /**
     * Gets the value of the flags property.
     * 
     * @return
     *     possible object is
     *     {@link HostFlagInfo }
     *     
     */
    public HostFlagInfo getFlags() {
        return flags;
    }

    /**
     * Sets the value of the flags property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostFlagInfo }
     *     
     */
    public void setFlags(HostFlagInfo value) {
        this.flags = value;
    }

    /**
     * Gets the value of the adminDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdminDisabled() {
        return adminDisabled;
    }

    /**
     * Sets the value of the adminDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdminDisabled(Boolean value) {
        this.adminDisabled = value;
    }

    /**
     * Gets the value of the lockdownMode property.
     * 
     * @return
     *     possible object is
     *     {@link HostLockdownMode }
     *     
     */
    public HostLockdownMode getLockdownMode() {
        return lockdownMode;
    }

    /**
     * Sets the value of the lockdownMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostLockdownMode }
     *     
     */
    public void setLockdownMode(HostLockdownMode value) {
        this.lockdownMode = value;
    }

    /**
     * Gets the value of the ipmi property.
     * 
     * @return
     *     possible object is
     *     {@link HostIpmiInfo }
     *     
     */
    public HostIpmiInfo getIpmi() {
        return ipmi;
    }

    /**
     * Sets the value of the ipmi property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostIpmiInfo }
     *     
     */
    public void setIpmi(HostIpmiInfo value) {
        this.ipmi = value;
    }

    /**
     * Gets the value of the sslThumbprintInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostSslThumbprintInfo }
     *     
     */
    public HostSslThumbprintInfo getSslThumbprintInfo() {
        return sslThumbprintInfo;
    }

    /**
     * Sets the value of the sslThumbprintInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostSslThumbprintInfo }
     *     
     */
    public void setSslThumbprintInfo(HostSslThumbprintInfo value) {
        this.sslThumbprintInfo = value;
    }

    /**
     * Gets the value of the sslThumbprintData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sslThumbprintData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSslThumbprintData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostSslThumbprintInfo }
     * 
     * 
     */
    public List<HostSslThumbprintInfo> getSslThumbprintData() {
        if (sslThumbprintData == null) {
            sslThumbprintData = new ArrayList<HostSslThumbprintInfo>();
        }
        return this.sslThumbprintData;
    }

    /**
     * Gets the value of the certificate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certificate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertificate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Byte }
     * 
     * 
     */
    public List<Byte> getCertificate() {
        if (certificate == null) {
            certificate = new ArrayList<Byte>();
        }
        return this.certificate;
    }

    /**
     * Gets the value of the pciPassthruInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pciPassthruInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPciPassthruInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostPciPassthruInfo }
     * 
     * 
     */
    public List<HostPciPassthruInfo> getPciPassthruInfo() {
        if (pciPassthruInfo == null) {
            pciPassthruInfo = new ArrayList<HostPciPassthruInfo>();
        }
        return this.pciPassthruInfo;
    }

    /**
     * Gets the value of the authenticationManagerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostAuthenticationManagerInfo }
     *     
     */
    public HostAuthenticationManagerInfo getAuthenticationManagerInfo() {
        return authenticationManagerInfo;
    }

    /**
     * Sets the value of the authenticationManagerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostAuthenticationManagerInfo }
     *     
     */
    public void setAuthenticationManagerInfo(HostAuthenticationManagerInfo value) {
        this.authenticationManagerInfo = value;
    }

    /**
     * Gets the value of the featureVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostFeatureVersionInfo }
     * 
     * 
     */
    public List<HostFeatureVersionInfo> getFeatureVersion() {
        if (featureVersion == null) {
            featureVersion = new ArrayList<HostFeatureVersionInfo>();
        }
        return this.featureVersion;
    }

    /**
     * Gets the value of the powerSystemCapability property.
     * 
     * @return
     *     possible object is
     *     {@link PowerSystemCapability }
     *     
     */
    public PowerSystemCapability getPowerSystemCapability() {
        return powerSystemCapability;
    }

    /**
     * Sets the value of the powerSystemCapability property.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerSystemCapability }
     *     
     */
    public void setPowerSystemCapability(PowerSystemCapability value) {
        this.powerSystemCapability = value;
    }

    /**
     * Gets the value of the powerSystemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PowerSystemInfo }
     *     
     */
    public PowerSystemInfo getPowerSystemInfo() {
        return powerSystemInfo;
    }

    /**
     * Sets the value of the powerSystemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerSystemInfo }
     *     
     */
    public void setPowerSystemInfo(PowerSystemInfo value) {
        this.powerSystemInfo = value;
    }

    /**
     * Gets the value of the cacheConfigurationInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cacheConfigurationInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCacheConfigurationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostCacheConfigurationInfo }
     * 
     * 
     */
    public List<HostCacheConfigurationInfo> getCacheConfigurationInfo() {
        if (cacheConfigurationInfo == null) {
            cacheConfigurationInfo = new ArrayList<HostCacheConfigurationInfo>();
        }
        return this.cacheConfigurationInfo;
    }

    /**
     * Gets the value of the wakeOnLanCapable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWakeOnLanCapable() {
        return wakeOnLanCapable;
    }

    /**
     * Sets the value of the wakeOnLanCapable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWakeOnLanCapable(Boolean value) {
        this.wakeOnLanCapable = value;
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
     * Gets the value of the maskedFeatureCapability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maskedFeatureCapability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaskedFeatureCapability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostFeatureCapability }
     * 
     * 
     */
    public List<HostFeatureCapability> getMaskedFeatureCapability() {
        if (maskedFeatureCapability == null) {
            maskedFeatureCapability = new ArrayList<HostFeatureCapability>();
        }
        return this.maskedFeatureCapability;
    }

    /**
     * Gets the value of the vFlashConfigInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HostVFlashManagerVFlashConfigInfo }
     *     
     */
    public HostVFlashManagerVFlashConfigInfo getVFlashConfigInfo() {
        return vFlashConfigInfo;
    }

    /**
     * Sets the value of the vFlashConfigInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostVFlashManagerVFlashConfigInfo }
     *     
     */
    public void setVFlashConfigInfo(HostVFlashManagerVFlashConfigInfo value) {
        this.vFlashConfigInfo = value;
    }

    /**
     * Gets the value of the vsanHostConfig property.
     * 
     * @return
     *     possible object is
     *     {@link VsanHostConfigInfo }
     *     
     */
    public VsanHostConfigInfo getVsanHostConfig() {
        return vsanHostConfig;
    }

    /**
     * Sets the value of the vsanHostConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link VsanHostConfigInfo }
     *     
     */
    public void setVsanHostConfig(VsanHostConfigInfo value) {
        this.vsanHostConfig = value;
    }

    /**
     * Gets the value of the domainList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDomainList() {
        if (domainList == null) {
            domainList = new ArrayList<String>();
        }
        return this.domainList;
    }

    /**
     * Gets the value of the scriptCheckSum property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getScriptCheckSum() {
        return scriptCheckSum;
    }

    /**
     * Sets the value of the scriptCheckSum property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setScriptCheckSum(byte[] value) {
        this.scriptCheckSum = value;
    }

    /**
     * Gets the value of the hostConfigCheckSum property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHostConfigCheckSum() {
        return hostConfigCheckSum;
    }

    /**
     * Sets the value of the hostConfigCheckSum property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHostConfigCheckSum(byte[] value) {
        this.hostConfigCheckSum = value;
    }

    /**
     * Gets the value of the graphicsInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the graphicsInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGraphicsInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostGraphicsInfo }
     * 
     * 
     */
    public List<HostGraphicsInfo> getGraphicsInfo() {
        if (graphicsInfo == null) {
            graphicsInfo = new ArrayList<HostGraphicsInfo>();
        }
        return this.graphicsInfo;
    }

    /**
     * Gets the value of the sharedPassthruGpuTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sharedPassthruGpuTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSharedPassthruGpuTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSharedPassthruGpuTypes() {
        if (sharedPassthruGpuTypes == null) {
            sharedPassthruGpuTypes = new ArrayList<String>();
        }
        return this.sharedPassthruGpuTypes;
    }

    /**
     * Gets the value of the ioFilterInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ioFilterInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIoFilterInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostIoFilterInfo }
     * 
     * 
     */
    public List<HostIoFilterInfo> getIoFilterInfo() {
        if (ioFilterInfo == null) {
            ioFilterInfo = new ArrayList<HostIoFilterInfo>();
        }
        return this.ioFilterInfo;
    }

}
