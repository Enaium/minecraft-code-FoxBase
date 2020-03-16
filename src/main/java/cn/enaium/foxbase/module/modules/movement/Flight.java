package cn.enaium.foxbase.module.modules.movement;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventUpdate;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.setting.Setting;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {

    Setting speed = new Setting(this, "Speed", 3.5D, 1.0D, 10.0D);

    public Flight() {
        super("Flight", GLFW.GLFW_KEY_C, Category.MOVEMENT);
        addSetting(speed);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ClientPlayerEntity player = mc.player;
        player.abilities.flying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ClientPlayerEntity player = mc.player;
        player.abilities.flying = false;
    }
}
