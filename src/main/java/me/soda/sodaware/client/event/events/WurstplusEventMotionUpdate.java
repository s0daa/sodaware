package me.soda.sodaware.client.event.events;

import me.soda.sodaware.client.event.WurstplusEventCancellable;

public class WurstplusEventMotionUpdate extends WurstplusEventCancellable {

    public int stage;

    public WurstplusEventMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }
    
}