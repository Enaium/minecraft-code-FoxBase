package cn.enaium.foxbase.mixin;

import cn.enaium.cf4m.event.events.KeyboardEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen == null && action == GLFW.GLFW_PRESS) {
            new KeyboardEvent(keyCode).call();
        }
    }
}