
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CannotMoveVsanEnabledHost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CannotMoveVsanEnabledHost">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VsanFault">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CannotMoveVsanEnabledHost")
@XmlSeeAlso({
    DestinationVsanDisabled.class,
    VsanClusterUuidMismatch.class
})
public class CannotMoveVsanEnabledHost
    extends VsanFault
{


}
