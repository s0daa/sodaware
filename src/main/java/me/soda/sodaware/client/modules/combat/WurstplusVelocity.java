package me.soda.sodaware.client.modules.combat;


import me.soda.sodaware.client.event.WurstplusEventCancellable;
import me.soda.sodaware.client.event.events.WurstplusEventEntity;
import me.soda.sodaware.client.event.events.WurstplusEventPacket;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

//mhm take no knockback from getting a huge cock in your anus mhm

public class WurstplusVelocity extends WurstplusHack {
	public WurstplusVelocity() {
		super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "Velocity";
		this.tag         = "Velocity";
		this.description = "No kockback";
	}

	@EventHandler
	private Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener<>(event -> {
		if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
			if (event.get_packet() instanceof SPacketEntityVelocity) {
				SPacketEntityVelocity knockback = (SPacketEntityVelocity) event.get_packet();

				if (knockback.getEntityID() == mc.player.getEntityId()) {
					event.cancel();

					knockback.motionX *= 0.0f;
					knockback.motionY *= 0.0f;
					knockback.motionZ *= 0.0f;
				}
			} else if (event.get_packet() instanceof SPacketExplosion) {
				event.cancel();

				SPacketExplosion knockback = (SPacketExplosion) event.get_packet();

				knockback.motionX *= 0.0f;
				knockback.motionY *= 0.0f;
				knockback.motionZ *= 0.0f;
			}
		}
	});

	@EventHandler
	private Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener<>(event -> {
		if (event.get_entity() == mc.player) {
			event.cancel();

		}
	});
}
