package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.event.events.EventRender2D;
import cn.enaium.foxbase.event.events.EventUpdate;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
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

    @Inject(at = @At("RETURN"), method = "renderGameOverlay")
    private void renderGameOverlay(CallbackInfo info) {
        GlStateManager.pushMatrix();
        new EventRender2D().call();
        GlStateManager.popMatrix();
    }

}
