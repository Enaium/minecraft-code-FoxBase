package cn.enaium.foxbase.client.modules.movement;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.cf4m.module.Category;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "Sprint", key = GLFW.GLFW_KEY_V, category = Category.MOVEMENT)
public class Sprint {

    @Event
    public void onUpdate(UpdateEvent e) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.setSprinting(true);
    }
}