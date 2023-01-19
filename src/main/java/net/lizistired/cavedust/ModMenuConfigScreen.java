package net.lizistired.cavedust;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.*;
import net.lizistired.cavedust.utils.TranslatableTextHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

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

        addButton(new Button(left, row += -60).onClick(sender -> {
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

        addButton(new Slider(left += -110, row += 24, -50, 0, config.getDimensionsMinX()))
                .onChange(config::setDimensionsMinX)
                .setTextFormat(transText::formatMinX)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.minX.tooltip"));

        addButton(new Slider(left, row += 24, -50, 0, config.getDimensionsMinY()))
                .onChange(config::setDimensionsMinY)
                .setTextFormat(transText::formatMinY)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.minY.tooltip"));

        addButton(new Slider(left, row += 24, -50, 0, config.getDimensionsMinZ()))
                .onChange(config::setDimensionsMinZ)
                .setTextFormat(transText::formatMinZ)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.minZ.tooltip"));

        addButton(new Slider(left += 220, row += -48, 0, 50, config.getDimensionsMaxX()))
                .onChange(config::setDimensionsMaxX)
                .setTextFormat(transText::formatMaxX)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.maxX.tooltip"));

        addButton(new Slider(left, row += 24, 0, 50, config.getDimensionsMaxY()))
                .onChange(config::setDimensionsMaxY)
                .setTextFormat(transText::formatMaxY)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.maxY.tooltip"));

        addButton(new Slider(left, row += 24, 0, 50, config.getDimensionsMaxZ()))
                .onChange(config::setDimensionsMaxZ)
                .setTextFormat(transText::formatMaxZ)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.maxZ.tooltip"));

        addButton(new Slider(left += -110, row += 24, -64, 319, config.getUpperLimit()))
                .onChange(config::setUpperLimit)
                .setTextFormat(transText::formatUpperLimit)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.upperlimit.tooltip"));

        addButton(new Slider(left, row += 24, -64, 319, config.getLowerLimit()))
                .onChange(config::setLowerLimit)
                .setTextFormat(transText::formatLowerLimit)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.lowerlimit.tooltip"));

        addButton(new Slider(left, row += 24, 1, 100, config.getParticleMultiplier()))
                .onChange(config::setParticleMultiplier)
                .setTextFormat(transText::formatParticleMultiplier)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.particlemultiplier.tooltip"));

        addButton(new Slider(left, row += 24, 0, 10, config.getVelocityRandomness()))
                .onChange(config::setVelocityRandomness)
                .setTextFormat(transText::formatVelocityRandomness)
                .getStyle().setTooltip(Text.translatable("menu.cavedust.velocityrandomness.tooltip"));


        addButton(new Button(left, row += 24).onClick(sender -> {
            config.resetConfig();
            finish();
        })).getStyle().setText(Text.translatable("menu.cavedust.reset")).setTooltip(Text.translatable("menu.cavedust.reset.tooltip"));

        addButton(new Button(left, row += 60)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
    }
}
