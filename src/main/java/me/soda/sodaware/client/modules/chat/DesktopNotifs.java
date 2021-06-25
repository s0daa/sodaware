package me.soda.sodaware.client.modules.chat;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import net.minecraft.client.Minecraft;

import java.awt.*;


public class DesktopNotifs extends WurstplusHack {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public DesktopNotifs() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "DesktopNotifs";
        this.tag = "DesktopNotifs";
        this.description = "get notifications on your desktop";
    }

    public static void sendNotification(String message, TrayIcon.MessageType messageType){
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("access.png");
        TrayIcon icon = new TrayIcon(image, "Sodaware");
        icon.setImageAutoSize(true);
        icon.setToolTip("Sodaware");
        try { tray.add(icon); }
        catch (AWTException e) { e.printStackTrace(); }
        icon.displayMessage("Sodaware", message, messageType);
    }
}
