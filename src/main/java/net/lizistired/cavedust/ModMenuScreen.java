package net.lizistired.cavedust;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.*;
import net.lizistired.cavedust.utils.TranslatableTextHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import javax.annotation.Nullable;

public class ModMenuScreen extends GameGui {
    public ModMenuScreen(@Nullable Screen parent) {
        super(new TranslatableText("menu.cavedust.title"), parent);
    }

    @Override
    public void init() {
        int left = width / 2 - 100;
        int row = height / 4 + 14;

        CaveDustConfig config = CaveDust.getInstance().getConfig();
        TranslatableTextHelper transText = new TranslatableTextHelper();
        config.load();

        addButton(new Label(width / 2, 30)).setCentered().getStyle()
                .setText(getTitle());

        addButton(new Button(left, row += -60).onClick(sender -> {
            sender.getStyle().setText("menu.cavedust.global." + config.toggleCaveDust()).setTooltip(new TranslatableText("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));
        })).getStyle()
                .setText("menu.cavedust.global." + config.getCaveDustEnabled())
                .setTooltip(new TranslatableText("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));

        addButton(new Slider(left += -110, row += 24, -50, 0, config.getDimensionsMinX()))
                .onChange(config::setDimensionsMinX)
                .setTextFormat(transText::formatMinX)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.minX.tooltip"));

        addButton(new Slider(left, row += 24, -50, 0, config.getDimensionsMinY()))
                .onChange(config::setDimensionsMinY)
                .setTextFormat(transText::formatMinY)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.minY.tooltip"));

        addButton(new Slider(left, row += 24, -50, 0, config.getDimensionsMinZ()))
                .onChange(config::setDimensionsMinZ)
                .setTextFormat(transText::formatMinZ)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.minZ.tooltip"));

        addButton(new Slider(left += 220, row += -48, 0, 50, config.getDimensionsMaxX()))
                .onChange(config::setDimensionsMaxX)
                .setTextFormat(transText::formatMaxX)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.maxX.tooltip"));

        addButton(new Slider(left, row += 24, 0, 50, config.getDimensionsMaxY()))
                .onChange(config::setDimensionsMaxY)
                .setTextFormat(transText::formatMaxY)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.maxY.tooltip"));

        addButton(new Slider(left, row += 24, 0, 50, config.getDimensionsMaxZ()))
                .onChange(config::setDimensionsMaxZ)
                .setTextFormat(transText::formatMaxZ)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.maxZ.tooltip"));

        addButton(new Slider(left += -110, row += 24, -64, 319, config.getUpperLimit()))
                .onChange(config::setUpperLimit)
                .setTextFormat(transText::formatUpperLimit)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.upperlimit.tooltip"));

        addButton(new Slider(left, row += 24, -64, 319, config.getLowerLimit()))
                .onChange(config::setLowerLimit)
                .setTextFormat(transText::formatLowerLimit)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.lowerlimit.tooltip"));

        addButton(new Slider(left, row += 24, 0, 10, config.getParticleMultiplier()))
                .onChange(config::setParticleMultiplier)
                .setTextFormat(transText::formatParticleMultiplier)
                .getStyle().setTooltip(new TranslatableText("menu.cavedust.particlemultiplier.tooltip"));


        addButton(new Button(left, row += 24).onClick(sender -> {
            config.resetConfig();
            finish();
        })).getStyle().setText(new TranslatableText("menu.cavedust.reset")).setTooltip(new TranslatableText("menu.cavedust.reset.tooltip"));

        addButton(new Button(left, row += 180)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
    }
}
