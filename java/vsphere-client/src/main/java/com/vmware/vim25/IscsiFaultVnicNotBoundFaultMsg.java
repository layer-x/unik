
package com.vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "IscsiFaultVnicNotBoundFault", targetNamespace = "urn:vim25")
public class IscsiFaultVnicNotBoundFaultMsg
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private IscsiFaultVnicNotBound faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IscsiFaultVnicNotBoundFaultMsg(String message, IscsiFaultVnicNotBound faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IscsiFaultVnicNotBoundFaultMsg(String message, IscsiFaultVnicNotBound faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.vmware.vim25.IscsiFaultVnicNotBound
     */
    public IscsiFaultVnicNotBound getFaultInfo() {
        return faultInfo;
    }

}
