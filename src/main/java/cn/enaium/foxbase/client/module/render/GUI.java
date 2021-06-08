package cn.enaium.foxbase.client.module.render;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.foxbase.client.clickgui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import static cn.enaium.foxbase.client.module.Type.RENDER;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "GUI", key = GLFW.GLFW_KEY_RIGHT_SHIFT, type = RENDER)
public class GUI {
    @Enable
    public void onEnable() {
        MinecraftClient.getInstance().openScreen(new ClickGUI());
        CF4M.INSTANCE.getModule().getByInstance(this).enable();
    }
}
