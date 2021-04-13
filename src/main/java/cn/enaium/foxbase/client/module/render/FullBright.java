package cn.enaium.foxbase.client.module.render;

import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import static cn.enaium.foxbase.client.module.Type.RENDER;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "FullBright", key = GLFW.GLFW_KEY_G, type = RENDER)
public class FullBright {

    @Enable
    public void onEnable() {
        MinecraftClient.getInstance().options.gamma = 300;
    }

    @Disable
    public void onDisable() {
        MinecraftClient.getInstance().options.gamma = 1;
    }


}
