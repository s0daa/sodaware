package me.soda.sodaware.client.modules.chat;

import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;

import java.util.Random;

public
class AutoExcuse
        extends WurstplusHack {
    int diedTime = 0;

    public
    AutoExcuse ( ) {
        super ( WurstplusCategory.WURSTPLUS_CHAT );
        this.name = "Auto Excuse";
        this.tag = "AutoExcuse";
        this.description = "tell people why you died";
    }

    @Override
    public
    void update ( ) {
        if ( this.diedTime > 0 ) {
            -- this.diedTime;
        }
        if ( AutoExcuse.mc.player.isDead ) {
            this.diedTime = 500;
        }
        if ( ! AutoExcuse.mc.player.isDead && this.diedTime > 0 ) {
            Random random = new Random ( );
            int n = random.nextInt ( 11 ) + 1;
            if ( n == 1 ) {
                AutoExcuse.mc.player.sendChatMessage ( "you win because you are a pingplayer :((" );
            }
            if ( n == 2 ) {
                AutoExcuse.mc.player.sendChatMessage ( "i was in my hacker console :(" );
            }
            if ( n == 3 ) {
                AutoExcuse.mc.player.sendChatMessage ( "bro im good i was testing settings :((" );
            }
            if ( n == 4 ) {
                AutoExcuse.mc.player.sendChatMessage ( "im desync :(" );
            }
            if ( n == 5 ) {
                AutoExcuse.mc.player.sendChatMessage ( "you're a cheater :(" );
            }
            if ( n == 6 ) {
                AutoExcuse.mc.player.sendChatMessage ( "my cat walked on my keyboard" );
            }
            if ( n == 7 ) {
                AutoExcuse.mc.player.sendChatMessage ( "i wasn't configed" );
            }
            if ( n == 8 ) {
                AutoExcuse.mc.player.sendChatMessage ( "my offhand failed" );
            }
            if ( n == 9 ) {
                AutoExcuse.mc.player.sendChatMessage ( "my armor broke" );
            }
            if ( n == 10 ) {
                AutoExcuse.mc.player.sendChatMessage ( "my monitor was off" );
            }
            if ( n == 11 ) {
                AutoExcuse.mc.player.sendChatMessage ( "i was tabbed out :c" );
            }
            this.diedTime = 0;
        }
    }
}