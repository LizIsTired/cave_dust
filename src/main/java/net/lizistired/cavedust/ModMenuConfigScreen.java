package net.lizistired.cavedust;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.*;
import com.minelittlepony.common.client.gui.element.Button;
import com.minelittlepony.common.client.gui.element.Label;
import net.lizistired.cavedust.utils.TranslatableTextHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.NoSuchElementException;

public class ModMenuConfigScreen extends GameGui {
    public ModMenuConfigScreen(@Nullable Screen parent) {
        super(Component.translatable("menu.cavedust.title"), parent);
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
            sender.getStyle().setText("menu.cavedust.global." + config.toggleCaveDust()).setTooltip(Component.translatable("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));
        })).getStyle()
                .setText("menu.cavedust.global." + config.getCaveDustEnabled())
                .setTooltip(Component.translatable("menu.cavedust.global.tooltip." + config.getCaveDustEnabled()));

        addButton(new Button(left, row += 120)
                .onClick(sender ->
                        minecraft.setScreen(new ModMenuConfigScreenAdvanced(parent)
                        ))).getStyle().setText("menu.cavedust.title.advanced");

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
