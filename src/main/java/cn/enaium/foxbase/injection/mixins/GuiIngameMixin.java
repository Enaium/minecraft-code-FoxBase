package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.event.events.EventUpdate;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    @Inject(at = @At("HEAD"), method = "renderGameOverlay()V")
    private void renderGameOverlay(CallbackInfo info) {
        new EventUpdate().call();
    }

}
