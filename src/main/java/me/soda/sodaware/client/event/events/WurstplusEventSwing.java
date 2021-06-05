package me.soda.sodaware.client.event.events;

import me.soda.sodaware.client.event.WurstplusEventCancellable;
import net.minecraft.util.EnumHand;

public class WurstplusEventSwing extends WurstplusEventCancellable {
    
    public EnumHand hand;

    public WurstplusEventSwing(EnumHand hand) {
        super();
        this.hand = hand;
    }

}