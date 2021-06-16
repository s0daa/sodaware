package me.soda.sodaware.client.modules.chat;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

public class Shrug
        extends WurstplusHack {
    public Shrug() {
        super( WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Shrug";
        this.tag = "Shrug";
        this.description = "shrugs";
    }

    @Override
    public void enable() {
        Shrug.mc.player.sendChatMessage( " \u00AF\\_(\u30C4)_/\u00AF" );
        this.toggle();
    }
}
