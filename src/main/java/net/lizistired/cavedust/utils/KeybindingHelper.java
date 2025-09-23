package net.lizistired.cavedust.utils;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeybindingHelper {
    public static final Lazy<KeyMapping> keyBinding1 = Lazy.of(() -> new KeyMapping(
            "key.cavedust.toggle",
            InputConstants.Type.KEYSYM,// The translation key of the keybinding's name // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_KP_ADD, // The keycode of the key
            "category.cavedust.spook" // The translation key of the keybinding's category.
    ));
    public static final Lazy<KeyMapping> keyBinding2 = Lazy.of(() -> new KeyMapping(
            "key.cavedust.reload", // The translation key of the keybinding's name
            InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_KP_ENTER, // The keycode of the key
            "category.cavedust.spook" // The translation key of the keybinding's category.
    ));
    public static void registerKeyBindings(RegisterKeyMappingsEvent event)
    {
        event.register(keyBinding1.get());
        event.register(keyBinding2.get());
    }
}
