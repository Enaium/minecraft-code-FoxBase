package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.FoxBase;
import net.minecraft.client.Minecraft;
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
public class ExampleMixin {

    @Inject(at = @At("RETURN"), method = "startGame")
    private void startGame(CallbackInfo info) {
        FoxBase.instance.Start();
    }

    @Inject(at = @At("RETURN"), method = "shutdownMinecraftApplet")
    private void shutdownMinecraftApplet(CallbackInfo info) {
        FoxBase.instance.Stop();
    }



}