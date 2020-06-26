package cn.enaium.foxbase.mixin;

import cn.enaium.foxbase.event.events.EventRender2D;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
            ordinal = 4), method = "render")
    private void render(MatrixStack matrixStack, float partialTicks, CallbackInfo info) {
        new EventRender2D(matrixStack).call();
    }
}
