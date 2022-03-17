package net.lizistired.cavedust.mixin;


import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Objects;
import static net.lizistired.cavedust.utils.MathHelper.*;

@Mixin(DebugHud.class)
public abstract class MixinDebugScreenOverlay {
    @Unique
    private static final List<BufferPoolMXBean> pools = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);

    @Unique
    private static final BufferPoolMXBean directPool;

    static {
        BufferPoolMXBean found = null;

        for (BufferPoolMXBean pool : pools) {
            if (pool.getName().equals("direct")) {
                found = pool;
                break;
            }
        }

        directPool = Objects.requireNonNull(found);
    }
    @Inject(method = "getRightText", at = @At("RETURN"))
    private void appendShaderPackText(CallbackInfoReturnable<List<String>> cir) {
        List<String> messages = cir.getReturnValue();

        messages.add("");
        messages.add("Should particles spawn: " + shouldParticlesSpawn);
        messages.add("");
    }
}
