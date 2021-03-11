package cn.enaium.foxbase.mixin;

import cn.enaium.foxbase.client.FoxBase;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

//    private Window window;
//
//    @Inject(at = @At("RETURN"), method = "updateWindowTitle")
//    private void updateWindowTitle(CallbackInfo callbackInfo) {
//        this.window.setTitle(FoxBase.instance.name + " | Author:" + FoxBase.instance.author + " | Version:" + FoxBase.instance.version + " | Minecraft:" + FoxBase.instance.game);
//    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo callbackInfo) {
        FoxBase.instance.run();
    }
}
