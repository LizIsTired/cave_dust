package net.lizistired.cavedust;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CaveDustServer  implements ModInitializer {
    public static final SimpleParticleType CAVE_DUST_MOTE = FabricParticleTypes.simple();
    public static final SimpleParticleType CAVE_DUST_PLUME = FabricParticleTypes.simple();
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of("cavedust", "cave_dust_mote"), CAVE_DUST_MOTE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of("cavedust", "cave_dust_plume"), CAVE_DUST_PLUME);
    }
}
