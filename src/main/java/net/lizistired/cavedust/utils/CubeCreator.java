package net.lizistired.cavedust.utils;

import com.jcraft.jorbis.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CubeCreator {


    // 3 nesting for loops to create a hollow border cube around the player
    public void cubeCreator(int steps, int offsetXInitial, int offsetYInitial, int offsetZInitial) {
        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();

        // Loop over the range of steps around the player
        for (int x = -steps / 2 + offsetXInitial; x < steps / 2 + offsetXInitial; x++) {
            for (int y = -steps / 2 + offsetYInitial; y < steps / 2 + offsetYInitial; y++) {
                for (int z = -steps / 2 + offsetZInitial; z < steps / 2 + offsetZInitial; z++) {

                    // Check if the particle is on the border of the cube
                    boolean onBorder = x == -steps / 2 + offsetXInitial || x == steps / 2 + offsetXInitial - 1
                            || y == -steps / 2 + offsetYInitial || y == steps / 2 + offsetYInitial - 1
                            || z == -steps / 2 + offsetZInitial || z == steps / 2 + offsetZInitial - 1;

                    // If it's on the border, spawn the particle
                    if (onBorder) {
                        spawnParticleClient(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                    }
                }
            }
        }
    }

    // Create a hollow sphere around the player
    public void sphereCreator(int radius, int offsetXInitial, int offsetYInitial, int offsetZInitial) {
        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();

        // Loop over the range of steps around the player (we are using a cube bounding box to check)
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    // Calculate the distance from the center (player position)
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

                    // If the distance is close to the radius, spawn the particle (create the border)
                    if (Math.abs(distance - radius) < 1.5) { // Threshold for thickness of the border
                        spawnParticleClient(playerPos.getX() + x + offsetXInitial,
                                playerPos.getY() + y + offsetYInitial,
                                playerPos.getZ() + z + offsetZInitial);
                    }
                }
            }
        }
    }

    public void randomSphereCreator(int radius, int offsetXInitial, int offsetYInitial, int offsetZInitial) {
        Random random = new Random();  // Random number generator

        // Generate random spherical angles
        double theta = Math.acos(2 * random.nextDouble() - 1);  // Random polar angle (0 to pi)
        double phi = 2 * Math.PI * random.nextDouble();  // Random azimuthal angle (0 to 2*pi)

        // Convert spherical coordinates to Cartesian coordinates
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        // Offset the generated point to be around the player
        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();
        int spawnX = playerPos.getX() + (int) (x + offsetXInitial);
        int spawnY = playerPos.getY() + (int) (y + offsetYInitial);
        int spawnZ = playerPos.getZ() + (int) (z + offsetZInitial);

        // Spawn a particle at the random position
        float randomX = random.nextFloat() + spawnX;
        float randomY = random.nextFloat() + spawnY;
        float randomZ = random.nextFloat() + spawnZ;
        spawnParticleClient(randomX, randomY, randomZ);
    }

    private void spawnParticleClient(float x, float y, float z) {
        MinecraftClient.getInstance().world.addParticleClient((ParticleEffect) Registries.PARTICLE_TYPE.get(Identifier.of("cavedust", "cave_dust_mote")), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
