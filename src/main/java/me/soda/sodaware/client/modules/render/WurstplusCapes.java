package me.soda.sodaware.client.modules.render;

import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

//mhm sexy cat cape mhm

public class WurstplusCapes extends WurstplusHack {

    public WurstplusCapes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }

    WurstplusSetting cape = create("Cape", "CapeCape", "New", combobox("New", "OG"));

}
