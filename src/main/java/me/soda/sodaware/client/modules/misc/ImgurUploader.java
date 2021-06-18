package me.soda.sodaware.client.modules.misc;


import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.KeyInputUpload;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ImgurUploader extends WurstplusHack {

    public ImgurUploader() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "AutoImgur";
        this.tag = "ImgurUploader";
        this.description = "uploads images to imgur F2";
    }

    public static KeyBinding screenShot;

    public void enable() {
                screenShot = new KeyBinding("key.imgurshot", Keyboard.KEY_F2, "key.categories.imgurmod");
                MinecraftForge.EVENT_BUS.register(new KeyInputUpload(screenShot));
                ClientRegistry.registerKeyBinding(screenShot);
    }
}
