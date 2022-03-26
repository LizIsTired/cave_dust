package net.lizistired.cavedust;

//minecraft imports
import net.lizistired.cavedust.mixin.ClientWorldAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
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
import static net.lizistired.cavedust.utils.MathHelper.generateRandomDouble;
import static net.lizistired.cavedust.utils.ParticleSpawnUtil.shouldParticlesSpawn;
import static net.lizistired.cavedust.utils.KeybindingHelper.*;


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
		registerKeyBindings();

		//register end client tick to create cave dust function, using end client tick for async
		ClientTickEvents.END_CLIENT_TICK.register(this::createCaveDust);
	}

	private void createCaveDust(MinecraftClient client) {
		if (keyBinding1.wasPressed()){
			getConfig().toggleCaveDust();
			LOGGER.info("Toggled dust");
			client.player.sendSystemMessage(new TranslatableText("debug.cavedust.toggle." + config.getCaveDustEnabled()), Util.NIL_UUID);
		}
		if (keyBinding2.wasPressed()){
			getConfig().load();
			LOGGER.info("Reloaded config");
			client.player.sendSystemMessage(new TranslatableText("debug.cavedust.reload"), Util.NIL_UUID);
		}

		//ensure world is not null
		if (client.world == null) return;
		World world = client.world;
		//LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
		// )
		double probabilityNormalized = normalize(config.getLowerLimit(), config.getUpperLimit(), client.player.getBlockY());

		for (int i = 0; i < probabilityNormalized * config.getParticleMultiplier() * 10; i++) {
			try {
				double x = client.player.getPos().getX() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxX());
				double y = client.player.getPos().getY() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxY());
				double z = client.player.getPos().getZ() + generateRandomDouble(config.getDimensionsMinX(), config.getDimensionsMaxZ());
				BlockPos particlePos = new BlockPos(x, y, z);

				if (!shouldParticlesSpawn(client, config, particlePos)){return;}


				world.addParticle(config.getParticle(), x, y, z, config.getVelocityRandomnessRandom(), config.getVelocityRandomnessRandom(), config.getVelocityRandomnessRandom());
			}
			catch (NullPointerException e) {
				LOGGER.error(String.valueOf(e));
				getConfig().setParticle("minecraft:white_ash");
			}
		}
	}
}
