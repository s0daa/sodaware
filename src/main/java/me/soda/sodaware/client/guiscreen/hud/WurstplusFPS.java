package me.soda.sodaware.client.guiscreen.hud;


import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusFPS extends WurstplusPinnable {
    
    public WurstplusFPS() {
        super("Fps", "Fps", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "FPS: " + get_fps();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}
	
    public String get_fps() {
		int fps = mc.getDebugFPS();
		if (fps >= 60) {
			return "\u00A7a"+Integer.toString(fps);
		} else if (fps >= 30) {
			return "\u00A73"+Integer.toString(fps);
		} else {
			return "\u00A74"+Integer.toString(fps);
		}
    }

}