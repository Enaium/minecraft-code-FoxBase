package cn.enaium.foxbase.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.Event;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.events.MotionEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {


    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if (CF4M.getInstance().commandManager.isCommand(message)) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void preTick(CallbackInfo callbackInfo) {
        new UpdateEvent().call();
    }

    @Inject(at = {@At("HEAD")}, method = {"sendMovementPackets()V"})
    private void onSendMovementPacketsHEAD(CallbackInfo ci) {
        new MotionEvent(Event.Type.PRE).call();
    }

    @Inject(at = {@At("TAIL")}, method = {"sendMovementPackets()V"})
    private void onSendMovementPacketsTAIL(CallbackInfo ci) {
        new MotionEvent(Event.Type.POST).call();
    }


}
