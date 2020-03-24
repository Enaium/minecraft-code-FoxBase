package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.events.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {
    @Inject(at = @At("HEAD"), method = "onUpdate()V")
    private void onUpdate(CallbackInfo info) {
        new EventUpdate().call();
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage(Ljava/lang/String;)V", cancellable = true)
    private void sendChatMessage(String message, CallbackInfo info) {
        if (FoxBase.instance.commandManager.processCommand(message)) {
            info.cancel();
        }
    }
}
