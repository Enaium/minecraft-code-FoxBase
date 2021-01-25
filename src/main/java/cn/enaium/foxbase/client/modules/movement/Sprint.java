package cn.enaium.foxbase.client.modules.movement;

import cn.enaium.cf4m.event.EventAT;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Sprint", GLFW.GLFW_KEY_V, Category.MOVEMENT);
    }

    @EventAT
    public void onUpdate(UpdateEvent e) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.setSprinting(true);
    }
}
