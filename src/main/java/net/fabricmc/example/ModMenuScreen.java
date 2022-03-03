package net.fabricmc.example;

import com.minelittlepony.common.client.gui.GameGui;
import com.minelittlepony.common.client.gui.element.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import javax.annotation.Nullable;

public class ModMenuScreen extends GameGui {
    public ModMenuScreen(@Nullable Screen parent) {
        super(new TranslatableText("menu.dust.title"), parent);
    }

    @Override
    public void init() {
        int left = width / 2 - 100;
        int row = height / 4 + 14;

        DustConfig config = Dust.getInstance().getConfig();

        addButton(new Label(width / 2, 30)).setCentered().getStyle()
                .setText(getTitle());

        addButton(new Button(left, row += 24).onClick(sender -> {
            sender.getStyle().setText("menu.dust.global." + config.toggleDust());
        })).getStyle()
                .setText("menu.dust.global." + config.getDustEnabled());

        addButton(new Button(left, row += 34)
                .onClick(sender -> finish())).getStyle()
                .setText("gui.done");
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
    }
}
