package net.lizistired.cavedust;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.lizistired.cavedust.utils.MathHelper.generateRandomDouble;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = CaveDustMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    private static final ModConfigSpec.IntValue WIDTH = BUILDER
            .comment("Width of particle")
            .defineInRange("width", 10, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue VELOCITY_RANDOMNESS = BUILDER
            .defineInRange("velocityRandomness", 0, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue HEIGHT = BUILDER
            .comment("Height of particle")
            .defineInRange("height", 10, 0, Integer.MAX_VALUE);


    private static final ModConfigSpec.BooleanValue CAVE_DUST_ENABLED = BUILDER
            .define("caveDustEnabled", true);
    private static final ModConfigSpec.BooleanValue SEA_LEVEL_CHECK = BUILDER
            .define("seaLevelCheck", true);
    private static final ModConfigSpec.BooleanValue SUPER_FLAT_STATUS = BUILDER
            .define("superFlatStatus", true);

    private static final ModConfigSpec.ConfigValue<Integer> UPPER_LIMIT = BUILDER
            .define("upperLimit", 64);
    private static final ModConfigSpec.ConfigValue<Integer> LOWER_LIMIT = BUILDER
            .define("lowerLimit", -64);

    private static final ModConfigSpec.IntValue PARTICLE_MULTIPLIER = BUILDER
            .defineInRange("particleMultiplier", 1, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue PARTICLE_MULTIPLIER_MULTIPLIER = BUILDER
            .defineInRange("particleMultiplierMultiplier", 10, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();


    public static int width = 10;
    public static int height = 10;
    public static int velocityRandomness = 0;

    public static boolean caveDustEnabled = true;
    public static boolean seaLevelCheck = true;
    public static boolean superFlatStatus = false;
    public static float upperLimit = 64;
    public static float lowerLimit = -64;
    public static int particleMultiplier = 1;
    public static int particleMultiplierMultiplier = 10;

    public static float getVelocityRandomnessRandom(){
        if (velocityRandomness == 0) {return 0;}
        return (float) generateRandomDouble(-velocityRandomness, velocityRandomness);
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

        width = WIDTH.get();
        height = HEIGHT.get();

        velocityRandomness = VELOCITY_RANDOMNESS.get();

        caveDustEnabled = CAVE_DUST_ENABLED.get();
        seaLevelCheck = SEA_LEVEL_CHECK.get();
        superFlatStatus = SUPER_FLAT_STATUS.get();

        upperLimit = UPPER_LIMIT.get();
        lowerLimit = LOWER_LIMIT.get();

        particleMultiplier = PARTICLE_MULTIPLIER.get();
        particleMultiplierMultiplier = PARTICLE_MULTIPLIER_MULTIPLIER.get();
    }
}
