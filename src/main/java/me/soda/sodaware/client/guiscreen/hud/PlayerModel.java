package me.soda.sodaware.client.guiscreen.hud;

import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.soda.sodaware.client.util.WurstplusMathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.math.MathHelper;

public class PlayerModel  extends WurstplusPinnable {

    public PlayerModel() {
        super("PlayerModel", "PlayerModel", 1, 0, 0);
    }

    public void render()
    {

        if (mc.world == null)
            return;

        float yaw = mc.player.rotationYaw;
        float pitch = mc.player.rotationPitch;

        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        GuiInventory.drawEntityOnScreen(get_x()+25, get_y()+80, 40, -yaw, -pitch, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        this.set_width(50);
        this.set_height(85);
    }

    private float interpolateAndWrap(float prev, float current) {
        return MathHelper.wrapDegrees(prev + (current - prev) * pTicks());
    }

    public static float pTicks() {
        float var0;
        if (Minecraft.getMinecraft().isGamePaused()) {
            var0 = Minecraft.getMinecraft().getRenderPartialTicks();
        } else {
            var0 = Minecraft.getMinecraft().getRenderPartialTicks();
        }
        return var0;
    }
}
