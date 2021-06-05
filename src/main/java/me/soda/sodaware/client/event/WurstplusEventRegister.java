package me.soda.sodaware.client.event;

import me.soda.sodaware.client.manager.WurstplusCommandManager;
import me.soda.sodaware.client.manager.WurstplusEventManager;
import net.minecraftforge.common.MinecraftForge;


public class WurstplusEventRegister {
	public static void register_command_manager(WurstplusCommandManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}

	public static void register_module_manager(WurstplusEventManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}
}