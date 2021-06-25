package me.soda.sodaware.client.guiscreen.hud;

import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;

public class Server extends WurstplusPinnable {
    public Server() {
        super("Server", "Testing", 1, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        String line = "";
        if(mc.isIntegratedServerRunning()){
            line = "single player";
        } else {
            line = mc.getCurrentServerData().serverIP;
        }

        create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }
}