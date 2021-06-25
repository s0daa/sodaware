package me.soda.sodaware.client.modules.capes;


import com.google.gson.Gson;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.soda.sodaware.client.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

//mhm sexy cat cape mhm

public class Capes extends WurstplusHack {

    public static boolean overrideOtherCapes = true;

    public Capes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
        INSTANCE = this;
    }

    public static me.soda.sodaware.client.modules.capes.Capes INSTANCE;

    private Map<String, me.soda.sodaware.client.modules.capes.Capes.CachedCape> allCapes = Collections.unmodifiableMap(new HashMap<>());

    private boolean hasBegunDownload = false;

    public void enable(){
        if (Sodaware.isOnline()) {
            if (!hasBegunDownload) {
                hasBegunDownload = true;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            HttpsURLConnection connection = (HttpsURLConnection) new URL(Sodaware.CAPES_JSON).openConnection();
                            connection.connect();
                            me.soda.sodaware.client.modules.capes.Capes.CapeUser[] capeUser = new Gson().fromJson(new InputStreamReader(connection.getInputStream()), me.soda.sodaware.client.modules.capes.Capes.CapeUser[].class);
                            connection.disconnect();
                            HashMap<String, me.soda.sodaware.client.modules.capes.Capes.CachedCape> capesByURL = new HashMap<>();
                            HashMap<String, me.soda.sodaware.client.modules.capes.Capes.CachedCape> capesByUUID = new HashMap<>();
                            for (me.soda.sodaware.client.modules.capes.Capes.CapeUser cape : capeUser) {
                                me.soda.sodaware.client.modules.capes.Capes.CachedCape o = capesByURL.get(cape.url);
                                if (o == null) {
                                    o = new CachedCape(cape);
                                    capesByURL.put(cape.url, o);
                                }
                                capesByUUID.put(cape.uuid, o);
                            }
                            allCapes = Collections.unmodifiableMap(capesByUUID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } else {
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Not connected to the internet!", false);
            this.set_disable();
        }
    }
    public static ResourceLocation getCapeResource(AbstractClientPlayer player) {
        me.soda.sodaware.client.modules.capes.Capes.CachedCape result = INSTANCE.allCapes.get(player.getUniqueID().toString());
        if (result == null)
            return null;
        result.request();
        return result.location;
    }

    private static BufferedImage parseCape(BufferedImage img)  {
        int imageWidth = 64;
        int imageHeight = 32;

        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        while (imageWidth < srcWidth || imageHeight < srcHeight) {
            imageWidth *= 2;
            imageHeight *= 2;
        }
        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return imgNew;
    }

    private static String formatUUID(String uuid) {
        return uuid.replaceAll("-", "");
    }

    // This is the raw Gson structure as seen in the assets
    public class CapeUser {
        public String uuid;
        public String url;
    }

    // This is the shared cape instance.
    private static class CachedCape {
        public final ResourceLocation location;
        public final String url;
        private boolean hasRequested = false;

        public CachedCape(me.soda.sodaware.client.modules.capes.Capes.CapeUser cape) {
            location = new ResourceLocation("capes/soda/" + formatUUID(cape.uuid));
            url = cape.url;
        }

        public void request() {
            if (hasRequested)
                return;
            hasRequested = true;
            // This is bindTexture moved to runtime (but still on the main thread)
            IImageBuffer iib = new IImageBuffer() {
                @Override
                public BufferedImage parseUserSkin(BufferedImage image) {
                    return parseCape(image);
                }

                @Override
                public void skinAvailable() {}
            };

            TextureManager textureManager = Wrapper.getMinecraft().getTextureManager();
            textureManager.getTexture(location);
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, url, null, iib);
            textureManager.loadTexture(location, textureCape);
        }
    }
}
