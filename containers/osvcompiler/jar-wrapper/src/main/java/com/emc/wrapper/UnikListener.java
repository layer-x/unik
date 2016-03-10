package com.emc.wrapper;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.Inet4Address;

public class UnikListener {
    private String type = "_unik._tcp";
    private String unikIp;

    public void Listen() throws IOException {
        final JmDNS jmdns = JmDNS.create();
        jmdns.addServiceListener(type, new ServiceListener() {
            public void serviceResolved(ServiceEvent ev) {
                Inet4Address[] addresses = ev.getInfo().getInet4Addresses();
                if (addresses.length > 0 && addresses[0] != null) {
                    unikIp = addresses[0].getHostAddress();
                }
            }

            public void serviceRemoved(ServiceEvent ev) {
                System.out.println("Service removed: " + ev.getName());
            }

            public void serviceAdded(ServiceEvent event) {
                // Required to force serviceResolved to be called again
                // (after the first search)
                jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
            }
        });
    }

    public String GetUnikIp() throws InterruptedException {
        while (unikIp == null) {
            System.out.println("waiting 1s for unik to broadcast its ip...");
            Thread.sleep(1000);
        }
        return unikIp;
    }
}

