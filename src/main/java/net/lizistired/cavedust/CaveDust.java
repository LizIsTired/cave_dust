package net.lizistired.cavedust;

//minecraft imports
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.lizistired.cavedust.utils.CubeCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
//other imports
import com.minelittlepony.common.util.GamePaths;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//java imports
import java.nio.file.Path;
//static imports
import static net.lizistired.cavedust.utils.MathHelper.*;
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

	public static ParticleOptions WHITE_ASH_ID = (ParticleOptions) BuiltInRegistries.PARTICLE_TYPE.getValue(Identifier.fromNamespaceAndPath("cavedust", "cave_dust"));
	public static int PARTICLE_AMOUNT = 0;
	public static int PARTICLE_RADIUS_PLUME = 50;
	CubeCreator cubeCreator = new CubeCreator();
	Minecraft client;




	@Override
	public void onInitializeClient() {
		//config path and loading
		Path CaveDustFolder = GamePaths.getConfigDirectory().resolve("cavedust");
		config = new CaveDustConfig(CaveDustFolder.getParent().resolve("cavedust.json"), this);
		config.load();
		registerKeyBindings();
		ParticleProviderRegistry.getInstance().register(CaveDustServer.CAVE_DUST_MOTE, CaveDustMoteParticleFactory.Factory::new);
		ParticleProviderRegistry.getInstance().register(CaveDustServer.CAVE_DUST_PLUME, CaveDustPlumeParticleFactory.Factory::new);

		//register end client tick to create cave dust function, using end client tick for async
		LevelRenderEvents.END_MAIN.register(this::createCaveDust);
		ServerLifecycleEvents.SERVER_STOPPING.register(this::nullClient);
	}

	private void nullClient(MinecraftServer minecraftServer) {
		client = null;
	}

	private void createCaveDust(LevelRenderContext context) {
		if(client == null) {
			try {
				client = context.gameRenderer().getMinecraft();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (keyBinding1.consumeClick()){
			getConfig().toggleCaveDust();
			LOGGER.info("Toggled dust");
			client.player.sendOverlayMessage(Component.translatable("debug.cavedust.toggle." + config.getCaveDustEnabled()));
		}
		if (keyBinding2.consumeClick()){
			getConfig().load();
			LOGGER.info("Reloaded config");
			client.player.sendOverlayMessage(Component.translatable("debug.cavedust.reload"));
		}

		//ensure world is not null
		if (client.level == null) return;
		Level world = client.level;

		double probabilityNormalized = normalize(config.getLowerLimit(), config.getUpperLimit(), client.player.getBlockY());
		PARTICLE_AMOUNT = (int) (probabilityNormalized * config.getParticleMultiplier());

		for (int i = 0; i < PARTICLE_AMOUNT; i++) {
			cubeCreator.randomSphereCreator((int) config.getDimensionWidth(), 0, 0, 0);
		}

    }

	private void createCaveDust1(Minecraft client) {

		//LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
		// )


		//for (int i = 0; i < PARTICLE_AMOUNT; i++) {
		//		int x = (int) (client.player.getPos().getX() + (int) generateRandomDouble(config.getDimensionWidth() *-1, config.getDimensionWidth()));
		//		int y = (int) (client.player.getEyePos().getY() + (int) generateRandomDouble(config.getDimensionHeight() *-1, config.getDimensionHeight()));
		//		int z = (int) (client.player.getPos().getZ() + (int) generateRandomDouble(config.getDimensionWidth() *-1, config.getDimensionWidth()));
		//		double miniX = (x + Math.random());
		//		double miniY = (y + Math.random());
		//		double miniZ = (z + Math.random());
		//		BlockPos particlePos = new BlockPos(x, y, z);
//
		//		if (shouldParticlesSpawn(client, config, particlePos)) {
		//			if (client.world.getBlockState(particlePos).isAir()) {
		//				world.addParticleClient((ParticleEffect) Registries.PARTICLE_TYPE.get(Identifier.of("cavedust", "cave_dust_mote")), miniX, miniY, miniZ, config.getVelocityRandomnessRandom() * 0.01, config.getVelocityRandomnessRandom() * 0.01, config.getVelocityRandomnessRandom() * 0.01);
		//			}
		//		}
		//	}

		for (int i = 0; i < 5000; i++) {
			cubeCreator.randomSphereCreator(5, 0, 0, 0);
		}


	//Vec3d playerPos = client.player.getEyePos();
	//int x = (int) (playerPos.getX() + (int) generateRandomDouble(generateRandomDouble(-50, -25), (generateRandomDouble(25, 50))));
	////int y = (int) (playerPos.getY() + (int) generateRandomDouble(PARTICLE_RADIUS_PLUME *-1, PARTICLE_RADIUS_PLUME));
	//int z = (int) (playerPos.getZ() + (int) generateRandomDouble(PARTICLE_RADIUS_PLUME *-1, PARTICLE_RADIUS_PLUME));
	//BlockPos particlePos = new BlockPos(x, (int) playerPos.y, z);
//
//
	//if (shouldParticlesSpawn(client, config, particlePos)) {
//
	//	if (playerPos.distanceTo(particlePos.toCenterPos()) >= 45 && playerPos.distanceTo(particlePos.toCenterPos()) <= 55) {
	//		if (client.world.getBlockState(particlePos).isAir()) {
	//			world.addParticleClient((ParticleEffect) Registries.PARTICLE_TYPE.get(Identifier.of("cavedust", "cave_dust_mote")), x, playerPos.y, z, 0, 0, 0);
	//		}
	//	}
	//}
//}
		}
	}
