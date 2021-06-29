package me.soda.sodaware.client.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Iterator;

public class PearlNotifier extends WurstplusHack {
    private Entity enderPearl;
    private boolean flag;
    private final HashMap list = new HashMap();

    public PearlNotifier() {
        super( WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Pearl Notifier";
        this.tag = "PearlNotifier";
        this.description = "Notify when someones throws a pearl";
    }

    public void enable() {
        this.flag = true;
    }

    public void update() {
        if (mc.world != null && mc.player != null) {
            this.enderPearl = null;
            Iterator var1 = mc.world.loadedEntityList.iterator();

            while(var1.hasNext()) {
                Entity e = (Entity)var1.next();
                if (e instanceof EntityEnderPearl) {
                    this.enderPearl = e;
                    break;
                }
            }

            if (this.enderPearl == null) {
                this.flag = true;
            } else {
                EntityPlayer closestPlayer = null;
                Iterator var5 = mc.world.playerEntities.iterator();

                while(var5.hasNext()) {
                    EntityPlayer entity = (EntityPlayer)var5.next();
                    if (closestPlayer == null) {
                        closestPlayer = entity;
                    } else if (closestPlayer.getDistance(this.enderPearl) > entity.getDistance(this.enderPearl)) {
                        closestPlayer = entity;
                    }
                }

                if (closestPlayer == mc.player) {
                    this.flag = false;
                }

                if (closestPlayer != null && this.flag) {
                    String faceing = this.enderPearl.getHorizontalFacing().toString();
                    if (faceing.equals("west")) {
                        faceing = "east";
                    } else if (faceing.equals("east")) {
                        faceing = "west";
                    }

                    WurstplusMessageUtil.send_client_message(ChatFormatting.RED + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " thrown a pearl heading " + faceing); 
                    this.flag = false;
                }
            }
        }

    }
}
