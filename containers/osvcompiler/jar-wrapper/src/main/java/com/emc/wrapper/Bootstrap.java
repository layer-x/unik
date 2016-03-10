package com.emc.wrapper;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Bootstrap {
    public static void bootstrap() {
        UnikListener unikListener = new UnikListener();
        String unikEndpoint = "";
        try {
            unikListener.Listen();
        } catch (IOException ex) {
            System.err.println("failed to listen for unik ip addr");
            ex.printStackTrace();
            System.exit(-1);
        }
        try {
            unikEndpoint = "http://"+unikListener.GetUnikIp()+":3000/connect";
        } catch (InterruptedException ex) {
            System.err.println("failed while waiting for unik ip addr");
            ex.printStackTrace();
            System.exit(-1);
        }

        if (unikEndpoint.equals("")) {
            System.err.println("failed while waiting for unik ip addr");
            System.exit(-1);
        }

        try {
            URL url = new URL(unikEndpoint);
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

    public static class UnikInstance {
        public String UnikInstanceID;
        public String UnikInstanceName;
        public UnikInstanceData UnikInstanceData;
    }

    public static class UnikInstanceData {
        public HashMap<String, String> Tags;
        public HashMap<String, String> Env;
    }

}
