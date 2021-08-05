package me.soda.sodaware;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.auth.*;
import me.soda.sodaware.client.event.WurstplusEventHandler;
import me.soda.sodaware.client.event.WurstplusEventRegister;
import me.soda.sodaware.client.guiscreen.WurstplusGUI;
import me.soda.sodaware.client.guiscreen.WurstplusHUD;
import me.soda.sodaware.client.manager.*;
import me.soda.sodaware.client.util.DiscordUtil;
import me.soda.sodaware.client.util.GLSLSandboxShader;
import me.soda.sodaware.client.util.WurstplusJson;
import me.soda.turok.Turok;
import me.soda.turok.task.Font;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Mod(modid = "sodaware", version = "1.6")
public class Sodaware
{
	public static GLSLSandboxShader backgroundShader;
	public static long initTime;
	public static Sodaware INSTANCE;
	@Mod.Instance
	private static Sodaware MASTER;
	public static final String WURSTPLUS_NAME = "sodaware";
	public static final String WURSTPLUS_VERSION = "2.1";
	public static final String WURSTPLUS_SIGN = " ";
	public static final int WURSTPLUS_KEY_GUI = 54;
	public static final int WURSTPLUS_KEY_DELETE = 211;
	public static final int WURSTPLUS_KEY_GUI_ESCAPE = 1;
	public static Logger wurstplus_register_log;
	public static final String CAPES_JSON = "https://soda.amogus.monster/capes";
	public static final String DONATORS_JSON = "https://soda.amogus.monster/discordusers";
	private static WurstplusSettingManager setting_manager;
	private static WurstplusConfigManager config_manager;
	private static WurstplusModuleManager module_manager;
	private static WurstplusHUDManager hud_manager;
	public static WurstplusGUI click_gui;
	public static WurstplusHUD click_hud;
	public static int client_r = 2150;
	public static int client_g = 0;
	public static int client_b = 103;
	public static Turok turok;
	public static ChatFormatting g;
	public static ChatFormatting p;
	public static ChatFormatting r;
	public static ChatFormatting l;
	public static List<String> hwidList = new ArrayList<>();
	public static boolean Sent = false;
	public static boolean NotAllowed = false;


	public Sodaware() {
		INSTANCE = this;
	}

    @Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		this.Verify();
		this.init_log("sodaware");
		WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();
		send_minecraft_log("initialising managers");
		Sodaware.setting_manager = new WurstplusSettingManager();
		Sodaware.config_manager = new WurstplusConfigManager();
		Sodaware.module_manager = new WurstplusModuleManager();
		Sodaware.hud_manager = new WurstplusHUDManager();
		final WurstplusEventManager event_manager = new WurstplusEventManager();
		final WurstplusCommandManager command_manager = new WurstplusCommandManager();
		send_minecraft_log("done");
		send_minecraft_log("initialising guis");
		Display.setTitle("sodaware");
		Sodaware.click_gui = new WurstplusGUI();
		Sodaware.click_hud = new WurstplusHUD();
		send_minecraft_log("done");
		send_minecraft_log("initialising skidded framework");
		Sodaware.turok = new Turok("Turok");
		send_minecraft_log("done");
		send_minecraft_log("initialising commands and events");
		WurstplusEventRegister.register_command_manager(command_manager);
		WurstplusEventRegister.register_module_manager(event_manager);
		send_minecraft_log("done");
		send_minecraft_log("loading settings");
		Sodaware.config_manager.load_settings();
		send_minecraft_log("done");
		if (Sodaware.module_manager.get_module_with_tag("GUI").is_active()) {
			Sodaware.module_manager.get_module_with_tag("GUI").set_active(false);
		}
		if (Sodaware.module_manager.get_module_with_tag("Freecam").is_active()) {
			Sodaware.module_manager.get_module_with_tag("Freecam").set_active(false);
		}
		if (Sodaware.module_manager.get_module_with_tag("HUD").is_active()) {
			Sodaware.module_manager.get_module_with_tag("HUD").set_active(false);
		}
		send_minecraft_log("client started");
		send_minecraft_log("boom");
		new DiscordUtil();
		send_minecraft_log("Loading USERS for RPC!");
	}

	public void Verify() {
		hwidList = NetworkUtil.getHWIDList();

		if (!hwidList.contains("true")) {
			FrameUtil.Display();
			throw new NoStackTraceThrowable("Verify HWID Failed!");
		}
	}

	public void init_log(final String name) {
		Sodaware.wurstplus_register_log = LogManager.getLogger(name);
		send_minecraft_log("starting sodaware");
	}

	public static boolean isOnline() {
		try {
			URL url = new URL("http://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}




	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) throws IOException {
		SodaRPC.setCustomIcons();
	}

	public static void send_minecraft_log(final String log) {
		Sodaware.wurstplus_register_log.info(log);
	}

	public static String get_name() {
		return "sodaware";
	}

	public static String get_version() {
		return "2.0";
	}

	public static String get_actual_user() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public static WurstplusConfigManager get_config_manager() {
		return Sodaware.config_manager;
	}

	public static WurstplusModuleManager get_hack_manager() {
		return Sodaware.module_manager;
	}

	public static WurstplusSettingManager get_setting_manager() {
		return Sodaware.setting_manager;
	}

	public static WurstplusHUDManager get_hud_manager() {
		return Sodaware.hud_manager;
	}

	public static WurstplusModuleManager get_module_manager() {
		return Sodaware.module_manager;
	}

	public static WurstplusEventHandler get_event_handler() {
		return WurstplusEventHandler.INSTANCE;
	}

	public static String smoth(final String base) {
		return Font.smoth(base);
	}

	static {
		Sodaware.g = ChatFormatting.DARK_GRAY;
		Sodaware.p = ChatFormatting.DARK_PURPLE;
		Sodaware.r = ChatFormatting.RESET;
		Sodaware.l = ChatFormatting.RED;
		Sodaware.hwidList = new ArrayList<String>();
	}
}