package com.emc.wrapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UnikListener {

    public String GetUnikIp() throws IOException, InterruptedException {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String unikMessage = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("RECEIVED: " + unikMessage+" FROM "+IPAddress.getHostName());
            if (unikMessage.equals("unik")) return IPAddress.getHostName();
            Thread.sleep(1000);
        }
    }
}
