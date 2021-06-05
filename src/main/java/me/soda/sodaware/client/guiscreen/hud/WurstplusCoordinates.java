package me.soda.sodaware.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;


public class WurstplusCoordinates extends WurstplusPinnable {
	ChatFormatting dg = ChatFormatting.DARK_GRAY;
	ChatFormatting db = ChatFormatting.DARK_BLUE;
	ChatFormatting dr = ChatFormatting.DARK_RED;

	public WurstplusCoordinates() {
		super("Coordinates", "Coordinates", 1, 0, 0);
	}

	@Override
	public void render() {
		int nl_r = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String x = Sodaware.g + "[" + Sodaware.r + Integer.toString((int) (mc.player.posX)) + Sodaware.g + "]" + Sodaware.r;
		String y = Sodaware.g + "[" + Sodaware.r + Integer.toString((int) (mc.player.posY)) + Sodaware.g + "]" + Sodaware.r;
		String z = Sodaware.g + "[" + Sodaware.r + Integer.toString((int) (mc.player.posZ)) + Sodaware.g + "]" + Sodaware.r;

		String x_nether = Sodaware.g + "[" + Sodaware.r + Long.toString(Math.round(mc.player.dimension != -1 ? (mc.player.posX / 8) : (mc.player.posX * 8))) + Sodaware.g + "]" + Sodaware.r;
		String z_nether = Sodaware.g + "[" + Sodaware.r + Long.toString(Math.round(mc.player.dimension != -1 ? (mc.player.posZ / 8) : (mc.player.posZ * 8))) + Sodaware.g + "]" + Sodaware.r;

		String line = "XYZ " + x + y + z + " XZ " + x_nether + z_nether;

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width"));
		this.set_height(this.get(line, "height") + 2);
	}
}