package me.soda.sodaware.client.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtil {

    public static final String HWID_URL = "https://soda.amogus.monster/sodahwid";
    public static final String H1 = "?";

    public static final String H2 = "v";

    public static final String H3 = "e";

    public static final String H4 = "r";

    public static List<String> getHWIDList(){
        List<String> HWIDList = new ArrayList<>();
        try {
            String HWIDD = HWIDUtil.getEncryptedHWID(FrameUtil.KEY);
            String HWIDDD = URIEncoder.encodeURIComponent(HWIDD);
            URLConnection connection = new URL(HWID_URL + H1 + H2 + H3 + H4 + H5 + H6 + H7 + H8 + HWIDDD).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                HWIDList.add(inputLine);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return HWIDList;
    }

    public static final String H5 = "i";

    public static final String H6 = "f";

    public static final String H7 = "y";

    public static final String H8 = "=";

}
