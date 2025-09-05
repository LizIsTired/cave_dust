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

        addButton(new Button(left, row += 120)
                .onClick(sender ->
                        client.setScreen(new ModMenuConfigScreenAdvanced(parent)
                        )));

        addButton(new Button(left, row += 24)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");

    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        renderBackground(context, mouseX, mouseY, partialTicks);
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
