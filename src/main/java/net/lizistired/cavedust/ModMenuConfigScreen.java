package net.lizistired.cavedust;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.*;
import net.lizistired.cavedust.utils.TranslatableTextHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

public class ModMenuConfigScreen extends GameGui {
    public ModMenuConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("menu.cavedust.title"), parent);
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

        addButton(new Button(left += -110, row += -60).onClick(sender -> {
            sender.getStyle().setText("menu.cavedust.global." + config.toggleCaveDust()).setTooltip(Text.translatable("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));
        })).getStyle()
                .setText("menu.cavedust.global." + config.getCaveDustEnabled())
                .setTooltip(Text.translatable("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));

        /*addButton(new Button(left, row += 24).onClick(sender -> {
            sender.getStyle().setText("menu.cavedust.enhanceddetection." + config.setEnhancedDetection()).setTooltip(Text.translatable("menu.cavedust.enhanceddetection.tooltip"));
        })).getStyle()
                .setText("menu.cavedust.enhanceddetection." + config.getEnhancedDetection())
                .setTooltip(Text.translatable("menu.cavedust.enhanceddetection.tooltip"));*/

        addButton(new Button(left, row += 24).onClick(sender -> {
            sender.getStyle().setText("menu.cavedust.superflatstatus." + config.setSuperFlatStatus()).setTooltip(Text.translatable("menu.cavedust.superflatstatus.tooltip"));
        })).getStyle()
                .setText("menu.cavedust.superflatstatus." + config.getSuperFlatStatus())
                .setTooltip(Text.translatable("menu.cavedust.superflatstatus.tooltip"));



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
                .getStyle().setTooltip(Text.translatable("menu.cavedust.particlemultiplier.tooltip"));

        addButton(new Slider(left, row += 24, 1, 100, config.getParticleMultiplierMultiplier()))
                .onChange(config::setParticleMultiplierMultiplier)
                .setTextFormat(transText::formatParticleMultiplierMultiplier)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.particlemultipliermultiplier.tooltip"));
        addButton(new Button(left, row += 24).onClick(sender ->{
            config.iterateParticle();
            sender.getStyle().setText("Particle: " + (getNameOfParticle()));
        })).getStyle().setText("Particle: " + (getNameOfParticle()))
                .setTooltip(Text.translatable("menu.cavedust.particle.tooltip"));

        addButton(new Slider(left += 220, row -= 96, 1, 50, config.getDimensionWidth()))
                .onChange(config::setDimensionWidth)
                .setTextFormat(transText::formatMaxWidth)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.width.tooltip"));

        addButton(new Slider(left, row += 24, 1, 50, config.getDimensionHeight()))
                .onChange(config::setDimensionHeight)
                .setTextFormat(transText::formatMaxHeight)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.height.tooltip"));

        addButton(new Slider(left, row += 24, 0, 10, config.getVelocityRandomness()))
                .onChange(config::setVelocityRandomness)
                .setTextFormat(transText::formatVelocityRandomness)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.velocityrandomness.tooltip"));


        addButton(new Button(left -= 110, row += 120).onClick(sender -> {
            config.resetConfig();
            finish();
            client.setScreen(new ModMenuConfigScreen(parent));
        })).getStyle().setText(Text.translatable("menu.cavedust.reset")).setTooltip(Text.translatable("menu.cavedust.reset.tooltip"));

        addButton(new Button(left, row += 24)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");

    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    private String getNameOfParticle(){
        CaveDustConfig config = CaveDust.getInstance().getConfig();
        config.load();
        try {
            return Registries.PARTICLE_TYPE.getEntry((ParticleType<?>) config.getParticleID()).getIdAsString();
        } catch (NoSuchElementException e){
            CaveDust.LOGGER.error(String.valueOf(e));
            return "null";
        }
    }
}
