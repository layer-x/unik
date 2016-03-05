
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostSriovInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostSriovInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}HostPciPassthruInfo">
 *       &lt;sequence>
 *         &lt;element name="sriovEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sriovCapable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sriovActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="numVirtualFunctionRequested" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numVirtualFunction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxVirtualFunctionSupported" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostSriovInfo", propOrder = {
    "sriovEnabled",
    "sriovCapable",
    "sriovActive",
    "numVirtualFunctionRequested",
    "numVirtualFunction",
    "maxVirtualFunctionSupported"
})
public class HostSriovInfo
    extends HostPciPassthruInfo
{

    protected boolean sriovEnabled;
    protected boolean sriovCapable;
    protected boolean sriovActive;
    protected int numVirtualFunctionRequested;
    protected int numVirtualFunction;
    protected int maxVirtualFunctionSupported;

    /**
     * Gets the value of the sriovEnabled property.
     * 
     */
    public boolean isSriovEnabled() {
        return sriovEnabled;
    }

    /**
     * Sets the value of the sriovEnabled property.
     * 
     */
    public void setSriovEnabled(boolean value) {
        this.sriovEnabled = value;
    }

    /**
     * Gets the value of the sriovCapable property.
     * 
     */
    public boolean isSriovCapable() {
        return sriovCapable;
    }

    /**
     * Sets the value of the sriovCapable property.
     * 
     */
    public void setSriovCapable(boolean value) {
        this.sriovCapable = value;
    }

    /**
     * Gets the value of the sriovActive property.
     * 
     */
    public boolean isSriovActive() {
        return sriovActive;
    }

    /**
     * Sets the value of the sriovActive property.
     * 
     */
    public void setSriovActive(boolean value) {
        this.sriovActive = value;
    }

    /**
     * Gets the value of the numVirtualFunctionRequested property.
     * 
     */
    public int getNumVirtualFunctionRequested() {
        return numVirtualFunctionRequested;
    }

    /**
     * Sets the value of the numVirtualFunctionRequested property.
     * 
     */
    public void setNumVirtualFunctionRequested(int value) {
        this.numVirtualFunctionRequested = value;
    }

    /**
     * Gets the value of the numVirtualFunction property.
     * 
     */
    public int getNumVirtualFunction() {
        return numVirtualFunction;
    }

    /**
     * Sets the value of the numVirtualFunction property.
     * 
     */
    public void setNumVirtualFunction(int value) {
        this.numVirtualFunction = value;
    }

    /**
     * Gets the value of the maxVirtualFunctionSupported property.
     * 
     */
    public int getMaxVirtualFunctionSupported() {
        return maxVirtualFunctionSupported;
    }

    /**
     * Sets the value of the maxVirtualFunctionSupported property.
     * 
     */
    public void setMaxVirtualFunctionSupported(int value) {
        this.maxVirtualFunctionSupported = value;
    }

}
