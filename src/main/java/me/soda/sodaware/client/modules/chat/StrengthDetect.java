package me.soda.sodaware.client.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.WurstplusMessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class StrengthDetect
        extends WurstplusHack {
    private final Set<EntityPlayer> str;
    public static final Minecraft mc = Minecraft.getMinecraft();

    public
    StrengthDetect () {
        super( WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Strenght Detect";
        this.tag = "StrenghtDect";
        this.description = "detects if someone has strenght";
        this.str = Collections.newSetFromMap(new WeakHashMap());
    }

    @Override
    public void update() {
        for (EntityPlayer entityPlayer : me.soda.sodaware.client.modules.chat.StrengthDetect.mc.world.playerEntities) {
            if (entityPlayer.equals( me.soda.sodaware.client.modules.chat.StrengthDetect.mc.player)) continue;
            if (entityPlayer.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(entityPlayer)) {
                WurstplusMessageUtil.send_client_message(ChatFormatting.RED + "" + ChatFormatting.BOLD + "StrengthDetect" + ChatFormatting.RESET + ChatFormatting.GRAY + " > " + ChatFormatting.RESET + entityPlayer.getDisplayNameString() + " Has Strength");
                this.str.add(entityPlayer);
            }
            if (!this.str.contains(entityPlayer) || entityPlayer.isPotionActive(MobEffects.STRENGTH)) continue;
            WurstplusMessageUtil.send_client_message(ChatFormatting.RED + "" + ChatFormatting.BOLD + "StrengthDetect" + ChatFormatting.RESET + ChatFormatting.GRAY + " > " + ChatFormatting.RESET + entityPlayer.getDisplayNameString() + " Has Ran Out Of Strength");
            this.str.remove(entityPlayer);
        }
    }
}