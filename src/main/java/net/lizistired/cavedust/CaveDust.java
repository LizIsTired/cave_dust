package net.lizistired.cavedust;

import com.mojang.logging.LogUtils;
import net.lizistired.cavedust.utils.KeybindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static net.lizistired.cavedust.utils.MathHelper.generateRandomDouble;
import static net.lizistired.cavedust.utils.MathHelper.normalize;
import static net.lizistired.cavedust.utils.ParticleSpawnUtil.shouldParticlesSpawn;

@Mod(CaveDust.MODID)
public class CaveDust
{
    public static final String MODID = "cavedust";
    private static final Logger LOGGER = LogUtils.getLogger();


    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final Supplier<SimpleParticleType> CAVE_DUST_PARTICLE = PARTICLE_TYPES.register(
            "cave_dust",
            () -> new SimpleParticleType(false)
    );

    public static int PARTICLE_AMOUNT = 0;

    public CaveDust(IEventBus modEventBus, ModContainer modContainer)
    {
        PARTICLE_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.CLIENT, CaveDustConfig.SPEC);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(CAVE_DUST_PARTICLE.get(), CaveDustParticle.CaveDustProvider::new);
        }
        @SubscribeEvent // on the mod event bus only on the physical client
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            KeybindingHelper.registerKeyBindings(event);
        }
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents
    {
        @SubscribeEvent
        public static void createCaveDust(ClientTickEvent.Post event)
        {
            Minecraft client = Minecraft.getInstance();
            while (KeybindingHelper.keyBinding1.get().consumeClick()) {
                CaveDustConfig.CAVE_DUST_ENABLED.set(!CaveDustConfig.caveDustEnabled);
                CaveDustConfig.CAVE_DUST_ENABLED.save();
                LOGGER.info("Toggled dust");
                client.player.displayClientMessage(Component.translatable("debug.cavedust.toggle." + CaveDustConfig.caveDustEnabled), false);
            }
            while (KeybindingHelper.keyBinding2.get().consumeClick()) {
                CaveDustConfig.onLoad(null);
                LOGGER.info("Reloaded config");
                client.player.displayClientMessage(Component.translatable("debug.cavedust.reload"), false);
            }

            //ensure world is not null
            if (client.level == null) return;
            Level world = client.level;

            //LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
            // )
            double probabilityNormalized = normalize(CaveDustConfig.lowerLimit, CaveDustConfig.upperLimit, client.player.getBlockY());
            PARTICLE_AMOUNT = (int) (probabilityNormalized * CaveDustConfig.particleMultiplier * CaveDustConfig.particleMultiplierMultiplier);

            for (int i = 0; i < PARTICLE_AMOUNT; i++) {
                int x = (int) (client.player.position().x() + (int) generateRandomDouble(CaveDustConfig.width *-1, CaveDustConfig.width ));
                int y = (int) (client.player.getEyePosition().y() + (int) generateRandomDouble(CaveDustConfig.height *-1, CaveDustConfig.height));
                int z = (int) (client.player.position().z() + (int) generateRandomDouble(CaveDustConfig.width  *-1, CaveDustConfig.width));
                double miniX = (x + Math.random());
                double miniY = (y + Math.random());
                double miniZ = (z + Math.random());
                BlockPos particlePos = new BlockPos(x, y, z);

                if (shouldParticlesSpawn(client, particlePos)) {
                    if (client.level.getBlockState(particlePos).isAir()) {
                        world.addParticle(CAVE_DUST_PARTICLE.get(), miniX, miniY, miniZ, CaveDustConfig.getVelocityRandomnessRandom() * 0.01, CaveDustConfig.getVelocityRandomnessRandom() * 0.01, CaveDustConfig.getVelocityRandomnessRandom() * 0.01);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void appendDebugText(CustomizeGuiOverlayEvent.DebugText event)
        {
            event.getRight().add("Particle amount evaluated: " + PARTICLE_AMOUNT);
        }
    }
}
