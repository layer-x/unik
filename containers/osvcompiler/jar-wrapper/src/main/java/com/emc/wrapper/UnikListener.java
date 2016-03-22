package com.emc.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UnikListener {

    public String GetUnikIp() throws IOException, InterruptedException {
//        DatagramSocket serverSocket = new DatagramSocket(9876);
//        System.out.println("Searching for unik ip...");
//        byte[] receiveData = new byte[1024];
//        while (true) {
//            System.out.println("trying again1...");
//            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//            System.out.println("trying again2...");
//            serverSocket.receive(receivePacket);
//            System.out.println("trying again3...");
//            String unikMessage = new String(receivePacket.getData());
//            System.out.println("trying again4...");
//            InetAddress IPAddress = receivePacket.getAddress();
//            System.out.println("RECEIVED: " + unikMessage+" FROM "+IPAddress.getHostName());
//            if (unikMessage.equals("unik")) return IPAddress.getHostName();
//            Thread.sleep(1000);
//        }
        return "192.168.0.46";
    }
}
