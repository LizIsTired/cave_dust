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
    private static float timer;
    public static boolean shouldParticlesSpawn;

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

    public static boolean shouldParticlesSpawn(MinecraftClient client, CaveDustConfig config) {

        //checks if the config is enabled, if the game isn't paused, if the world is valid, if the particle is valid and if the player isn't in a lush caves biome
        if (!config.getCaveDustEnabled()
                || client.isPaused()
                || client.world == null
                || !client.world.getDimension().isBedWorking()
                || client.world.getBiome(Objects.requireNonNull(client.player).getBlockPos()).matchesKey(LUSH_CAVES))
        {
            timer = 0;
            shouldParticlesSpawn = false;
            return false;
        }

        World world = client.world;
        int seaLevel = world.getSeaLevel();

        if (!client.player.clientWorld.isSkyVisible(client.player.getBlockPos())) {
            if (client.player.getBlockPos().getY() < seaLevel){
                timer = timer + 1;
                if (timer > 10){
                    timer = 10;
                    shouldParticlesSpawn = true;
                    return true;
                }
            }
        }
        shouldParticlesSpawn = false;
        return false;
    }
}
