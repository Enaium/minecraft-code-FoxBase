package cn.enaium.foxbase.mixins;

import cn.enaium.foxbase.client.events.EventRender3D;
import net.minecraft.client.renderer.EntityRenderer;
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
@Mixin(EntityRenderer.class)
public class EntityRenderMixin {
    @Inject(at = @At("RETURN"), method = "renderWorldPass")
    private void renderWorldPass(int p_renderWorldPass_1_, float p_renderWorldPass_2_, long p_renderWorldPass_3_, CallbackInfo info) {
        GlStateManager.pushMatrix();
        new EventRender3D(p_renderWorldPass_2_).call();
        GlStateManager.popMatrix();
    }
}
