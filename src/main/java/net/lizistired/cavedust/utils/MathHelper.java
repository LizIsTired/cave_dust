package net.lizistired.cavedust.utils;

import net.lizistired.cavedust.CaveDustConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.OceanRuinFeature;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.world.biome.BiomeKeys.LUSH_CAVES;

public class MathHelper {

    public static double normalize(double min, double max, double val) {
        return 1 - ((val - min) / (max - min));
    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();

        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }


    public static double generateRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
