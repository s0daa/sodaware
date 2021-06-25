package me.soda.sodaware.client.modules.movement;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlow extends WurstplusHack {
    public NoSlow() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "No Slow";
        this.tag = "NoSlow";
        this.description = "dont make you slow while doing anything, duh.";
    }

    @EventHandler
    private Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });
}
