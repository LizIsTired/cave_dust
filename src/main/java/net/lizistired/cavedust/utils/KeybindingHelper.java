package net.lizistired.cavedust.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindingHelper {

    public static KeyBinding keyBinding1;
    public static KeyBinding keyBinding2;


    public static void registerKeyBindings(){
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cavedust.toggle",
                InputUtil.Type.KEYSYM,// The translation key of the keybinding's name // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_KP_ADD, // The keycode of the key
                "category.cavedust.spook" // The translation key of the keybinding's category.
        ));
        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cavedust.reload", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_KP_ENTER, // The keycode of the key
                "category.cavedust.spook" // The translation key of the keybinding's category.
        ));
    }
}
