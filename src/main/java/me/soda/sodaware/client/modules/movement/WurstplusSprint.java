package me.soda.sodaware.client.modules.movement;

import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

//mhm vaimok custom code mhm

public class WurstplusSprint extends WurstplusHack {
    
    public WurstplusSprint() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		this.name        = "Sprint";
		this.tag         = "Sprint";
		this.description = "ZOOOOOOOOM";
    }

    WurstplusSetting rage = create("Rage", "SprintRage", true);

    @Override
	public void update() {

    	if (mc.player == null) return;

    	if (rage.get_value(true) && (mc.player.moveForward != 0 || mc.player.moveStrafing != 0)) {
			mc.player.setSprinting(true);
		} else mc.player.setSprinting(mc.player.moveForward > 0 || mc.player.moveStrafing > 0);
		
	}


}
