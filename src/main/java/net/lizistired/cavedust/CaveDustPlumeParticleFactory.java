package net.lizistired.cavedust;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lizistired.cavedust.utils.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CaveDustPlumeParticleFactory extends SingleQuadParticle {
    private final SpriteSet spriteProvider;
    CaveDustPlumeParticleFactory(ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider.first());
        this.spriteProvider = spriteProvider; //Sets the sprite provider from above to the sprite provider in the constructor method
        this.lifetime = 60; //20 ticks = 1 second
        this.quadSize = 10f;
        this.xd = velocityX; //The velX from the constructor parameters
        this.yd = -0.007f; //Allows the particle to slowly fall
        this.zd = velocityZ;
        this.x = x; //The x from the constructor parameters
        this.y = y;
        this.z = z;
        this.hasPhysics = false;
        this.alpha = 0.5f; //Setting the alpha to 1.0f means there will be no opacity change until the alpha value is changed
    }

    @Override
    public void tick() {
        super.tick();
        LocalPlayer player = Minecraft.getInstance().player;
        Vec3 particlePos = new Vec3(this.x, this.y, this.z);
        double distanceFromParticleToPlayer = particlePos.distanceTo(player.getEyePosition());
        this.alpha = net.minecraft.util.Mth.clamp((float) MathHelper.normalize(0, 50, distanceFromParticleToPlayer) * -1, -1, 1);
        if(this.alpha < 0.001f){
            this.remove();
        }
        this.alpha -= 0.00005f;
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
            return new CaveDustPlumeParticleFactory(
                    level,
                    x,
                    y,
                    z,
                    xAux,
                    yAux,
                    zAux,
                    this.spriteProvider);
        }
    }
}
