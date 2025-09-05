package net.lizistired.cavedust;

import com.google.common.collect.ImmutableList;
import net.lizistired.cavedust.utils.JsonFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import static net.lizistired.cavedust.CaveDust.*;
import static net.lizistired.cavedust.utils.MathHelper.*;

import java.nio.file.Path;
import java.util.*;

public class CaveDustConfig extends JsonFile {
    private transient final net.lizistired.cavedust.CaveDust CaveDust;


    private int width = 10;
    private int height = 10;
    private int velocityRandomness = 0;

    private boolean caveDustEnabled = true;
    private boolean seaLevelCheck = true;
    private boolean superFlatStatus = false;
    private float upperLimit = 64;
    private float lowerLimit = -64;
    private int particleMultiplier = 1;

    int listNumber = 0;

    private int particleMultiplierMultiplier = 10;

    List<Identifier> list = List.of(Registries.PARTICLE_TYPE.getIds().toArray(new Identifier[0]));

    Identifier newId = Identifier.of("cavedust", "cave_dust_mote");

    public CaveDustConfig(Path file, net.lizistired.cavedust.CaveDust caveDust) {
        super(file);
        this.CaveDust = caveDust;
    }

    public float setDimensionWidth(float size){
        if (this.width != size) {
            this.width = (int)size;
            save();
        }
        return getDimensionWidth();
    }

    public float setDimensionHeight(float size){
        if (this.height != size) {
            this.height = (int)size;
            save();
        }
        return getDimensionHeight();
    }

    public float getDimensionWidth(){
        return width;
    }

    public float getDimensionHeight(){
        return height;
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

    //todo
    //public Identifier setParticle(String particleType){
        //particleName = particleType;
        //save();
        //return getParticle().get().getKey().get().getValue();
   //}

    //public ParticleEffect getParticle(){
    //    try {
    //        return Registries.PARTICLE_TYPE.getOptional(Identifier.of(Registries.PARTICLE_TYPE.getOptional(getParticleID()).get().getKey().get().getValue().toString().toLowerCase()));
    //    } catch (ClassCastException e) {
    //        MinecraftClient.getInstance().player.sendMessage(Text.translatable("debug.cavedust.particleerror"), true);
    //        LOGGER.error("Cannot spawn particle, check config.");
    //        iterateParticle();
    //        save();
    //        return getParticle();
    //    }
    //}

    public ParticleEffect getParticle(){
        try{
            return (ParticleEffect) Registries.PARTICLE_TYPE.get(newId);
        }
        catch (ClassCastException e){
            iterateParticle();
            return getParticle();
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

    public void iterateParticle() {
        try {
            listNumber = listNumber + 1;
            newId = list.get(listNumber);
        } catch (IndexOutOfBoundsException e){
            newId = list.get(0);
            listNumber = 0;
        }
        save();
    }

    public ParticleEffect getParticleID(){
        return getParticle();
    }

    public void resetConfig(){
        width = 10;
        height = 10;

        upperLimit = 64;
        lowerLimit = -64;

        particleMultiplier = 1;
        particleMultiplierMultiplier = 10;
        velocityRandomness = 0;

        newId = Identifier.of("cavedust", "cave_dust");

        seaLevelCheck = true;
        caveDustEnabled = true;
        save();
    }
}
