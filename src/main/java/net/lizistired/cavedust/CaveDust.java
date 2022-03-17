package net.lizistired.cavedust;

//minecraft imports
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
//other imports
import com.minelittlepony.common.util.GamePaths;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//java imports
import java.nio.file.Path;
//static imports
import static net.lizistired.cavedust.utils.MathHelper.*;


public class CaveDust implements ClientModInitializer {
	//logger
	public static final Logger LOGGER = LoggerFactory.getLogger("caveDust");
	//make class static
	private static CaveDust instance;
	public static CaveDust getInstance() {
		return instance;
	}
	public CaveDust() {
		instance = this;
	}
	//config assignment
	private static CaveDustConfig config;
	public CaveDustConfig getConfig() {
		return config;
	}

	@Override
	public void onInitializeClient() {
		//config path and loading
		Path CaveDustFolder = GamePaths.getConfigDirectory().resolve("cavedust");
		config = new CaveDustConfig(CaveDustFolder.getParent().resolve("cavedust.json"), this);
		config.load();
		//register end client tick to create cave dust function, using end client tick for async
		ClientTickEvents.END_CLIENT_TICK.register(this::createCaveDust);
	}

	private void createCaveDust(MinecraftClient client) {

		if (client.world == null) return;
		World world = client.world;

		if (shouldParticlesSpawn(client, config)) {
			double probabilityNormalized = normalize(config.getLowerLimit(), config.getUpperLimit(), client.player.getBlockY());

			for (int i = 0; i < probabilityNormalized * config.getParticleMultiplier(); i++) {
				try {
					double x = client.player.getPos().getX() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxX());
					double y = client.player.getPos().getY() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxY());
					double z = client.player.getPos().getZ() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxZ());

					world.addParticle(config.getParticle(), x, y, z, 0, 0, 0);}

				catch (NullPointerException e) {
					LOGGER.error(String.valueOf(e));
					getConfig().setParticle("minecraft:white_ash");
				}
			}
		}
	}
}
