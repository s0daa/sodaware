package me.soda.sodaware.client.modules.misc;


import me.soda.sodaware.client.event.events.WurstplusEventMotionUpdate;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.event.events.WurstplusEventCancellable.Era;
import me.soda.sodaware.client.util.*;
import me.soda.sodaware.client.util.WurstplusBlockInteractHelper.PlaceResult;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;

public class EnderChestFarmer extends WurstplusHack
{
    public EnderChestFarmer() {

        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "EChestFarmer";
        this.tag = "EChestFarmer";
        this.description = "farm echest lmao";
    }

    WurstplusSetting radius = create("Radius", "Radius", 5, 1, 10);
    WurstplusSetting delay = create("Delay", "Delay", 1f, 0f, 10f);

    private Timer PlaceTimer = new Timer();

    @EventHandler
    private Listener<WurstplusEventMotionUpdate> OnPlayerUpdate = new Listener<>(p_Event ->
    {

        BlockPos l_ClosestPos = WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), radius.get_value(1), radius.get_value(1), false, true, 0).stream()
                .filter(p_Pos -> IsValidBlockPos(p_Pos))
                .min(Comparator.comparing(p_Pos -> WurstplusEntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos)))
                .orElse(null);

        if (l_ClosestPos != null)
        {
            boolean hasPickaxe = mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;

            if (!hasPickaxe)
            {
                for (int i = 0; i < 9; ++i)
                {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);

                    if (stack.isEmpty())
                        continue;

                    if (stack.getItem() == Items.DIAMOND_PICKAXE)
                    {
                        hasPickaxe = true;
                        mc.player.inventory.currentItem = i;
                        mc.playerController.updateController();
                        break;
                    }
                }
            }

            if (!hasPickaxe) {
                WurstplusMessageUtil.send_client_error_message("No Pickaxe!");
                this.set_disable();
                return;
            }

            p_Event.cancel();

            final double l_Pos[] =  WurstplusEntityUtil.calculateLookAt(
                    l_ClosestPos.getX() + 0.5,
                    l_ClosestPos.getY() - 0.5,
                    l_ClosestPos.getZ() + 0.5,
                    mc.player);

            WurstplusPlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);

            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketPlayerDigging(
                    CPacketPlayerDigging.Action.START_DESTROY_BLOCK, l_ClosestPos, EnumFacing.UP));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    l_ClosestPos, EnumFacing.UP));
        }
        else
        {
            if (!PlaceTimer.passed(delay.get_value(1)*1000))
                return;

            PlaceTimer.reset();

            if (!IsCurrItemEnderChest())
            {
                int slot = GetEnderChestSlot();

                if (slot == -1)
                    return;

                mc.player.inventory.currentItem = slot;
                mc.playerController.updateController();
            }

            for (BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), radius.get_value(1), radius.get_value(1), false, true, 0))
            {
                PlaceResult result = WurstplusBlockInteractHelper.place(pos, radius.get_value(1), true, false);

                if (result == PlaceResult.Placed)
                {
                    p_Event.cancel();

                    final double rotations[] =  WurstplusEntityUtil.calculateLookAt(
                            pos.getX() + 0.5,
                            pos.getY() - 0.5,
                            pos.getZ() + 0.5,
                            mc.player);

                    WurstplusPlayerUtil.PacketFacePitchAndYaw((float)rotations[1], (float)rotations[0]);
                    return;
                }
            }
        }
    });

    private boolean IsValidBlockPos(final BlockPos p_Pos)
    {
        IBlockState l_State = mc.world.getBlockState(p_Pos);

        if (l_State.getBlock() instanceof BlockEnderChest)
            return true;

        return false;
    }

    private boolean IsCurrItemEnderChest()
    {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)
        {
            final ItemBlock block = (ItemBlock) mc.player.getHeldItemMainhand().getItem();
            return block.getBlock() == Blocks.ENDER_CHEST;
        }

        return false;
    }

    private int GetEnderChestSlot()
    {
        for (int i = 0; i < 9; ++i)
        {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof ItemBlock))
                continue;

            final ItemBlock block = (ItemBlock) stack.getItem();
            if (block.getBlock() == Blocks.ENDER_CHEST)
                return i;
        }

        return -1;
    }
}
