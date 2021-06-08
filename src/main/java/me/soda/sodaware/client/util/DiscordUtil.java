package me.soda.sodaware.client.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.soda.sodaware.Sodaware;
import net.minecraft.client.Minecraft;

public class DiscordUtil
{
    private static final String ClientId = "851865852847980563";
    private static final Minecraft mc;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static String details;
    private static String state;
    
    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        DiscordUtil.rpc.Discord_Initialize("851865852847980563", handlers, true, "");
        DiscordUtil.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordUtil.presence.details = "uwu~ im playing as " + mc.player.getName();
        DiscordUtil.presence.state = "Main Menu";
        DiscordUtil.presence.largeImageKey = "wurstplus";
        DiscordUtil.presence.largeImageText = Sodaware.WURSTPLUS_VERSION;
        DiscordUtil.rpc.Discord_UpdatePresence(DiscordUtil.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                	DiscordUtil.rpc.Discord_RunCallbacks();
                	DiscordUtil.details = "uwu~ im playing with " + mc.player.getName();
                	DiscordUtil.state = ":3";
                    if (DiscordUtil.mc.isIntegratedServerRunning()) {
                    	DiscordUtil.state = "Playing on Singleplayer";
                    }
                    else if (DiscordUtil.mc.getCurrentServerData() != null) {
                        if (!DiscordUtil.mc.getCurrentServerData().serverIP.equals("")) {
                        	DiscordUtil.state = ":3~ " + DiscordUtil.mc.getCurrentServerData().serverIP;
                        }
                    }
                    else {
                    	DiscordUtil.state = "watching femboys :3~";
                    }
                    if (!DiscordUtil.details.equals(DiscordUtil.presence.details) || !DiscordUtil.state.equals(DiscordUtil.presence.state)) {
                    	DiscordUtil.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordUtil.presence.details = DiscordUtil.details;
                    DiscordUtil.presence.state = DiscordUtil.state;
                    DiscordUtil.rpc.Discord_UpdatePresence(DiscordUtil.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
    
    static {
        mc = Minecraft.getMinecraft();
        rpc = DiscordRPC.INSTANCE;
        DiscordUtil.presence = new DiscordRichPresence();
    }
    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}

