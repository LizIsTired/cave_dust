package net.lizistired.cavedust;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value=CaveDust.MODID, dist= Dist.CLIENT)
public class CaveDustClient {
    public CaveDustClient(FMLModContainer modContainer)
    {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
