package me.soda.sodaware.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketPlayer;

public class WurstplusPlayerUtil {
    
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
    
    public enum FacingDirection {
        North,
        South,
        East,
        West,
    }

    public static FacingDirection GetFacing() {
        switch (MathHelper.floor((double) (mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7)
        {
            case 0:
            case 1:
                return FacingDirection.South;
            case 2:
            case 3:
                return FacingDirection.West;
            case 4:
            case 5:
                return FacingDirection.North;
            case 6:
            case 7:
                return FacingDirection.East;
        }
        return FacingDirection.North;
    }

    public double getMoveYaw(boolean flag) {
        float strafe = 90 * mc.player.moveStrafing;
        strafe *= mc.player.moveForward != 0 ? mc.player.moveForward * 0.5 : 1;
        float yaw = mc.player.rotationYaw - strafe;
        yaw -= mc.player.moveForward < 0 ? 180 : 0;

        if(flag)return yaw; //meme
        return Math.toRadians(yaw);
    }

    public double getSpeed() {
        return Math.hypot(mc.player.motionX, mc.player.motionZ);
    }

    public void setSpeed(Double speed) {
        Double yaw = getMoveYaw(false);
        mc.player.motionX = -Math.sin(yaw) * speed;
        mc.player.motionZ = Math.cos(yaw) * speed;
    }

    public void addSpeed(Double speed, boolean flag) {
        Double yaw = getMoveYaw(flag);
        mc.player.motionX -= Math.sin(yaw) * speed;
        mc.player.motionZ += Math.cos(yaw) * speed;
    }

    public void setTimer(float speed) {
        mc.timer.tickLength = 50 / speed;
    }

    public void step(double height, double[] offset, boolean flag, float speed) {
        for(int i = 0; i < offset.length; i++) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset[i], mc.player.posZ, mc.player.onGround));
        }
        if(flag)setTimer(speed);
        mc.player.posY += height;
    }
    public static void PacketFacePitchAndYaw(float p_Pitch, float p_Yaw)
    {
        boolean l_IsSprinting = mc.player.isSprinting();

        if (l_IsSprinting != mc.player.serverSprintState)
        {
            if (l_IsSprinting)
            {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else
            {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }

            mc.player.serverSprintState = l_IsSprinting;
        }

        boolean l_IsSneaking = mc.player.isSneaking();

        if (l_IsSneaking != mc.player.serverSneakState)
        {
            if (l_IsSneaking)
            {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else
            {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }

            mc.player.serverSneakState = l_IsSneaking;
        }
    }

}