package com.emc.wrapper;


import com.google.gson.Gson;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Bootstrap {

    public static ByteArrayOutputStream logBuffer = new ByteArrayOutputStream();

    public static void bootstrap() {
        UnikListener unikListener = new UnikListener();
        String unikIp = "";
        String macAddress = "";
        try {
            unikListener.Listen();
        } catch (IOException ex) {
            System.err.println("failed to listen for unik ip addr");
            ex.printStackTrace();
            System.exit(-1);
        }
        try {
            unikIp = unikListener.GetUnikIp();
        } catch (InterruptedException ex) {
            System.err.println("failed while waiting for unik ip addr");
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

        String response = "";
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

        //connect logs
        try {
            URL url = new URL("http://" + unikIp + ":3001/connect_logs/"+macAddress);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStream unikOutputStream = connection.getOutputStream();

            MultiOutputStream multiOut = new MultiOutputStream(System.out, unikOutputStream);
            MultiOutputStream multiErr = new MultiOutputStream(System.err, unikOutputStream);

            PrintStream stdout = new PrintStream(multiOut);
            PrintStream stderr = new PrintStream(multiErr);

            System.setOut(stdout);
            System.setErr(stderr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.err.println("Malformed UNIK url");
            System.exit(-1);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("IOException");
            System.exit(-1);
        }

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
    }

    public static String getHTML(String urlToRead) throws MalformedURLException, IOException {
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

    public static String getMacAddress() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();

        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


    public interface LibC extends Library {
        public int setenv(String name, String value, int overwrite);
        public int unsetenv(String name);
    }

    public static class UnikInstanceData {
        public HashMap<String, String> Tags;
        public HashMap<String, String> Env;
    }

}
