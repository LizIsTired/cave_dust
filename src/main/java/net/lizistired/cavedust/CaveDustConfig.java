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


    private int dimensionX = 5;
    private int dimensionY = 5;
    private int dimensionZ = 5;
    private int velocityRandomness = 1;

    private boolean caveDustEnabled = true;
    private boolean seaLevelCheck = true;
    private boolean superFlatStatus = false;
    private float upperLimit = 64;
    private float lowerLimit = -64;
    private int particleMultiplier = 1;

    private int particleMultiplierMultiplier = 10;

    private int particleID = WHITE_ASH_ID;

    public CaveDustConfig(Path file, net.lizistired.cavedust.CaveDust caveDust) {
        super(file);
        this.CaveDust = caveDust;
    }

    public float setDimensionsX(float size){
        if (this.dimensionX != size) {
            this.dimensionX = (int)size;
            save();
        }
        return getDimensionsX();
    }

    public float setDimensionsY(float size){
        if (this.dimensionY != size) {
            this.dimensionY = (int)size;
            save();
        }
        return getDimensionsY();
    }

    public float setDimensionsZ(float size){
        if (this.dimensionZ != size) {
            this.dimensionZ = (int)size;
            save();
        }
        return getDimensionsZ();
    }

    public float getDimensionsX(){
        return dimensionX;
    }

    public float getDimensionsY(){
        return dimensionY;
    }

    public float getDimensionsZ(){
        return dimensionZ;
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

    public int getParticleMultiplierMultiplier(){
        return particleMultiplierMultiplier;
    }

    public float setParticleMultiplierMultiplier(float particleMultiplierMultiplier){
        this.particleMultiplierMultiplier = (int) particleMultiplierMultiplier;
        save();
        return getParticleMultiplierMultiplier();
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
        //particleName = particleType;
        save();
        return getParticle();
    }

    public ParticleEffect getParticle(){
        return (ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(Registries.PARTICLE_TYPE.getEntry(getParticleID()).get().getKey().get().getValue().toString().toLowerCase()));
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

    public void iterateParticle(){
        if(getParticleID() > Registries.PARTICLE_TYPE.size() - 2) {
            particleID = 1;
            save();
        } else {
            particleID = getParticleID() + 1;
            save();
        }
    }

    public void setParticleID(int particleID){
        this.particleID = particleID;
        save();
    }

    public int getParticleID(){
        if ((!Registries.PARTICLE_TYPE.getEntry(particleID).isPresent())) {
            setParticleID(WHITE_ASH_ID);
        }
        return particleID;
    }

    public void resetConfig(){
        dimensionX = 5;
        dimensionY = 5;
        dimensionZ = 5;

        upperLimit = 64;
        lowerLimit = -64;

        particleMultiplier = 1;
        particleMultiplierMultiplier = 10;

        seaLevelCheck = true;
        caveDustEnabled = true;
        particleID = WHITE_ASH_ID;
        save();
    }
}
