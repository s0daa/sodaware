package me.soda.sodaware.client.modules.chat;


import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

public class WurstplusClearChat extends WurstplusHack {
    public WurstplusClearChat() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Clear Chatbox";
        this.tag = "ClearChatbox";
        this.description = "Removes the default minecraft chat outline.";
    }
}