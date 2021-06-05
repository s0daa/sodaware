package me.soda.sodaware.client.modules.render;

import me.soda.sodaware.client.event.events.WurstplusEventSetupFog;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

//mhm anti no see mhm

public class WurstplusAntifog extends WurstplusHack {
    
    public WurstplusAntifog() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }

    @EventHandler
    private Listener<WurstplusEventSetupFog> setup_fog = new Listener<> (event -> {

        event.cancel();

        mc.entityRenderer.setupFogColor(false);

        GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.colorMaterial(1028, 4608);

    });

}
