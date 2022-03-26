package net.lizistired.cavedust.utils;

import com.minelittlepony.common.client.gui.element.AbstractSlider;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TranslatableTextHelper {
    public Text formatMinX(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.minX", (int)Math.floor(slider.getValue()));
    }
    public Text formatMinY(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.minY", (int)Math.floor(slider.getValue()));
    }
    public Text formatMinZ(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.minZ", (int)Math.floor(slider.getValue()));
    }
    public Text formatMaxX(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.maxX", (int)Math.floor(slider.getValue()));
    }
    public Text formatMaxY(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.maxY", (int)Math.floor(slider.getValue()));
    }
    public Text formatMaxZ(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.maxZ", (int)Math.floor(slider.getValue()));
    }
    public Text formatUpperLimit(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.upperlimit", (int)Math.floor(slider.getValue()));
    }
    public Text formatLowerLimit(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.lowerlimit", (int)Math.floor(slider.getValue()));
    }
    public Text formatParticleMultiplier(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.particlemultiplier", (int)Math.floor(slider.getValue()));
    }
    public Text formatVelocityRandomness(AbstractSlider<Float> slider) {
        return new TranslatableText("menu.cavedust.velocityrandomness", (int)Math.floor(slider.getValue()));
    }
}
