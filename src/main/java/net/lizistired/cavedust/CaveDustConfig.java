package net.lizistired.cavedust;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.lizistired.cavedust.utils.MathHelper.generateRandomDouble;

@EventBusSubscriber(modid = CaveDust.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CaveDustConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    private static final ModConfigSpec.IntValue WIDTH = BUILDER
            .translation("menu.cavedust.width")
            .defineInRange("width", 10, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue VELOCITY_RANDOMNESS = BUILDER
            .translation("menu.cavedust.velocityrandomness")
            .defineInRange("velocityRandomness", 0, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue HEIGHT = BUILDER
            .translation("menu.cavedust.height")
            .defineInRange("height", 10, 0, Integer.MAX_VALUE);


    public static final ModConfigSpec.BooleanValue CAVE_DUST_ENABLED = BUILDER
            .translation("menu.cavedust.global") // may need to change as lang files change
            .define("caveDustEnabled", true);
    private static final ModConfigSpec.BooleanValue SEA_LEVEL_CHECK = BUILDER
            .define("seaLevelCheck", true);
    private static final ModConfigSpec.BooleanValue SUPER_FLAT_STATUS = BUILDER
            .translation("menu.cavedust.superflatstatus")
            .define("superFlatStatus", false);

    private static final ModConfigSpec.ConfigValue<Integer> UPPER_LIMIT = BUILDER
            .translation("menu.cavedust.upperlimit")
            .define("upperLimit", 64);
    private static final ModConfigSpec.ConfigValue<Integer> LOWER_LIMIT = BUILDER
            .translation("menu.cavedust.lowerlimit")
            .define("lowerLimit", -64);

    private static final ModConfigSpec.IntValue PARTICLE_MULTIPLIER = BUILDER.translation("")
            .translation("menu.cavedust.particlemultiplier")
            .defineInRange("particleMultiplier", 1, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue PARTICLE_MULTIPLIER_MULTIPLIER = BUILDER
            .translation("menu.cavedust.particlemultipliermultiplier")
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
