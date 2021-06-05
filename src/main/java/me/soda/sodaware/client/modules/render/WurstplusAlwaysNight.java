package me.soda.sodaware.client.modules.render;

import me.soda.sodaware.client.event.events.WurstplusEventRender;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

//mhm night > day mhm

public class WurstplusAlwaysNight extends WurstplusHack {

    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }

    @EventHandler
    private Listener<WurstplusEventRender> on_render = new Listener<>(event -> {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    });

    @Override
    public void update() {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    }
}
