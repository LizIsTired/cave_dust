package net.fabricmc.example;

import net.fabricmc.example.utils.JsonFile;

import java.nio.file.Path;
import java.util.Vector;

public class DustConfig extends JsonFile {
    private transient final Dust dust;
    private Vector dimensions;
    private boolean dustEnabled = true;

    public DustConfig(Path file, Dust dust) {
        super(file);

        this.dust = dust;
    }

    public Vector setDimensions(){

        return null;
    }

    public Vector getDimensions(){

        return null;
    }

    public boolean toggleDust(){
    dustEnabled = !dustEnabled;
    save();
    return dustEnabled;
    }

    public boolean getDustEnabled(){
        return dustEnabled;
    }
}
