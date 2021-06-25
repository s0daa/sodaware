package me.soda.sodaware.client.modules.misc;

import me.soda.sodaware.SodaRPC;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import net.minecraft.client.Minecraft;

public class RichPresence extends WurstplusHack {

    public RichPresence() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "DiscordRPC";
        this.tag = "RichPresence";
        this.description = "show people how cool you are";
    }

    public void enable() {
        if(Sodaware.isOnline()){
            SodaRPC.init();
        } else {
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Not connected to the internet!", false);
            this.set_disable();
        }
    }
}
