package me.soda.sodaware.client.event.events;

import me.soda.sodaware.client.event.WurstplusEventCancellable;

public class WurstplusEventPlayerJump extends WurstplusEventCancellable {
    
    public double motion_x;
    public double motion_y;

    public WurstplusEventPlayerJump(double motion_x, double motion_y) {
        super();

        this.motion_x = motion_x;
        this.motion_y = motion_y;
    }

}