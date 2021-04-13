package cn.enaium.foxbase.client.module.movement;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.foxbase.client.event.Events.UpdatedEvent;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import static cn.enaium.foxbase.client.module.Type.MOVEMENT;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "Sprint", key = GLFW.GLFW_KEY_V, type = MOVEMENT)
public class Sprint {

    @Event
    public void onUpdate(UpdatedEvent e) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.setSprinting(true);
    }
}
