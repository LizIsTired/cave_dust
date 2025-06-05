package net.lizistired.cavedust;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.lizistired.cavedust.utils.MathHelper.generateRandomDouble;
import static net.lizistired.cavedust.utils.MathHelper.normalize;
import static net.lizistired.cavedust.utils.ParticleSpawnUtil.shouldParticlesSpawn;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CaveDustMod.MODID)
public class CaveDustMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cavedust";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    // The easiest way to add new particle types is reusing vanilla's SimpleParticleType.
    // Implementing a custom ParticleType is also possible, see below.
    public static final Supplier<SimpleParticleType> CAVE_DUST_PARTICLE = PARTICLE_TYPES.register(
            // The name of the particle type.
            "cave_dust",
            // The supplier. The boolean parameter denotes whether setting the Particles option in the
            // video settings to Minimal will affect this particle type or not; this is false for
            // most vanilla particles, but true for e.g. explosions, campfire smoke, or squid ink.
            () -> new SimpleParticleType(false)
    );

    public static int PARTICLE_AMOUNT = 0;

//    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
//    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
//    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
//    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
//    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
//    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
//
//    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
//    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
//    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
//    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
//
//    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
//    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
//
//    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
//            .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CaveDustMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
//        modEventBus.addListener(this::commonSetup);
//
//        // Register the Deferred Register to the mod event bus so blocks get registered
//        BLOCKS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so items get registered
//        ITEMS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so tabs get registered
//        CREATIVE_MODE_TABS.register(modEventBus);
//
//        // Register ourselves for server and other game events we are interested in.
//        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
//        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//        NeoForge.EVENT_BUS.register(this);
//
//        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);
        PARTICLE_TYPES.register(modEventBus);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

//    private void commonSetup(final FMLCommonSetupEvent event)
//    {
//        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
//    }
//
//    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event)
//    {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
//    }
//
//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            // There are multiple ways to register providers, all differing in the functional type they provide in the
            // second parameter. For example, #registerSpriteSet represents a Function<SpriteSet, ParticleProvider<?>>:
            event.registerSpriteSet(CAVE_DUST_PARTICLE.get(), CaveDustProvider::new);
            // Other methods include #registerSprite, which is essentially a Supplier<TextureSheetParticle>,
            // and #registerSpecial, which maps to a Supplier<Particle>. See the source code of the event for further info.
        }
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents
    {
        @SubscribeEvent
        public static void createCaveDust(ClientTickEvent.Post event)
        {
            Minecraft client = Minecraft.getInstance();
            if (client.level == null) return;
            Level world = client.level;

            //LOGGER.info(String.valueOf(((ClientWorldAccessor) client.world.getLevelProperties()).getFlatWorld()));
            // )
            double probabilityNormalized = normalize(Config.lowerLimit, Config.upperLimit, client.player.getBlockY());
            PARTICLE_AMOUNT = (int) (probabilityNormalized * Config.particleMultiplier * Config.particleMultiplierMultiplier);

            for (int i = 0; i < PARTICLE_AMOUNT; i++) {
                int x = (int) (client.player.position().x() + (int) generateRandomDouble(Config.width *-1, Config.width ));
                int y = (int) (client.player.getEyePosition().y() + (int) generateRandomDouble(Config.height *-1, Config.height));
                int z = (int) (client.player.position().z() + (int) generateRandomDouble(Config.width  *-1, Config.width));
                double miniX = (x + Math.random());
                double miniY = (y + Math.random());
                double miniZ = (z + Math.random());
                BlockPos particlePos = new BlockPos(x, y, z);

                if (shouldParticlesSpawn(client, particlePos)) {
                    if (client.level.getBlockState(particlePos).isAir()) {
                        world.addParticle(CAVE_DUST_PARTICLE.get(), miniX, miniY, miniZ, Config.getVelocityRandomnessRandom() * 0.01, Config.getVelocityRandomnessRandom() * 0.01, Config.getVelocityRandomnessRandom() * 0.01);
                    }
                }
            }
        }
    }
}
