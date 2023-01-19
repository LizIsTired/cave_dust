package net.lizistired.cavedust;

import net.lizistired.cavedust.utils.JsonFile;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import static net.lizistired.cavedust.CaveDust.*;
import static net.lizistired.cavedust.utils.MathHelper.*;

import java.nio.file.Path;

public class CaveDustConfig extends JsonFile {
    private transient final net.lizistired.cavedust.CaveDust CaveDust;


    private int dimensionMaxX = 5;
    private int dimensionMaxY = 5;
    private int dimensionMaxZ = 5;
    private int dimensionMinX = -5;
    private int dimensionMinY = -5;
    private int dimensionMinZ = -5;
    private int velocityRandomness = 1;

    private boolean caveDustEnabled = true;
    private String particleName = "white_ash";
    private boolean seaLevelCheck = true;
    private boolean superFlatStatus = false;
    //private boolean enhancedDetection = true;
    private float upperLimit = 64;
    private float lowerLimit = -64;
    private int particleMultiplier = 1;

    public CaveDustConfig(Path file, net.lizistired.cavedust.CaveDust caveDust) {
        super(file);
        this.CaveDust = caveDust;
    }

    public float setDimensionsMinX(float size){
        if (this.dimensionMinX != size) {
            this.dimensionMinX = (int)size;
            save();
        }
        return getDimensionsMinX();
    }

    public float setDimensionsMinY(float size){
        if (this.dimensionMinY != size) {
            this.dimensionMinY = (int)size;
            save();
        }
        return getDimensionsMinY();
    }

    public float setDimensionsMinZ(float size){
        if (this.dimensionMinZ != size) {
            this.dimensionMinZ = (int)size;
            save();
        }
        return getDimensionsMinZ();
    }

    public float getDimensionsMinX(){
        return dimensionMinX;
    }

    public float getDimensionsMinY(){
        return dimensionMinY;
    }

    public float getDimensionsMinZ(){
        return dimensionMinZ;
    }

    public float setDimensionsMaxX(float size){
        if (this.dimensionMaxX != size) {
            this.dimensionMaxX = (int)size;
            save();
        }
        return getDimensionsMaxX();
    }

    public float setDimensionsMaxY(float size){
        if (this.dimensionMaxY != size) {
            this.dimensionMaxY = (int)size;
            save();
        }
        return getDimensionsMaxY();
    }

    public float setDimensionsMaxZ(float size){
        if (this.dimensionMaxZ != size) {
            this.dimensionMaxZ = (int)size;
            save();
        }
        return getDimensionsMaxZ();
    }

    public float getDimensionsMaxX(){
        return dimensionMaxX;
    }

    public float getDimensionsMaxY(){
        return dimensionMaxY;
    }

    public float getDimensionsMaxZ(){
        return dimensionMaxZ;
    }

    public float setUpperLimit(float upperLimit){
        if (this.upperLimit - 1 < getLowerLimit())
        {
            return getUpperLimit();
        }
        if (this.upperLimit != upperLimit) {
            this.upperLimit = (int)upperLimit;
            save();
        }
        return getUpperLimit();
    }

    public float getUpperLimit(){
        return upperLimit;
    }

    public float setLowerLimit(float lowerLimit){
        if (this.lowerLimit + 1 > getUpperLimit())
        {
            return getLowerLimit();
        }
        if (this.lowerLimit != lowerLimit) {
            this.lowerLimit = (int)lowerLimit;
            save();
        }
        return getLowerLimit();
    }

    public float getLowerLimit(){
        return lowerLimit;
    }

    public int getParticleMultiplier(){
        return particleMultiplier;
    }

    public float setParticleMultiplier(float particleMultiplier){
        this.particleMultiplier = (int) particleMultiplier;
        save();
        return getParticleMultiplier();
    }

    public boolean toggleCaveDust(){
        caveDustEnabled = !caveDustEnabled;
        save();
        return caveDustEnabled;
    }

    public boolean getCaveDustEnabled(){
        return caveDustEnabled;
    }

    public ParticleEffect setParticle(String particleType){
        particleName = particleType;
        save();
        return getParticle();
    }

    public ParticleEffect getParticle(){
        try {
            return (ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(particleName.toLowerCase()));
        } catch (ClassCastException e) {
            LOGGER.error(e + "\nThere was an error loading the specified particle from the config, make sure a valid particle name is specified. Falling back to \"minecraft:white_ash\".");
            particleName = "minecraft:white_ash";
            save();
            return ParticleTypes.WHITE_ASH;
        }
    }

    public boolean getSeaLevelCheck() {
        return seaLevelCheck;
    }

    public boolean setSeaLevelCheck() {
        seaLevelCheck = !seaLevelCheck;
        save();
        return getSeaLevelCheck();
    }

    public float getVelocityRandomnessRandom(){
        if (velocityRandomness == 0) {return 0;}
        return (float) generateRandomDouble(-velocityRandomness, velocityRandomness);
    }

    public float getVelocityRandomness(){
        return velocityRandomness;
    }

    public float setVelocityRandomness(float velocityRandomness){
        this.velocityRandomness = (int) velocityRandomness;
        save();
        return getVelocityRandomness();
    }

    public boolean getSuperFlatStatus(){
        return superFlatStatus;
    }

    public boolean setSuperFlatStatus(){
        superFlatStatus = !superFlatStatus;
        save();
        return getSuperFlatStatus();
    }

    /*public boolean getEnhancedDetection(){
        return enhancedDetection;
    }

    public boolean setEnhancedDetection(){
        enhancedDetection = !enhancedDetection;
        save();
        return getEnhancedDetection();
    }*/

    public void resetConfig(){
        dimensionMinX = -5;
        dimensionMinY = -5;
        dimensionMinZ = -5;

        dimensionMaxX = 5;
        dimensionMaxY = 5;
        dimensionMaxZ = 5;

        upperLimit = 64;
        lowerLimit = -64;

        particleMultiplier = 1;

        seaLevelCheck = true;
        caveDustEnabled = true;
        particleName = "minecraft:white_ash";
        save();
    }
}
