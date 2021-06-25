package me.soda.sodaware.client.util;


import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WurstplusEntityUtil {

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void attackEntity(final Entity entity, final boolean packet, final WurstplusSetting setting) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketUseEntity(entity));
        }
        else {
            mc.playerController.attackEntity(mc.player, entity);
        }
        if (setting.in("Mainhand") || setting.in("Both")) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (setting.in("Offhand") || setting.in("Both")) {
            mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }

    public static boolean isLiving(Entity e)
    {
        return e instanceof EntityLivingBase;
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z)
    {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, 0 * y,
                (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks)
    {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks)
    {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
                .add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks)
    {
        return getInterpolatedPos(entity, ticks).subtract(mc.getRenderManager().renderPosX,
                mc.getRenderManager().renderPosY,
                mc.getRenderManager().renderPosZ);
    }

    public static BlockPos is_cityable(final EntityPlayer player, final boolean end_crystal) {

        BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);

        if (mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.north();
            }
            else if (mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR) {
                return pos.north();
            }
        }
        if (mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.east();
            }
            else if (mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR) {
                return pos.east();
            }
        }
        if (mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.south();
            }
            else if (mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR) {
                return pos.south();
            }

        }
        if (mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.west();
            }
            else if (mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.AIR) {
                return pos.west();
            }
        }

        return null;

    }

    public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z)
    {
        double d0 = p_X - x;
        double d1 = p_Y - y;
        double d2 = p_Z - z;
        return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos)
    {
        return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
    }


    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        // to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]
                { yaw, pitch };
    }


}