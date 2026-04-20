package net.lizistired.cavedust;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.Button;
import com.minelittlepony.common.client.gui.element.Label;
import com.minelittlepony.common.client.gui.element.Slider;
import net.lizistired.cavedust.utils.TranslatableTextHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

public class ModMenuConfigScreenAdvanced extends GameGui {
    public ModMenuConfigScreenAdvanced(@Nullable Screen parent) {
        super(Component.translatable("menu.cavedust.title.advanced"), parent);
    }

    @Override
    public void init() {
        int left = width / 2 - 100;
        int row = height / 4 + 14;

        CaveDustConfig config = CaveDust.getInstance().getConfig();
        TranslatableTextHelper transText = new TranslatableTextHelper();;
        config.load();

        addButton(new Label(width / 2, 30)).setCentered().getStyle()
                .setText(getTitle());


        /*addButton(new Button(left, row += 24).onClick(sender -> {
            sender.getStyle().setText("menu.cavedust.enhanceddetection." + config.setEnhancedDetection()).setTooltip(Text.translatable("menu.cavedust.enhanceddetection.tooltip"));
        })).getStyle()
                .setText("menu.cavedust.enhanceddetection." + config.getEnhancedDetection())
                .setTooltip(Text.translatable("menu.cavedust.enhanceddetection.tooltip"));*/


        /*addButton(new Slider(left, row += 48, -64, 319, config.getUpperLimit()))
                .onChange(config::setUpperLimit)
                .setTextFormat(transText::formatUpperLimit)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.upperlimit.tooltip"));

        addButton(new Slider(left, row += 24, -64, 319, config.getLowerLimit()))
                .onChange(config::setLowerLimit)
                .setTextFormat(transText::formatLowerLimit)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.lowerlimit.tooltip"));*/

        addButton(new Slider(left, row += 24, 1, 100, config.getParticleMultiplier()))
                .onChange(config::setParticleMultiplier)
                .setTextFormat(transText::formatParticleMultiplier)
                .getStyle().setTooltip(Component.translatable("menu.cavedust.particlemultiplier.tooltip"));

        addButton(new Button(left, row += 24).onClick(sender ->{
            config.iterateParticle();
            sender.getStyle().setText("Particle: " + (getNameOfParticle()));
        })).getStyle().setText("Particle: " + (getNameOfParticle()))
                .setTooltip(Component.translatable("menu.cavedust.particle.tooltip"));

        addButton(new Slider(left, row += 24, 1, 50, config.getDimensionWidth()))
                .onChange(config::setDimensionWidth)
                .setTextFormat(transText::formatMaxWidth)
                .getStyle().setTooltip(Component.translatable("menu.cavedust.width.tooltip"));


        addButton(new Button(left, row += 120).onClick(sender -> {
            config.resetConfig();
            finish();
            minecraft.setScreen(new ModMenuConfigScreenAdvanced(parent));
        })).getStyle().setText(Component.translatable("menu.cavedust.reset")).setTooltip(Component.translatable("menu.cavedust.reset.tooltip"));

        addButton(new Button(left, row += 24)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");

    }


    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(context, mouseX, mouseY, partialTicks);
    }

    private String getNameOfParticle(){
        CaveDustConfig config = CaveDust.getInstance().getConfig();
        config.load();
        try {
            return BuiltInRegistries.PARTICLE_TYPE.wrapAsHolder((ParticleType<?>) config.getParticleID()).getRegisteredName();
        } catch (NoSuchElementException e){
            CaveDust.LOGGER.error(String.valueOf(e));
            return "null";
        }
    }
}
