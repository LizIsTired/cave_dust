package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ExampleMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
				ClientTickEvents.START_CLIENT_TICK.register((client1) -> {

					World world = client1.world;
					if (!client1.player.clientWorld.isSkyVisible(client.player.getBlockPos())) {
						double d = 0;
						double e = 0;
						double f = 0;
						double probabilityClamped = lerp(64,-64,client.player.getBlockY());
						for (int i = 0; i < probabilityClamped; i++) {
							d = client.player.getPos().getX() + getRandomNumberUsingInts(-5, 5);
							e = client.player.getPos().getY() + getRandomNumberUsingInts(-20, 20);
							f = client.player.getPos().getZ() + getRandomNumberUsingInts(-5, 5);
							world.addParticle(ParticleTypes.WHITE_ASH, d, e, f, getRandomNumberUsingInts(-5, 20), getRandomNumberUsingInts(-5, 20), getRandomNumberUsingInts(-5, 20));
						}
					}
				});
		});
	}

	public int getRandomNumberUsingInts(int min, int max) {
		Random random = new Random();
		return random.ints(min,max)
				.findFirst()
				.getAsInt();
	}
	public static double lerp(double min, double max, double val) {
		return 1 - ((val - min) / (max - min));
	}
}
