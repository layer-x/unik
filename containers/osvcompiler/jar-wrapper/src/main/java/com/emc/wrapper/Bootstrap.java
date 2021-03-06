package com.emc.wrapper;


import com.google.gson.Gson;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;

public class Bootstrap {

    public static ByteArrayOutputStream logBuffer = new ByteArrayOutputStream();

    public static void bootstrap() throws Exception {
        //connect stdout to logs
        MultiOutputStream multiOut = new MultiOutputStream(System.out, logBuffer);
        MultiOutputStream multiErr = new MultiOutputStream(System.err, logBuffer);

        PrintStream stdout = new PrintStream(multiOut);
        PrintStream stderr = new PrintStream(multiErr);

        System.setOut(stdout);
        System.setErr(stderr);

        //listen to requests for logs
        WrapperServer.ServerThread serverThread = new WrapperServer.ServerThread(new WrapperServer());
        serverThread.start();

        String response = "";

        //register with unik
        try {
            response = getHTML("http://169.254.169.254/latest/user-data");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.err.println("Malformed EC2 userdata url");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("IOException trying to contact EC2");
        }

        //if not running on ec2
        if (response.equals("")) {
            UnikListener unikListener = new UnikListener();
            String unikIp = "";
            String macAddress = "";
            try {
                unikIp = unikListener.GetUnikIp();
            } catch (IOException ex) {
                System.err.println("failed to listen for unik ip addr");
                ex.printStackTrace();
                System.exit(-1);
            } catch (InterruptedException ex) {
                System.err.println("interrupted while listening for unik ip");
                ex.printStackTrace();
                System.exit(-1);
            }

            if (unikIp.equals("")) {
                System.err.println("failed while waiting for unik ip addr");
                System.exit(-1);
            }

            try {
                macAddress = getMacAddress();
            } catch (UnknownHostException ex){
                System.err.println("failed retrieving mac addr");
                ex.printStackTrace();
                System.exit(-1);
            } catch (SocketException ex){
                System.err.println("failed retrieving mac addr");
                ex.printStackTrace();
                System.exit(-1);
            }

            if (macAddress.equals("")) {
                System.err.println("failed to retrieve my own mac addr");
                System.exit(-1);
            }

            //register with unik
            try {
                response = getHTML("http://" + unikIp + ":3001/bootstrap?mac_address="+macAddress);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                System.err.println("Malformed UNIK url");
                System.exit(-1);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("IOException");
                System.exit(-1);
            }
        }

        //boostrap env
        Gson gson = new Gson();
        UnikInstanceData unikInstanceData = gson.fromJson(response, UnikInstanceData.class);

        if (unikInstanceData.Env != null) {
            LibC libc = (LibC) Native.loadLibrary("c", LibC.class);
            for (String key: unikInstanceData.Env.keySet()){
                String value = unikInstanceData.Env.get(key);
                int result = libc.setenv(key, value, 1);
                System.out.println("set "+key+"="+value+": "+result);
            }
        }

    }

    public static String getHTML(String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static String getMacAddress() throws UnknownHostException, SocketException, Exception {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        byte[] mac = new byte[1];
        while (ifaces.hasMoreElements()) {
            NetworkInterface network = ifaces.nextElement();
            System.out.println("Interface name: " + network.getName());
            if (network.getHardwareAddress() != null) {
                mac = network.getHardwareAddress();
                break;
            }
        }
        if (mac.length == 1) {
            throw new Exception("faield to find mac addr");
        }

        System.out.print("Current MAC address : "+new String(mac));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            String macString = String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
            sb.append(macString);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


    public interface LibC extends Library {
        int setenv(String name, String value, int overwrite);
    }

    public static class UnikInstanceData {
        public HashMap<String, String> Tags;
        public HashMap<String, String> Env;
    }

}
