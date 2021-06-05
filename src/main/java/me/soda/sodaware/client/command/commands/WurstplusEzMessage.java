package me.soda.sodaware.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.command.WurstplusCommand;
import me.soda.sodaware.client.util.WurstplusEzMessageUtil;
import me.soda.sodaware.client.util.WurstplusMessageUtil;

public class WurstplusEzMessage extends WurstplusCommand {

    public WurstplusEzMessage() {
        super("ezmessage", "Set ez mode");
    }

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("message needed");
            return true;
        }

        if (message.length >= 2) {
            StringBuilder ez = new StringBuilder();
            boolean flag = true;
            for (String word : message) {
                if (flag) {
                    flag = false;
                    continue;
                }
                ez.append(word).append(" ");
            }
            WurstplusEzMessageUtil.set_message(ez.toString());
            WurstplusMessageUtil.send_client_message("ez message changed to " + ChatFormatting.BOLD + ez.toString());
            Sodaware.get_config_manager().save_settings();
            return true;
        }

        return false;

    }

}
