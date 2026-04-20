package net.lizistired.cavedust.utils;

import net.lizistired.cavedust.CaveDustConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Objects;

import static net.minecraft.world.level.biome.Biomes.LUSH_CAVES;

public class ParticleSpawnUtil {
    private static float timer;
    public static boolean shouldParticlesSpawn;


    /**
     * Returns true if particles should spawn.
     * @param client MinecraftClient
     * @param config CaveDustConfig
     * @return boolean
     */
    public static boolean shouldParticlesSpawn(Minecraft client, CaveDustConfig config) {

        //checks if the config is enabled, if the game isn't paused, if the world is valid, if the particle is valid and if the player isn't in a lush caves biome
        if (!config.getCaveDustEnabled()
                || client.isPaused()
                || client.level == null
                || !client.level.dimensionType().hasFixedTime()
                || Objects.requireNonNull(client.player).isUnderWater()
                || client.level.getBiome(Objects.requireNonNull(client.player.blockPosition())).is(LUSH_CAVES))
        {
            timer = 0;
            shouldParticlesSpawn = false;
            return false;
        }

        Level world = client.level;
        int seaLevel = world.getSeaLevel();

        if (!client.player.level().canSeeSky(client.player.blockPosition())) {
            if (client.player.blockPosition().getY() + 2 < seaLevel){
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

    /**
     * Returns true if particles should spawn (uses particle position instead of player).
     * @param client MinecraftClient
     * @param config CaveDustConfig
     * @param pos BlockPos
     * @return boolean
     */
    public static boolean shouldParticlesSpawn(Minecraft client, CaveDustConfig config, BlockPos pos) {

        //checks if the config is enabled, if the game isn't paused, if the world is valid, if the particle is valid and if the player isn't in a lush caves biome
        if (!config.getCaveDustEnabled()
                || client.isPaused()
                || client.level == null
                || !client.level.dimensionType().hasFixedTime()
                || (client.level.getMinY() > pos.getY())
                //|| client.world.getBiome(Objects.requireNonNull(pos)).matchesKey(LUSH_CAVES))
                || client.level.getBiome(Objects.requireNonNull(pos)).is(LUSH_CAVES))

        {
            timer = 0;
            shouldParticlesSpawn = false;
            return false;
        }
        if(!config.getSuperFlatStatus()) {
            if (client.level.getSeaLevel() > 52) {
                return false;
            }
        }

        Level world = client.level;
        int seaLevel = world.getSeaLevel();

        if (!client.player.level().canSeeSky(pos)) {
            if (pos.getY() + 2 < seaLevel){
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
