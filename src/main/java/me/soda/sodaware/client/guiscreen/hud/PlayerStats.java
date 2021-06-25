package me.soda.sodaware.client.guiscreen.hud;

import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.soda.sodaware.client.modules.misc.UpdateStats;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.stats.StatList;
import scala.collection.script.Update;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerStats extends WurstplusPinnable {

    public PlayerStats() {
        super("PlayerStats", "PlayerStats", 1, 0, 0);
    }

    public static int kills = 0;
    public static int deaths = 0;
    public static int damage_deal = 0;

    @Override
    public void render() {
        int nl_r = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        kills = mc.player.getStatFileWriter().readStat(StatList.PLAYER_KILLS);
        deaths = mc.player.getStatFileWriter().readStat(StatList.DEATHS);
        damage_deal = mc.player.getStatFileWriter().readStat(StatList.DAMAGE_DEALT);

        if(kills == 0 && deaths == 0){
            String killLine = Sodaware.l + "Click UpdateStats";
            String deathLine = Sodaware.l + "In GUI to get";
            String deadLine = Sodaware.l + "Kill Stats";

            create_line(killLine, this.docking(1, killLine), 1, nl_r, nl_g, nl_b, nl_a);
            create_line(deathLine, this.docking(1, deathLine), this.get(killLine, "height") + 2, nl_r, nl_g, nl_b, nl_a);
            create_line(deadLine, this.docking(1, deadLine), this.get(killLine, "height") * 2 + 3, nl_r, nl_g, nl_b, nl_a);

            this.set_width(this.get(killLine, "width") + 10);
            this.set_height(this.get(killLine, "height") * 3 + 10);
        } else {
            String killLine = "Kills: " + "\u00A7a" + kills;
            String deathLine = "Deaths: " + "\u00A7a" + deaths;
            String damageLine = "Damage Dealt: " + "\u00A7a" + damage_deal;

            create_line(killLine, this.docking(1, killLine), 1, nl_r, nl_g, nl_b, nl_a);
            create_line(deathLine, this.docking(1, deathLine), this.get(killLine, "height") + 2, nl_r, nl_g, nl_b, nl_a);
            create_line(damageLine, this.docking(1, damageLine), this.get(killLine, "height") * 2 + 3, nl_r, nl_g, nl_b, nl_a);

            this.set_width(this.get(killLine, "width") + 10);
            this.set_height(this.get(killLine, "height") * 3 + 10);
        }
    }
}