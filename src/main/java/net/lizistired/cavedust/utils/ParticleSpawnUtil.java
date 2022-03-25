package net.lizistired.cavedust.utils;

import net.lizistired.cavedust.CaveDustConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;

import static net.minecraft.world.biome.BiomeKeys.LUSH_CAVES;

public class ParticleSpawnUtil {
    private static float timer;
    public static boolean shouldParticlesSpawn;
    public static boolean shouldParticlesSpawn(MinecraftClient client, CaveDustConfig config) {

        //checks if the config is enabled, if the game isn't paused, if the world is valid, if the particle is valid and if the player isn't in a lush caves biome
        if (!config.getCaveDustEnabled()
                || client.isPaused()
                || client.world == null
                || !client.world.getDimension().isBedWorking()
                || Objects.requireNonNull(client.player).isSubmergedInWater())
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
