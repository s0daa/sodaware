package me.soda.sodaware.client.modules.render;

import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

public class TitleScreenShader extends WurstplusHack {

    public TitleScreenShader() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name        = "Menu Shaders";
        this.tag         = "TitleScreenShader";
        this.description = "TitleScreenShader";
    }

}
