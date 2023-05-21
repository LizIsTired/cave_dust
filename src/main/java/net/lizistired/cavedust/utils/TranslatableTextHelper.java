package net.lizistired.cavedust.utils;

import com.minelittlepony.common.client.gui.element.AbstractSlider;
import net.minecraft.text.Text;

public class TranslatableTextHelper {
    public Text formatMaxX(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.X", (int)Math.floor(slider.getValue()));
    }
    public Text formatMaxY(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.Y", (int)Math.floor(slider.getValue()));
    }
    public Text formatMaxZ(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.Z", (int)Math.floor(slider.getValue()));
    }
    public Text formatUpperLimit(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.upperlimit", (int)Math.floor(slider.getValue()));
    }
    public Text formatLowerLimit(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.lowerlimit", (int)Math.floor(slider.getValue()));
    }
    public Text formatParticleMultiplier(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.particlemultiplier", (int)Math.floor(slider.getValue()));
    }
    public Text formatVelocityRandomness(AbstractSlider<Float> slider) {
        return Text.translatable("menu.cavedust.velocityrandomness", (int) Math.floor(slider.getValue()));
    }
}
