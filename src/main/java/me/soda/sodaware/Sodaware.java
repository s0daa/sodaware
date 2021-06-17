package me.soda.sodaware;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.auth.FrameUtil;
import me.soda.sodaware.client.auth.HWIDUtil;
import me.soda.sodaware.client.auth.NetworkUtil;
import me.soda.sodaware.client.auth.NoStackTraceThrowable;
import me.soda.sodaware.client.event.WurstplusEventHandler;
import me.soda.sodaware.client.event.WurstplusEventRegister;
import me.soda.sodaware.client.guiscreen.WurstplusGUI;
import me.soda.sodaware.client.guiscreen.WurstplusHUD;
import me.soda.sodaware.client.manager.*;
import me.soda.sodaware.client.util.DiscordUtil;
import me.soda.turok.Turok;
import me.soda.turok.task.Font;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "sodaware", version = "1.6")
public class Sodaware
{
	@Mod.Instance
	private static Sodaware MASTER;
	public static final String WURSTPLUS_NAME = "sodaware";
	public static final String WURSTPLUS_VERSION = "1.6";
	public static final String WURSTPLUS_SIGN = " ";
	public static final int WURSTPLUS_KEY_GUI = 54;
	public static final int WURSTPLUS_KEY_DELETE = 211;
	public static final int WURSTPLUS_KEY_GUI_ESCAPE = 1;
	public static Logger wurstplus_register_log;
	public static final String CAPES_JSON = "https://raw.githubusercontent.com/kars0nn/sodaAssets/main/capes.json";
	public static final String DONATORS_JSON = "https://raw.githubusercontent.com/kars0nn/sodaAssets/main/users.json";
	private static WurstplusSettingManager setting_manager;
	private static WurstplusConfigManager config_manager;
	private static WurstplusModuleManager module_manager;
	private static WurstplusHUDManager hud_manager;
	public static WurstplusGUI click_gui;
	public static WurstplusHUD click_hud;
	public static Turok turok;
	public static ChatFormatting g;
	public static ChatFormatting p;
	public static ChatFormatting r;
	public static List<String> hwidList;
	public static final String KEY = "sodabitchnigga";
	public static final String HWID_URL = "https://pastebin.com/raw/nMTE5FCs";

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
		if (Sodaware.module_manager.get_module_with_tag("HUD").is_active()) {
			Sodaware.module_manager.get_module_with_tag("HUD").set_active(false);
		}
		send_minecraft_log("client started");
		send_minecraft_log("boom");
		new DiscordUtil();
		send_minecraft_log("Loading USERS for RPC!");
	}

	public void Verify() {
		Sodaware.hwidList = (List<String>)NetworkUtil.getHWIDList();
		if (!Sodaware.hwidList.contains(HWIDUtil.getEncryptedHWID("sodabitchnigga"))) {
			FrameUtil.Display();
			throw new NoStackTraceThrowable("Verify HWID Failed!");
		}
	}

	public void init_log(final String name) {
		Sodaware.wurstplus_register_log = LogManager.getLogger(name);
		send_minecraft_log("starting sodaware");
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		SodaRPC.setCustomIcons();
	}

	public static void send_minecraft_log(final String log) {
		Sodaware.wurstplus_register_log.info(log);
	}

	public static String get_name() {
		return "sodaware";
	}

	public static String get_version() {
		return "1.6";
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
		Sodaware.hwidList = new ArrayList<String>();
	}
}