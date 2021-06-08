package cn.enaium.foxbase.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.event.Events;
import cn.enaium.foxbase.client.event.Events.Render3DEvent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(
            at = {@At(value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    ordinal = 0)},
            method = {
                    "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"})
    private void onRenderWorld(float partialTicks, long finishTimeNano,
                               MatrixStack matrixStack, CallbackInfo ci) {
        CF4M.EVENT.post(new Render3DEvent(partialTicks));
    }
}
