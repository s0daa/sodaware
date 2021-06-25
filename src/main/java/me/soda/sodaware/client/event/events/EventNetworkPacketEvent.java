package me.soda.sodaware.client.event.events;

import me.soda.sodaware.client.event.MinecraftEvent;
import net.minecraft.network.Packet;

public class EventNetworkPacketEvent extends MinecraftEvent
{
    public Packet m_Packet;

    public EventNetworkPacketEvent(Packet p_Packet)
    {
        super();
        m_Packet = p_Packet;
    }

    public Packet GetPacket()
    {
        return m_Packet;
    }

    public Packet getPacket()
    {
        return m_Packet;
    }
}