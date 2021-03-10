package cn.enaium.foxbase.mixins;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.events.UpdateEvent;
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
    @Inject(at = @At("HEAD"), method = "onUpdate")
    private void onUpdate(CallbackInfo info) {
        new UpdateEvent().call();
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void sendChatMessage(String message, CallbackInfo info) {
        if (CF4M.INSTANCE.command.isCommand(message)) {
            info.cancel();
        }
    }
}
