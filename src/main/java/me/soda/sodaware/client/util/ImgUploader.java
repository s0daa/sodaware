package me.soda.sodaware.client.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.modules.misc.ImgurUploader;
import net.minecraft.client.Minecraft;

public class ImgUploader implements Runnable {

    BufferedImage img;

    public ImgUploader(BufferedImage img) {
        this.img = img;
    }

    public void run() {
        if(Sodaware.isOnline()){
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Uploading image...", true);

            try {
                JsonElement jelement = new JsonParser().parse(getImgurContent("22835aafce1a83e",img));
                JsonObject  jobject = jelement.getAsJsonObject();
                jobject = jobject.getAsJsonObject("data");

                StringSelection selection = new StringSelection(jobject.get("link").toString().replaceAll("\"", ""));
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Copied to clipboard!", false);

            }
            catch(Exception e) {
                e.printStackTrace();
                WurstplusMessageUtil.send_client_message("Upload FAILED bitch!");
            }
        } else {
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Not connected to the internet!", false);
        }

    }


    public static String getImgurContent(String clientID, BufferedImage image) throws IOException {
        URL url;
        url = new URL("https://api.imgur.com/3/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArray);
        byte[] byteImage = byteArray.toByteArray();
        String dataImage = Base64.getEncoder().encodeToString(byteImage);
        String data = URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(dataImage, "UTF-8");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Client-ID " + clientID);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.connect();
        StringBuilder stb = new StringBuilder();
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        return stb.toString();
    }
}
