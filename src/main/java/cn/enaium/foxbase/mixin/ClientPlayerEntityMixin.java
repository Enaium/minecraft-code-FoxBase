package cn.enaium.foxbase.mixin;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.events.EventUpdate;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {



    @Inject(at = @At("HEAD"),
            method = "sendChatMessage(Ljava/lang/String;)V",
            cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info)
    {
        if (FoxBase.instance.commandManager.processCommand(message))
                info.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void preTick(CallbackInfo callbackInfo) {
        new EventUpdate().call();
    }


}
