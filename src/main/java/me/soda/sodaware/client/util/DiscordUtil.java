package me.soda.sodaware.client.util;


import com.google.gson.Gson;
import me.soda.sodaware.Sodaware;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author dominikaaaa
 */
public class DiscordUtil {

    public static DiscordUtil INSTANCE;
    public CustomUser[] customUsers;

    public static class CustomUser {
        public String uuid;
        public String type;
    }

    public DiscordUtil() {
        INSTANCE = this;
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(Sodaware.DONATORS_JSON).openConnection();
            connection.connect();
            this.customUsers = new Gson().fromJson(new InputStreamReader(connection.getInputStream()), CustomUser[].class);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
