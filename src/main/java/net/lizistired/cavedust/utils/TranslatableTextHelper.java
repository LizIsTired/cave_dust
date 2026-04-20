package net.lizistired.cavedust.utils;

import com.minelittlepony.common.client.gui.element.AbstractSlider;
import net.minecraft.network.chat.Component;

public class TranslatableTextHelper {
    public Component formatMaxWidth(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.width", (int)Math.floor(slider.getValue()));
    }
    public Component formatMaxHeight(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.height", (int)Math.floor(slider.getValue()));
    }
    public Component formatUpperLimit(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.upperlimit", (int)Math.floor(slider.getValue()));
    }
    public Component formatLowerLimit(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.lowerlimit", (int)Math.floor(slider.getValue()));
    }
    public Component formatParticleMultiplier(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.particlemultiplier", (int)Math.floor(slider.getValue()));
    }

    public Component formatParticleMultiplierMultiplier(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.particlemultipliermultiplier", (int)Math.floor(slider.getValue()));
    }
    public Component formatVelocityRandomness(AbstractSlider<Float> slider) {
        return Component.translatable("menu.cavedust.velocityrandomness", (int) Math.floor(slider.getValue()));
    }
}
