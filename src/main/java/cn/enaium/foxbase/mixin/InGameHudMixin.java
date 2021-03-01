package cn.enaium.foxbase.mixin;

import cn.enaium.cf4m.event.Listener;
import cn.enaium.foxbase.client.events.Render2DEvent;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    private void render(float partialTicks, CallbackInfo info) {
        new Render2DEvent(Listener.At.HEAD).call();
    }
}
