package me.soda.sodaware;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.soda.sodaware.client.util.DiscordUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class SodaRPC {
    private static final String ClientId = "854439224015454228";
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence presence = new DiscordRichPresence();
    private static String details = "";
    private static String state;
    public static int players;
    public static int maxPlayers;
    public static int players2;
    public static int maxPlayers2;
    public static ServerData svr;
    public static String[] popInfo;

    public static void init(){
        if(Sodaware.isOnline() == false){
            return;
        }
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = "Main Menu";
        if (!details.equals(presence.details) || !state.equals(presence.state)) {
            presence.startTimestamp = System.currentTimeMillis() / 1000L;
        }
        presence.state = "waiting...";
        presence.largeImageKey = "soda";
        presence.largeImageText = Sodaware.WURSTPLUS_VERSION;
        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "zzzz";
                    state = "zzzzz";
                    players = 0;
                    maxPlayers = 0;
                    if (mc.isIntegratedServerRunning()) {
                        details = mc.player.getName() + " | singleplayer :3";
                        state = ":3";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        svr = mc.getCurrentServerData();
                        if (!svr.serverIP.equals("")) {
                            details = mc.player.getName() + " | access ";
                            state = "on " + svr.serverIP + "";
                            if (svr.populationInfo != null) {
                                popInfo = svr.populationInfo.split("/");
                                if (popInfo.length > 2) {
                                    players2 = Integer.parseInt(popInfo[0]);
                                    maxPlayers2 = Integer.parseInt(popInfo[1]);
                                }
                            }
                        }
                    } else {
                        details = "sodaware | main menu";
                        state = "prolly jerking off";
                    }
                    presence.details = details;
                    presence.state = state;
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);
                } catch(Exception e2){
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch(InterruptedException e3){
                    e3.printStackTrace();
                }
            }
            return;
        }, "Discord-RPC-Callback-Handler").start();
    }

    public static void end() {
        rpc.Discord_Shutdown();
        rpc.Discord_ClearPresence();
    }

    public static void setCustomIcons() {
        if (DiscordUtil.INSTANCE.customUsers != null) {
            for (DiscordUtil.CustomUser user : DiscordUtil.INSTANCE.customUsers) {
                if (user.uuid.equalsIgnoreCase(Minecraft.getMinecraft().session.getProfile().getId().toString())) {
                    switch (Integer.parseInt(user.type)) {
                        case 0: {
                            presence.smallImageKey = "gay";
                            presence.smallImageText = "gay.";
                            break;
                        }
                        case 1: {
                            presence.smallImageKey = "dev";
                            presence.smallImageText = "dev owo";
                            break;
                        }
                        case 2: {
                            presence.smallImageKey = "beta";
                            presence.smallImageText = "beta <3";
                            break;
                        }
                        case 3: {
                            presence.smallImageKey = "user";
                            presence.smallImageText = "access";
                            break;
                        }
                    }
                }
            }
        }
    }
}

