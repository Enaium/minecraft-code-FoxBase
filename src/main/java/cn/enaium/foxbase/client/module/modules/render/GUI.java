package cn.enaium.foxbase.client.module.modules.render;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.foxbase.client.clickgui.ClickGUI;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module(value = "GUI",key = Keyboard.KEY_RSHIFT)
public class GUI {
    @Enable
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(new ClickGUI());
        CF4M.INSTANCE.module.enable(this);
    }
}
