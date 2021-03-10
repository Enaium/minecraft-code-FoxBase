package cn.enaium.foxbase.mixins;

import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.foxbase.client.FoxBase;
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
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    public GuiScreen currentScreen;

    @Inject(at = @At("RETURN"), method = "startGame")
    private void startGame(CallbackInfo info) {
        FoxBase.instance.Start();
    }

    @Inject(at = @At(value = "RETURN"), method = "dispatchKeypresses")
    private void dispatchKeypresses(CallbackInfo info) {
        if (Keyboard.getEventKeyState() && this.currentScreen == null) {
            new KeyboardEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()).call();
        }
    }
}