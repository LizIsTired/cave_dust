package net.lizistired.cavedust;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;

public class CaveDustMoteParticleFactory extends SingleQuadParticle {
    private final SpriteSet spriteProvider;
    private double getHeatValue(BlockPos pos) {
        var state = this.level.getBlockState(pos);

        if (state.is(Blocks.LAVA)) return 0.4;
        if (state.is(Blocks.MAGMA_BLOCK)) return 0.2;
        if (state.is(Blocks.CAMPFIRE)) return 0.25;
        if (state.is(Blocks.SOUL_CAMPFIRE)) return 0.2;
        if (state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH)) return 0.1;

        return 0.0;
    }
    CaveDustMoteParticleFactory(ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider.first());
        this.spriteProvider = spriteProvider; //Sets the sprite provider from above to the sprite provider in the constructor method
        this.lifetime = 200; //20 ticks = 1 second
        this.quadSize = 0.05f;
        this.xd = velocityX; //The velX from the constructor parameters
        this.yd = -0.007f; //Allows the particle to slowly fall
        this.zd = velocityZ;
        this.x = x; //The x from the constructor parameters
        this.y = y;
        this.z = z;
        this.hasPhysics = false;
        this.alpha = 1.0f; //Setting the alpha to 1.0f means there will be no opacity change until the alpha value is changed
        this.setSpriteFromAge(spriteProvider); //Required
    }

    @Override
    public void tick() {
        super.tick();

        this.setSpriteFromAge(this.spriteProvider);

        this.alpha = 1.0f - ((float)this.age / (float)this.lifetime);

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            double dx = this.x - player.getX();
            double dy = this.y - player.getY();
            double dz = this.z - player.getZ();

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double influenceRadius = 3.0;

            if (distance < influenceRadius) {
                double strength = (1.0 - (distance / influenceRadius)) * 0.1;

                this.xd += player.getDeltaMovement().x * strength;
                this.yd += player.getDeltaMovement().y * strength * 0.2;
                this.zd += player.getDeltaMovement().z * strength;
            }
        }

        if (this.level.canSeeSky(BlockPos.containing(this.x, this.y, this.z))) {
            this.xd *= 0.98;
            this.zd *= 0.98;
            this.yd -= 0.01;
        } else {
            this.xd *= 0.995;
            this.zd *= 0.995;
            this.yd += (this.random.nextFloat() - 0.5f) * 0.002f;
        }

        this.xd += (this.random.nextFloat() - 0.5f) * 0.002f;
        this.zd += (this.random.nextFloat() - 0.5f) * 0.002f;

        BlockPos origin = BlockPos.containing(this.x, this.y, this.z);

        double heatStrength = 0.0;

        BlockPos[] offsets = {
                origin,
                origin.above(),
                origin.below(),
                origin.north(),
                origin.south(),
                origin.east(),
                origin.west()
        };

        for (BlockPos pos : offsets) {
            double heat = getHeatValue(pos);

            if (heat > 0) {
                double distSq = origin.distSqr(pos);
                heatStrength += heat / (distSq + 1.0);
            }
        }

        if (heatStrength > 0) {
            double targetLift = Math.min(heatStrength * 0.02, 0.03);

            this.yd = this.yd * 0.9 + targetLift * 0.1;

            this.xd += (this.random.nextFloat() - 0.5f) * targetLift * 0.3;
            this.zd += (this.random.nextFloat() - 0.5f) * targetLift * 0.3;
        }

        this.yd = Math.min(this.yd, 0.05);
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSLUCENT;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
            return new CaveDustMoteParticleFactory(level, x, y, z, xAux, yAux, zAux, this.spriteProvider);
        }
    }
}
