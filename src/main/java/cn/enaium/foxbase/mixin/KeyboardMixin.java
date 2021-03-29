package cn.enaium.foxbase.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.event.Events;
import cn.enaium.foxbase.client.event.Events.KeyboardEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null) {
            new KeyboardEvent(keyCode).call();
            CF4M.module.onKey(keyCode);
        }
    }
}