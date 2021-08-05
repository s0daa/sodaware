package me.soda.sodaware.mixins;

import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.modules.render.TitleScreenShader;
import me.soda.sodaware.client.util.GLSLSandboxShader;
import me.soda.sodaware.client.util.GLSLShaders;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMainMenu.class})
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Shadow protected abstract void renderSkybox(int mouseX, int mouseY, float partialTicks);

    @Inject(method = "initGui", at = @At(value = "RETURN"), cancellable = true)
    public void initGui(CallbackInfo info) {
        try {
                Random random = new Random();
                GLSLShaders[] shaders = GLSLShaders.values();
                Sodaware.backgroundShader = new GLSLSandboxShader(shaders[random.nextInt(shaders.length)].get());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load background shader", e);
        }
        int i = height / 4 + 48;
        this.buttonList.add(new GuiButton(932, 5, 150, 98, 20, "2b2tpvp.net"));
        this.buttonList.add(new GuiButton(284, 5, 174, 98, 20, "2b2t.org"));
        this.buttonList.add(new GuiButton(953, 5, 198, 98, 20, "eu cc"));
        this.buttonList.add(new GuiButton(991, 5, 222, 98, 20, "us cc"));
        Sodaware.initTime = System.currentTimeMillis();
    }

    @Inject(method = "actionPerformed", at = @At(value = "HEAD"), cancellable = true)
    public void actionPerformed(GuiButton button, CallbackInfo info) {
        if(button.id == 932) {
            this.mc.displayGuiScreen(new GuiConnecting(this, mc, "2b2tpvp.net", 25565));
        }
        if(button.id == 284) {
            this.mc.displayGuiScreen(new GuiConnecting(this, mc, "2b2t.org", 25565));
        }
        if(button.id == 953) {
            this.mc.displayGuiScreen(new GuiConnecting(this, mc, "eu.crystalpvp.cc", 25565));
        }
        if(button.id == 991) {
            this.mc.displayGuiScreen(new GuiConnecting(this, mc, "us.crystalpvp.cc", 25565));
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;renderSkybox(IIF)V"))
    private void voided(GuiMainMenu guiMainMenu, int mouseX, int mouseY, float partialTicks) {
        if (!Sodaware.get_module_manager().get_module_with_tag("TitleScreenShader").is_active()) {
            renderSkybox(mouseX, mouseY, partialTicks);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 0))
    private void noRect1(GuiMainMenu guiMainMenu, int left, int top, int right, int bottom, int startColor, int endColor) {
        if (!Sodaware.get_module_manager().get_module_with_tag("TitleScreenShader").is_active()) {
            drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 1))
    private void noRect2(GuiMainMenu guiMainMenu, int left, int top, int right, int bottom, int startColor, int endColor) {
        if (!Sodaware.get_module_manager().get_module_with_tag("TitleScreenShader").is_active()) {
            drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }


    @Inject(method = "drawScreen", at = @At(value = "HEAD"), cancellable = true)
    public void drawScreenShader(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (Sodaware.get_module_manager().get_module_with_tag("TitleScreenShader").is_active()) {
            GlStateManager.disableCull();
            Sodaware.backgroundShader.useShader(this.width*2, this.height*2, mouseX*2, mouseY*2, (System.currentTimeMillis() - Sodaware.initTime) / 1000f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(-1f, -1f);
            GL11.glVertex2f(-1f, 1f);
            GL11.glVertex2f(1f, 1f);
            GL11.glVertex2f(1f, -1f);
            GL11.glEnd();
            GL20.glUseProgram(0);
        }
    }
}