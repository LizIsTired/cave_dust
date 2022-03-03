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
		ClientTickEvents.START_CLIENT_TICK.register((client) -> {
			if(!client.isPaused()){
				if (client.world != null) {
					World world = client.world;
					if (!client.player.clientWorld.isSkyVisible(client.player.getBlockPos())) {
						double x;
						double y;
						double z;
						double probabilityNormalized = lerp(64, -64, client.player.getBlockY());
						for (int i = 0; i < probabilityNormalized; i++) {
							x = client.player.getPos().getX() + getRandomNumberUsingInts(-5, 5);
							y = client.player.getPos().getY() + getRandomNumberUsingInts(-20, 20);
							z = client.player.getPos().getZ() + getRandomNumberUsingInts(-5, 5);
							world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, getRandomNumberUsingInts(-5, 20), getRandomNumberUsingInts(-5, 20), getRandomNumberUsingInts(-5, 20));
						}
					}
				}
			}
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
