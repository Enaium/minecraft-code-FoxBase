package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import cn.enaium.foxbase.client.clickgui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class GUI extends Module {
    public GUI() {
        super("GUI", "GUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftClient.getInstance().openScreen(new ClickGUI());
        enable();
    }
}
