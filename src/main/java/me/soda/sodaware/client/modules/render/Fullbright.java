package me.soda.sodaware.client.modules.render;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import net.minecraft.init.MobEffects;

public class Fullbright extends WurstplusHack {

    private float prior_gamma;

    public Fullbright() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Full Bright";
        this.tag = "FullBright";
        this.description = "best hack";
    }

    @Override
    protected void enable() {
        prior_gamma = mc.gameSettings.gammaSetting;
    }

    @Override
    protected void disable() {
        mc.gameSettings.gammaSetting = prior_gamma;
    }

    @Override
    public void update() {
        mc.gameSettings.gammaSetting = 1000;
        mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
    }
}
