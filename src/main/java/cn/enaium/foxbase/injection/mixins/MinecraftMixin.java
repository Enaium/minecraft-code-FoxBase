package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.events.EventKeyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    public GuiScreen currentScreen;

    @Inject(at = @At("RETURN"), method = "startGame")
    private void startGame(CallbackInfo info) {
        FoxBase.instance.Start();
    }

    @Inject(at = @At("HEAD"), method = "shutdownMinecraftApplet")
    private void shutdownMinecraftApplet(CallbackInfo info) {
        FoxBase.instance.Stop();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER), method = "runTick")
    private void runTick(CallbackInfo info) {
        if (Keyboard.getEventKeyState() && this.currentScreen == null) {
            new EventKeyboard(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()).call();
        }
    }

}