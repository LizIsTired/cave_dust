package net.lizistired.cavedust;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;

public class CaveDustParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    // First four parameters are self-explanatory. The SpriteSet parameter is provided by the
    // ParticleProvider, see below. You may also add additional parameters as needed, e.g. xSpeed/ySpeed/zSpeed.
    public     CaveDustParticle(ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {

        super(clientWorld, x, y, z);
        this.spriteSet = spriteProvider; //Sets the sprite provider from above to the sprite provider in the constructor method
        this.lifetime = 200; //20 ticks = 1 second
        this.quadSize = 0.1f;
        this.xd = velocityX; //The velX from the constructor parameters
        this.yd = -0.007f; //Allows the particle to slowly fall
        this.zd = velocityZ;
        this.x = x; //The x from the constructor parameters
        this.y = y;
        this.z = z;
        this.hasPhysics = true;
        this.alpha = 1.0f; //Setting the alpha to 1.0f means there will be no opacity change until the alpha value is changed
        this.setSpriteFromAge(spriteSet); //Require
    }

    @Override
    public void tick() {
        super.tick();
        if(this.alpha < 0.0f){
            this.remove();
        }
        this.alpha -= 0.005f;
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

//    public static class Factory implements ParticleProvider<SimpleParticleType> {
//        private final SpriteSet spriteProvider;
//
//        public Factory(SpriteSet spriteProvider) {
//            this.spriteProvider = spriteProvider;
//        }
//
//
//        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
//            return new CaveDustParticleFactory(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
//        }
//    }
// The generic type of ParticleProvider must match the type of the particle type this provider is for.

}

