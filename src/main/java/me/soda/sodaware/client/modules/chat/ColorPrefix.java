package me.soda.sodaware.client.modules.chat;

import me.soda.sodaware.client.event.events.WurstplusEventPacket;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.manager.WurstplusCommandManager;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

public
class ColorPrefix
        extends WurstplusHack {
    WurstplusSetting mode = this.create ( "Prefix" , "CPrefix" , ">" , this.combobox ( ">" , "`" , "#" , "&" , "%" , "$" , "*" , "," ) );
    WurstplusSetting space = this.create ( "Space" , "CPrefixSpace" , true );
    @EventHandler
    private final Listener < WurstplusEventPacket.SendPacket > listener = new Listener < WurstplusEventPacket.SendPacket > ( sendPacket -> {
        if ( sendPacket.get_packet ( ) instanceof CPacketChatMessage ) {
            if ( ( (CPacketChatMessage) sendPacket.get_packet ( ) ).getMessage ( ).startsWith ( "/" ) || ( (CPacketChatMessage) sendPacket.get_packet ( ) ).getMessage ( ).startsWith ( WurstplusCommandManager.get_prefix ( ) ) ) {
                return;
            }
            String string = ( (CPacketChatMessage) sendPacket.get_packet ( ) ).getMessage ( );
            String string2 = "";
            String string3 = "";
            if ( this.mode.in ( ">" ) ) {
                string2 = ">";
            }
            if ( this.mode.in ( "`" ) ) {
                string2 = "`";
            }
            if ( this.mode.in ( "#" ) ) {
                string2 = "#";
            }
            if ( this.mode.in ( "&" ) ) {
                string2 = "&";
            }
            if ( this.mode.in ( "%" ) ) {
                string2 = "%";
            }
            if ( this.mode.in ( "$" ) ) {
                string2 = "$";
            }
            if ( this.mode.in ( "*" ) ) {
                string2 = "*";
            }
            if ( this.mode.in ( "," ) ) {
                string2 = ",";
            }
            string3 = this.space.get_value ( true ) ? " " : "";
            String string4 = string2 + string3 + string;
            if ( string4.length ( ) > 255 ) {
                return;
            }
            ( (CPacketChatMessage) sendPacket.get_packet ( ) ).message = string4;
        }
    } );

    public
    ColorPrefix ( ) {
        super ( WurstplusCategory.WURSTPLUS_CHAT );
        this.name = "Color Prefix";
        this.tag = "ColorPrefix";
        this.description = "it does exactly what you think";
    }
}