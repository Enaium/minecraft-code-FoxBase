package cn.enaium.foxbase.injection.mixins;

import cn.enaium.foxbase.client.events.EventRender2D;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
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

    @Inject(at = @At("HEAD"), method = "renderTooltip")
    private void renderTooltip(ScaledResolution p_renderTooltip_1_, float p_renderTooltip_2_, CallbackInfo info) {
        new EventRender2D().call();
    }

}
