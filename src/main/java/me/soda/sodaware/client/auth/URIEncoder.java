package me.soda.sodaware.client.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URIEncoder {
    private static final String[][] CHARACTERS = {
            { "\\+", "%20" },
            { "\\%21", "!" },
            { "\\%27", "'" },
            { "\\%28", "(" },
            { "\\%29", ")" },
            { "\\%7E", "~" }
    };

    public static String encodeURIComponent(String text)
            throws UnsupportedEncodingException {

        String result = URLEncoder.encode(text, "UTF-8"); // StandardCharsets.UTF_8

        for(String[] entry : CHARACTERS) {
            result = result.replaceAll(entry[0], entry[1]);
        }

        return result;
    }
}
