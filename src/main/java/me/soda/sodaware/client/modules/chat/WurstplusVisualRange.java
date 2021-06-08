package me.soda.sodaware.client.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.WurstplusFriendUtil;
import me.soda.sodaware.client.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

//mhm visualrange

public class WurstplusVisualRange extends WurstplusHack {

	private List<String> people;

	public WurstplusVisualRange() {
		super(WurstplusCategory.WURSTPLUS_CHAT);

		this.name        = "Visual Range";
		this.tag         = "VisualRange";
		this.description = "bc using ur eyes is overrated";
	}

	@Override
	public void enable() {
		people = new ArrayList<>();
	}

	@Override
	public void update() {
		if (mc.world == null | mc.player == null) return;

		List<String> peoplenew = new ArrayList<>();
		List<EntityPlayer> playerEntities = mc.world.playerEntities;

		for (Entity e : playerEntities) {
			if (e.getName().equals(mc.player.getName())) continue;
			peoplenew.add(e.getName());
		}

		if (peoplenew.size() > 0) {
			for (String name : peoplenew) {
				if (!people.contains(name)) {
					if (WurstplusFriendUtil.isFriend(name)) {
						WurstplusMessageUtil.send_client_message("I see a cute guy called " + ChatFormatting.RESET + ChatFormatting.GREEN + name + ChatFormatting.RESET + " :3");
					} else {
						WurstplusMessageUtil.send_client_message("I see a guy called " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". :3");
					}
					people.add(name);
				}
			}
		}

	}
}
