package me.soda.sodaware.client.event.events;

import me.soda.sodaware.client.event.MinecraftEvent;

public class EventPlayerPush extends MinecraftEvent
{
    public double X, Y, Z;

    public EventPlayerPush(double p_X, double p_Y, double p_Z)
    {
        super();

        X = p_X;
        Y = p_Y;
        Z = p_Z;
    }
}
