
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsSingleIpPort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsSingleIpPort">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DvsIpPort">
 *       &lt;sequence>
 *         &lt;element name="portNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DvsSingleIpPort", propOrder = {
    "portNumber"
})
public class DvsSingleIpPort
    extends DvsIpPort
{

    protected int portNumber;

    /**
     * Gets the value of the portNumber property.
     * 
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Sets the value of the portNumber property.
     * 
     */
    public void setPortNumber(int value) {
        this.portNumber = value;
    }

}
