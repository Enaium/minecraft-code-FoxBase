package cn.enaium.foxbase.mixin;

import cn.enaium.foxbase.event.events.EventKeyboard;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin
{
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onOnKey(long windowHandle, int keyCode, int scanCode,
                         int action, int modifiers, CallbackInfo ci)
    {
        new EventKeyboard(keyCode,scanCode,action,modifiers).call();
    }
}