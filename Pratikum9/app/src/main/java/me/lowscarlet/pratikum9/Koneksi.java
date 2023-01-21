package me.lowscarlet.pratikum9;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Koneksi {
    public String call(String url) {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return str;
    }
    private InputStream OpenHttpConnection(String url) throws IOException {
        InputStream in = null;
        int response = -1;
        URL url1 = new URL(url);
        URLConnection conn = url1.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not An Http Connection");
        try {
            HttpURLConnection httpconn = (HttpURLConnection) conn;
            System.out.println("[TESTTEST] pass 1");
            httpconn.setAllowUserInteraction(false);
            System.out.println("[TESTTEST] pass 2");
            httpconn.setInstanceFollowRedirects(true);
            System.out.println("[TESTTEST] pass 3");
            httpconn.setRequestMethod("GET");
            System.out.println("[TESTTEST] pass 4");
            httpconn.connect();
            System.out.println("[TESTTEST] pass 5");
            response = httpconn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpconn.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error connecting2");
        }
        return in;
    }
}

