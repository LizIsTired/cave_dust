package net.lizistired.cavedust;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class CaveDustServer  implements ModInitializer {
    public static final SimpleParticleType CAVE_DUST_MOTE = FabricParticleTypes.simple();
    public static final SimpleParticleType CAVE_DUST_PLUME = FabricParticleTypes.simple();
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("cavedust", "cave_dust_mote"), CAVE_DUST_MOTE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("cavedust", "cave_dust_plume"), CAVE_DUST_PLUME);
    }
}
