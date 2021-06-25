package me.soda.sodaware.client.modules.movement;

import me.soda.sodaware.client.event.events.WurstplusEventMove;
import me.soda.sodaware.client.event.events.WurstplusEventPacket;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import static me.soda.sodaware.client.util.WurstplusMathUtil.movement_speed;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

public class Freecam extends WurstplusHack {

    public Freecam() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "Freecam";
        this.tag         = "Freecam";
        this.description = "pog";
    }

    double x;
    double y;
    double z;

    float pitch;
    float yaw;

    EntityOtherPlayerMP soul;

    Entity riding_entity;

    boolean is_riding;

    WurstplusSetting speed_h = create("Horizontal Speed", "Horizontal Speed", 2, 1, 4);
    WurstplusSetting speed_v = create("Vertical Speed", "Vertical Speed", 2, 1, 4);
    WurstplusSetting cancelpackets = create("Cancel Packets", "cancel packets", true);

    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {
        if (cancelpackets.get_value(true) && (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput)) {
            event.cancel();
        }
    });

    @EventHandler
    Listener<WurstplusEventMove> listener_move = new Listener<>(event -> {
        mc.player.noClip = true;
    });

    @EventHandler
    Listener<PlayerSPPushOutOfBlocksEvent> listener_push = new Listener<>(event -> {
        event.setCanceled(true);
    });

    @EventHandler
    Listener<WurstplusEventPacket.SendPacket> listener_packet = new Listener<>(event -> {
        if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput) {
            event.cancel();
        }
    });

    @Override
    public void enable() {
        if (mc.player != null && mc.world != null) {
            is_riding = mc.player.getRidingEntity() != null;

            if (mc.player.getRidingEntity() == null) {
                x = mc.player.posX;
                y = mc.player.posY;
                z = mc.player.posZ;
            } else {
                riding_entity = mc.player.getRidingEntity();

                mc.player.dismountRidingEntity();
            }

            pitch = mc.player.rotationPitch;
            yaw   = mc.player.rotationYaw;

            soul = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());

            soul.copyLocationAndAnglesFrom(mc.player);

            soul.rotationYawHead = mc.player.rotationYawHead;

            mc.world.addEntityToWorld(-100, soul);

            mc.player.noClip = true;
        }
    }

    @Override
    public void disable() {
        if (mc.player != null && mc.world != null) {
            mc.player.setPositionAndRotation(x, y, z, yaw, pitch);

            mc.world.removeEntityFromWorld(-100);

            // Remove soul power;
            soul  = null;
            x     = 0;
            y     = 0;
            z     = 0;
            yaw   = 0;
            pitch = 0;

            mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0;

            if (is_riding) {
                mc.player.startRiding(riding_entity, true);
            }
        }
    }

    @Override
    public void update() {
        if (mc.player != null && mc.world != null) {
            mc.player.noClip               = true;

            mc.player.setVelocity(0, 0, 0);

            mc.player.renderArmPitch     = 5000;
            mc.player.jumpMovementFactor = 0.5f;

            final double[] static_mov = movement_speed(speed_h.get_value(1));

            if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                mc.player.motionX = static_mov[0];
                mc.player.motionZ = static_mov[1];
            } else {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }

            mc.player.setSprinting(false);

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY += speed_v.get_value(1);
            }

            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motionY -= speed_v.get_value(1);
            }
        }
    }
}
