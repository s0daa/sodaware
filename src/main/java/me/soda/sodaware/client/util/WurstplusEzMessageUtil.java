package me.soda.sodaware.client.util;

public class WurstplusEzMessageUtil {

    private static String message;

    public static void set_message(String message) {
        WurstplusEzMessageUtil.message = message;
    }

    public static String get_message() {
        return message;
    }

}
