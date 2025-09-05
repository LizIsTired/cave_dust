package net.lizistired.cavedust;

//minecraft imports
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.lizistired.cavedust.utils.CubeCreator;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
//other imports
import com.minelittlepony.common.util.GamePaths;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.world.event.GameEvent;
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

	public static ParticleEffect WHITE_ASH_ID = (ParticleEffect) Registries.PARTICLE_TYPE.get(Identifier.of("cavedust", "cave_dust"));
	public static int PARTICLE_AMOUNT = 0;
	public static int PARTICLE_RADIUS_PLUME = 50;
	CubeCreator cubeCreator = new CubeCreator();
	MinecraftClient client;




	@Override
	public void onInitializeClient() {
		//config path and loading
		Path CaveDustFolder = GamePaths.getConfigDirectory().resolve("cavedust");
		config = new CaveDustConfig(CaveDustFolder.getParent().resolve("cavedust.json"), this);
		config.load();
		registerKeyBindings();
		ParticleFactoryRegistry.getInstance().register(CaveDustServer.CAVE_DUST_MOTE, CaveDustMoteParticleFactory.Factory::new);
		ParticleFactoryRegistry.getInstance().register(CaveDustServer.CAVE_DUST_PLUME, CaveDustPlumeParticleFactory.Factory::new);

		//register end client tick to create cave dust function, using end client tick for async
		WorldRenderEvents.LAST.register(this::createCaveDust);
		ServerLifecycleEvents.SERVER_STOPPING.register(this::nullClient);
	}

	private void nullClient(MinecraftServer minecraftServer) {
		client = null;
	}

	private void createCaveDust(WorldRenderContext worldRenderContext) {
		if(client == null) {
			try {
				client = worldRenderContext.gameRenderer().getClient();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (keyBinding1.wasPressed()){
			getConfig().toggleCaveDust();
			LOGGER.info("Toggled dust");
			client.player.sendMessage(Text.translatable("debug.cavedust.toggle." + config.getCaveDustEnabled()), true);
		}
		if (keyBinding2.wasPressed()){
			getConfig().load();
			LOGGER.info("Reloaded config");
			client.player.sendMessage(Text.translatable("debug.cavedust.reload"), true);
		}

		//ensure world is not null
		if (client.world == null) return;
		World world = client.world;

		for (int i = 0; i < 5000; i++) {
			cubeCreator.randomSphereCreator(5, 0, 0, 0);
		}

    }

	private void createCaveDust(MinecraftClient client) {

		//LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
		// )
		double probabilityNormalized = normalize(config.getLowerLimit(), config.getUpperLimit(), client.player.getBlockY());
		PARTICLE_AMOUNT = (int) (probabilityNormalized * config.getParticleMultiplier() * config.getParticleMultiplierMultiplier());

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

	//cubeCreator.cubeCreator(50, 0, 0, 0);
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
