package net.fabricmc.example;

import com.minelittlepony.common.util.GamePaths;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

public class Dust implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("dust");

	private static Dust instance;

	public static Dust getInstance() {
		return instance;
	}

	public Dust() {
		instance = this;
	}

	private DustConfig config;

	public DustConfig getConfig() {
		return config;
	}

	@Override
	public void onInitializeClient() {
		Path dustFolder = GamePaths.getConfigDirectory().resolve("dust");
		config = new DustConfig(dustFolder.resolve("userconfig.json"), this);
		config.load();

		ClientTickEvents.END_CLIENT_TICK.register(this::createDust);
	}

	public int getRandomNumberUsingInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, max)
				.findFirst()
				.getAsInt();
	}

	public static double lerp(double min, double max, double val) {
		return 1 - ((val - min) / (max - min));
	}

	private void createDust(MinecraftClient client) {
		if (getConfig().getDustEnabled()) {
			if (!client.isPaused()) {
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
		}
	}
}
