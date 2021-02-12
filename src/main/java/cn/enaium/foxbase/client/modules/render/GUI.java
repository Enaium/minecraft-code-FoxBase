package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.module.Category;
import cn.enaium.foxbase.client.clickgui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "GUI", key = GLFW.GLFW_KEY_RIGHT_SHIFT, category = Category.RENDER)
public class GUI {
    @Enable
    public void onEnable() {
        MinecraftClient.getInstance().openScreen(new ClickGUI());
        CF4M.getInstance().module.enable(this);
    }
}
