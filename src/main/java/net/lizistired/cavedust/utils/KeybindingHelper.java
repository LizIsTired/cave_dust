package net.lizistired.cavedust.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.lizistired.cavedust.CaveDust;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

public class KeybindingHelper {


    public static KeyMapping keyBinding1;
    public static KeyMapping keyBinding2;


    public static void registerKeyBindings(){
        KeyMapping.Category category = new KeyMapping.Category(Identifier.fromNamespaceAndPath(CaveDust.LOGGER.getName(), "spook"));
        keyBinding1 = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.cavedust.toggle",
                InputConstants.Type.KEYSYM,// The translation key of the keybinding's name // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_KP_ADD,
                category
        ));
        keyBinding2 = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.cavedust.reload", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_KP_ENTER, // The keycode of the key
                category
                // The translation key of the keybinding's category.
        ));
    }
}
