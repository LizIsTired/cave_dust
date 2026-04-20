package net.lizistired.cavedust.utils;

import com.jcraft.jorbis.Block;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lizistired.cavedust.CaveDust;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class CubeCreator {

    private static final Random random = new Random();

    // 3 nesting for loops to create a hollow border cube around the player
    public void cubeCreator(int steps, int offsetXInitial, int offsetYInitial, int offsetZInitial) {
        BlockPos playerPos = Minecraft.getInstance().player.blockPosition();

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
        BlockPos playerPos = Minecraft.getInstance().player.blockPosition();

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

        // Random radius (correct distribution)
        double r = radius * Math.pow(random.nextDouble(), 0.2);

        // Random spherical angles
        double theta = Math.acos(2 * random.nextDouble() - 1);
        double phi = 2 * Math.PI * random.nextDouble();

        // Convert to Cartesian coordinates
        double x = r * Math.sin(theta) * Math.cos(phi);
        double y = r * Math.sin(theta) * Math.sin(phi);
        double z = r * Math.cos(theta);

        // Player position
        BlockPos playerPos = Minecraft.getInstance().player.blockPosition();
        int spawnX = playerPos.getX() + (int) (x + offsetXInitial);
        int spawnY = playerPos.getY() + (int) (y + offsetYInitial);
        int spawnZ = playerPos.getZ() + (int) (z + offsetZInitial);

        // Slight randomness within the block
        float randomX = random.nextFloat() + spawnX;
        float randomY = random.nextFloat() + spawnY;
        float randomZ = random.nextFloat() + spawnZ;

        spawnParticleClient(randomX, randomY, randomZ);
    }

    private void spawnParticleClient(float x, float y, float z) {
        Minecraft.getInstance().level.addParticle(CaveDust.getInstance().getConfig().getParticle(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
