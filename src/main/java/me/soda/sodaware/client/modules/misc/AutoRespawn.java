package me.soda.sodaware.client.modules.misc;

import me.soda.sodaware.client.event.WurstplusEventBus;
import me.soda.sodaware.client.event.events.*;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiGameOver;
import me.zero.alpine.fork.listener.EventHandler;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.*;
import me.zero.alpine.fork.listener.Listener;

public class AutoRespawn extends WurstplusHack
{
    WurstplusSetting coords = this.create("DeathCoords", "AutoRespawnDeathCoords", true);

    @EventHandler
    private Listener<WurstplusEventGUIScreen> listener;

    public AutoRespawn() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto Respawn";
        this.tag = "AutoRespawn";
        this.description = "AutoRespawn";

        this.listener = new Listener<WurstplusEventGUIScreen>(event -> {
            if (event.get_guiscreen() instanceof GuiGameOver) {
                if (this.coords.get_value(true)) {
                    WurstplusMessageUtil.send_client_message(String.format("You died at x%d y%d z%d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
                }
                if (AutoRespawn.mc.player != null) {
                    AutoRespawn.mc.player.respawnPlayer();
                }
                AutoRespawn.mc.displayGuiScreen((GuiScreen)null);
            }
            return;
        }, (Predicate<WurstplusEventGUIScreen>[])new Predicate[0]);
    }

    public void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    public void disable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
}
