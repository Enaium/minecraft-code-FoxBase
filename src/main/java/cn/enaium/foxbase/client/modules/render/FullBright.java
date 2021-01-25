package cn.enaium.foxbase.client.modules.render;

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
public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "FullBright", GLFW.GLFW_KEY_G, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftClient.getInstance().options.gamma = 300;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftClient.getInstance().options.gamma = 1;
    }


}
