package net.lizistired.cavedust;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lizistired.cavedust.utils.MathHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

public class CaveDustPlumeParticleFactory extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    CaveDustPlumeParticleFactory(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider; //Sets the sprite provider from above to the sprite provider in the constructor method
        this.maxAge = 60; //20 ticks = 1 second
        this.scale = 10f;
        this.velocityX = velocityX; //The velX from the constructor parameters
        this.velocityY = -0.007f; //Allows the particle to slowly fall
        this.velocityZ = velocityZ;
        this.x = x; //The x from the constructor parameters
        this.y = y;
        this.z = z;
        this.collidesWithWorld = false;
        this.alpha = 0.5f; //Setting the alpha to 1.0f means there will be no opacity change until the alpha value is changed
        this.setSprite(spriteProvider); //Required
    }

    @Override
    public void tick() {
        super.tick();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        Vec3d particlePos = new Vec3d(this.x, this.y, this.z);
        double distanceFromParticleToPlayer = particlePos.distanceTo(player.getEyePos());
        this.alpha = net.minecraft.util.math.MathHelper.clamp((float) MathHelper.normalize(0, 50, distanceFromParticleToPlayer) * -1, -1, 1);
        if(this.alpha < 0.001f){
            this.markDead();
        }
        this.alpha -= 0.00005f;
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }




        public Particle createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CaveDustPlumeParticleFactory(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
