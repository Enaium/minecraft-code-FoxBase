package cn.enaium.foxbase.injection.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleMixin {
	@Inject(at = @At("RETURN"), method = "startGame")
	private void startGame(CallbackInfo info) {
		System.out.println("This line is printed by an example mod mixin!");
	}
}
