package me.soda.sodaware.client.modules.misc;

import me.soda.sodaware.SodaRPC;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

public class RichPresence extends WurstplusHack {

    public RichPresence() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "RichPresence";
        this.tag = "RichPresence";
        this.description = "show people how cool you are";
    }

    public void enable() {
        SodaRPC.init();
        if (mc.player != null) {
            mc.player.sendChatMessage("let's have sex");
        }
    }
}
