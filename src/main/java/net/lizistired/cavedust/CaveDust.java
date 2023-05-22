package net.lizistired.cavedust;

//minecraft imports
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
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
	public static final Logger LOGGER = LoggerFactory.getLogger("cavedust");
	//make class static
	private static CaveDust instance;
	public static CaveDust getInstance() {
		return instance;
	}
	public CaveDust() {
		instance = this;
	}
	//config assignment
	private static net.lizistired.cavedust.CaveDustConfig config;
	public net.lizistired.cavedust.CaveDustConfig getConfig() {
		return config;
	}

	public static int WHITE_ASH_ID = Registries.PARTICLE_TYPE.getRawId(ParticleTypes.WHITE_ASH);
	public static int PARTICLE_AMOUNT = 0;


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
			client.player.sendMessage(Text.translatable("debug.cavedust.toggle." + config.getCaveDustEnabled()), false);
		}
		if (keyBinding2.wasPressed()){
			getConfig().load();
			LOGGER.info("Reloaded config");
			client.player.sendMessage(Text.translatable("debug.cavedust.reload"), false);
		}

		//ensure world is not null
		if (client.world == null) return;
		World world = client.world;
		//LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
		// )
		double probabilityNormalized = normalize(config.getLowerLimit(), config.getUpperLimit(), client.player.getBlockY());
		PARTICLE_AMOUNT = (int) (probabilityNormalized * config.getParticleMultiplier() * config.getParticleMultiplierMultiplier());

		for (int i = 0; i < PARTICLE_AMOUNT; i++) {
			try {
				int x = (int) (client.player.getPos().getX() + (int) generateRandomDouble(config.getDimensionsX() * -1, config.getDimensionsX()));
				int y = (int) (client.player.getPos().getY() + (int) generateRandomDouble(config.getDimensionsY() * -1, config.getDimensionsY()));
				int z = (int) (client.player.getPos().getZ() + (int) generateRandomDouble(config.getDimensionsZ() * -1, config.getDimensionsZ()));
				BlockPos particlePos = new BlockPos(x, y, z);

				if (shouldParticlesSpawn(client, config, particlePos)) {
					if (client.world.getBlockState(particlePos).isAir()) {
						world.addParticle(config.getParticle(), x, y, z, config.getVelocityRandomnessRandom(), config.getVelocityRandomnessRandom(), config.getVelocityRandomnessRandom());
					}
				}
			}
			catch (NullPointerException e) {
				LOGGER.error(String.valueOf(e));
				getConfig().setParticleID(WHITE_ASH_ID);
			}
		}
	}
}
