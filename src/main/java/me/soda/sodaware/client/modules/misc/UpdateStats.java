package me.soda.sodaware.client.modules.misc;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import net.minecraft.network.play.client.CPacketClientStatus;

public class UpdateStats extends WurstplusHack {

    public UpdateStats() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "UpdateStats";
        this.tag = "UpdateStats";
        this.description = "UpdateStats for HUD module";
    }

    public void enable() {
        mc.getConnection().sendPacket(new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
        this.disable();
        this.set_disable();
    }

    public void disable(){

    }
}

