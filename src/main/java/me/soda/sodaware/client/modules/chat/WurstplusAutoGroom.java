package me.soda.sodaware.client.modules.chat;

import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WurstplusAutoGroom extends WurstplusHack {

    /*
     *    Created by GL_DONT_CARE
     */

    public WurstplusAutoGroom() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Auto Groom";
        this.tag = "AutoGroom";
        this.description = "i love 13 y/o femboys";
    }

    WurstplusSetting delay = create("Delay", "AutoGroomDelay", 10, 0, 100);

    List<String> chants = new ArrayList<>();

    Random r = new Random();
    int tick_delay;

    @Override
    protected void enable() {
        tick_delay = 0;

        chants.add("Send thigh pics");
        chants.add("Are you a femboy?");
        chants.add("Where do you live?");
        chants.add("How old are you?");
        chants.add("Let's have sex");
        chants.add("Send skirt pics");
        chants.add("I'll get you nitro if you send thigh pics");
        chants.add("Hewwo cutie");
        chants.add("AwA Let's have sex");
    }

    @Override
    public void update() {
		tick_delay++;

		if (tick_delay < delay.get_value(1) * 10) return;

		String s = chants.get(r.nextInt(chants.size()));

		mc.player.sendChatMessage(s);
		tick_delay = 0;
    }
}
