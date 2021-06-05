package me.soda.sodaware.client.modules.misc;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.DiscordUtil;

public class RichPresence extends WurstplusHack {
	
	public RichPresence() {
		
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Rich Presence";
        this.tag = "RichPresence";
        this.description = "shows discord rpc";
	}
    @Override
    public void enable() {
        DiscordUtil.init();
    }
    @Override
    public void disable() {
    	DiscordUtil.shutdown();
    }
}