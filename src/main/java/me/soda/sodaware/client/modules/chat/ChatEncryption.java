package me.soda.sodaware.client.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.event.events.WurstplusEventPacket;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.WurstplusMessageUtil;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.manager.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import me.zero.alpine.fork.listener.EventHook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ChatEncryption extends WurstplusHack {

    public ChatEncryption() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name        = "ChatEncryption";
        this.tag         = "ChatEncryption";
        this.description = "ChatEncryption";
    }
    private final Pattern CHAT_PATTERN = Pattern.compile("<.*?> ");
    private static final char[] ORIGIN_CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '_', '/', ';', '=', '?', '+', '\u00b5', '\u00a3', '*', '^', '\u00f9', '$', '!', '{', '}', '\'', '\"', '|', '&'};
    WurstplusSetting key = create("Key", "Key", "9", combobox("1", "4", "6", "9", "5"));
    WurstplusSetting mode = create("Mode", "Mode", 1, 1, 2);
    WurstplusSetting delim = create("Delimiter", "Delimiter", false);

    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> sendListener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (event.get_packet() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage)event.get_packet()).getMessage();
            if (this.delim.get_value(true)) {
                if (!s.startsWith("%")) {
                    return;
                }
                s = s.substring(1);
            }
            StringBuilder builder = new StringBuilder();
            switch (this.mode.get_value(1)) {
                case 1: {
                    builder.append(this.shuffle(this.key.get_value(1), s));
                    builder.append("\ud83d\ude4d");
                    break;
                }
                case 2: {
                    s.chars().forEachOrdered(value -> builder.append((char)(value + (ChatAllowedCharacters.isAllowedCharacter((char)((char)(value + this.key.get_value(1)))) ? this.key.get_value(1) : 0))));
                    builder.append("\ud83d\ude48");
                }
            }
            s = builder.toString();
            if (s.length() > 256) {
                WurstplusMessageUtil.send_client_message("Encrypted message length was too long, couldn't send!");
                event.cancel();
                return;
            }
            ((CPacketChatMessage)event.get_packet()).message = s;
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receiveListener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        if (event.get_packet() instanceof SPacketChat) {
            String s = ((SPacketChat)event.get_packet()).getChatComponent().getUnformattedText();
            Matcher matcher = this.CHAT_PATTERN.matcher(s);
            String username = "unnamed";
            if (matcher.find()) {
                username = matcher.group();
                username = username.substring(1, username.length() - 2);
                s = matcher.replaceFirst("");
            }
            StringBuilder builder = new StringBuilder();
            switch (this.mode.get_value(1)) {
                case 1: {
                    if (!s.endsWith("\ud83d\ude4d")) {
                        return;
                    }
                    s = s.substring(0, s.length() - 2);
                    builder.append(this.unshuffle(this.key.get_value(1), s));
                    break;
                }
                case 2: {
                    if (!s.endsWith("\ud83d\ude48")) {
                        return;
                    }
                    s = s.substring(0, s.length() - 2);
                    s.chars().forEachOrdered(value -> builder.append((char)(value + (ChatAllowedCharacters.isAllowedCharacter((char)((char)value)) ? -this.key.get_value(1) : 0))));
                }
            }
            ((SPacketChat)event.get_packet()).chatComponent = new TextComponentString("\u00a7b" + username + '\u00a7' + "r: " + builder.toString());
        }
    }, new Predicate[0]);

    private Map<Character, Character> generateShuffleMap(int seed) {
        Random r = new Random(seed);
        List characters = CharBuffer.wrap(ORIGIN_CHARS).chars().mapToObj(value -> Character.valueOf((char)value)).collect(Collectors.toList());
        ArrayList counter = new ArrayList(characters);
        Collections.shuffle(counter, r);
        LinkedHashMap<Character, Character> map = new LinkedHashMap<Character, Character>();
        for (int i = 0; i < characters.size(); ++i) {
            map.put((Character)characters.get(i), (Character)counter.get(i));
        }
        return map;
    }

    private String shuffle(int seed, String input) {
        Map<Character, Character> s = this.generateShuffleMap(seed);
        StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, s, builder);
        return builder.toString();
    }

    private String unshuffle(int seed, String input) {
        Map<Character, Character> s = this.generateShuffleMap(seed);
        StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, ChatEncryption.reverseMap(s), builder);
        return builder.toString();
    }

    private void swapCharacters(String input, Map<Character, Character> s, StringBuilder builder) {
        CharBuffer.wrap(input.toCharArray()).chars().forEachOrdered(value -> {
            char c = (char)value;
            if (s.containsKey(Character.valueOf(c))) {
                builder.append(s.get(Character.valueOf(c)));
            } else {
                builder.append(c);
            }
        });
    }

    private static <K, V> Map<V, K> reverseMap(Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    private static enum EncryptionMode {
        SHUFFLE,
        SHIFT;

    }

}

