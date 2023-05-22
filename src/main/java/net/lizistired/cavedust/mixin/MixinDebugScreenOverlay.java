package net.lizistired.cavedust.mixin;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.lizistired.cavedust.CaveDust.PARTICLE_AMOUNT;
import static net.lizistired.cavedust.utils.ParticleSpawnUtil.shouldParticlesSpawn;

@Mixin(DebugHud.class)
public abstract class MixinDebugScreenOverlay {
    @Inject(method = "getRightText", at = @At("RETURN"))
    private void appendShaderPackText(CallbackInfoReturnable<List<String>> cir) {
        List<String> messages = cir.getReturnValue();

        messages.add("");
        messages.add("Particle amount evaluated: " + PARTICLE_AMOUNT);
        messages.add("");
    }
}
